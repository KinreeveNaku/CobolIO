/**
 * 
 */
package com.github.cobolio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.github.cobolio.internal.util.CobolSchemaException;

/**
 * 
 * @author Andrew
 * @see org.apache.avro.Schema
 */
public abstract class CobolSchema implements Serializable {
	private static final long serialVersionUID = 1L;

	private Type type;
	private List<CobolSchema> fields = new LinkedList<>();

	static final Map<String, Type> PRIMITIVES = new HashMap<>();
	static {
		PRIMITIVES.put("string", Type.STRING);
		PRIMITIVES.put("bytes", Type.BYTES);
		PRIMITIVES.put("int", Type.INT);
		PRIMITIVES.put("long", Type.LONG);
		PRIMITIVES.put("float", Type.FLOAT);
		PRIMITIVES.put("double", Type.DOUBLE);
		PRIMITIVES.put("boolean", Type.BOOLEAN);
		PRIMITIVES.put("null", Type.NULL);
	}
	
	

	CobolSchema(Type type) {
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	

	public CobolSchema get(int index) {
		return this.fields.get(index);

	}

	/**
	 * If this is a record, enum or fixed, returns its name, otherwise the name of
	 * the primitive type.
	 */
	public String getName() {
		return type.name;
	}

	private static ThreadLocal<Boolean> validateNames = ThreadLocal.withInitial(() -> true);
	
	@SuppressWarnings({"java:S3398"})
	private static String validateName(String name) {
		if (!validateNames.get()) {
			return name; // not validating names
		}
		int length = name.length();
		if (length == 0)
			throw new CobolSchemaException("Empty name");
		char first = name.charAt(0);
		if (!(Character.isLetter(first) || first == '_'))
			throw new CobolSchemaException("Illegal initial character: " + name);
		for (int i = 1; i < length; i++) {
			char c = name.charAt(i);
			if (!(Character.isLetterOrDigit(c) || c == '_'))
				throw new CobolSchemaException("Illegal character in: " + name);
		}
		return name;
	}

	/* 
	 * classes and enums
	 */
	/** The type of a schema. */
	public enum Type {
		RECORD,
		ENUM,
		ARRAY,
		MAP,
		UNION,
		FIXED,
		STRING,
		BYTES,
		INT,
		LONG,
		FLOAT,
		DOUBLE,
		BOOLEAN,
		NULL;

		private final String name;

		Type() {
			this.name = this.name().toLowerCase(Locale.ENGLISH);
		}

		public String getName() {
			return name;
		}
	}
	
	/**
	 * An object representing the abstract object data of a COBOL record.
	 * 
	 * @author Andrew
	 *
	 */
	@SuppressWarnings("serial")
	class CobolRecordSchema extends NamedCobolSchema {
		/**
		 * @param type
		 */

		private String name;
		private List<CobolField> fields;
		private Map<String, CobolField> fieldMap;

		public CobolRecordSchema(Type type, Name name) {
			super(type, name);
		}

		public CobolRecordSchema(Name name, List<CobolField> fields) {
			super(Type.RECORD, name);
			setFields(fields);
		}

		public void setFields(List<CobolField> fields) {
			// in this context, all fields must have names, otherwise an exception should be
			// thrown or the field should be thrown away.
			if (this.fields != null) {
				throw new CobolSchemaException("Fields are already set");
			}

			if (fields.get(0) == null) {
				throw new CobolSchemaException("");// TODO Error message
			}
			int i = 0;
			fieldMap = new HashMap<>();
			LockingArrayList<CobolField> finalFields = new LockingArrayList<>();
			for (CobolField f : fields) {
				if (f.position != -1) {
					throw new CobolSchemaException("");// TODO Error message
				}
				f.position = i++;
				final CobolField existing = fieldMap.put(f.name, f);
				if (existing != null) {
					throw new CobolSchemaException("");// TODO Error message
				}
				finalFields.add(f);
			}
			this.fields = finalFields.lock();
		}
	}

	public static class CobolField implements Comparable<CobolField> {
		private final String name; // name of the field.
		private int position = -1;
		private final CobolSchema schema;
		private final Order order;
		private Set<String> aliases;

		/** How values of this field should be ordered when sorting records. */
		public enum Order {
			ASCENDING, DESCENDING, IGNORE;

			private final String name;

			Order() {
				this.name = this.name().toLowerCase(Locale.ENGLISH);
			}
		}

		CobolField(String name, CobolSchema schema, Order order, Set<String> aliases) {
			this.name = name;
			this.schema = schema;
			this.order = order;
			this.aliases = aliases;
		}

		public CobolField(String name, CobolSchema schema) {
			this(name, schema, Order.ASCENDING, null);
		}

		public CobolField(String name, CobolSchema schema, Order order) {
			this(name, schema, Objects.requireNonNull(order), null);
		}

		/**
		 * 
		 */
		@Override
		public int compareTo(CobolField o) {
			if (o == null || Objects.nonNull(this.name) || Objects.nonNull(o.name)) {
				// check null and check name null and check foreign name null
				return -1;
			} // else if ((this.name + o.name).equals("nullnull")) { }
			else {
				return this.name.compareTo(o.name);
			}

		}
	}

	static class Name {
		private final String name;
		private final String space;
		private final String full;

		/**
		 * 
		 * 
		 * {@link String#concat(String)} is faster for empty strings
		 * 
		 * @param name
		 * @param space
		 */
		public Name(String name, String space) {
			if (name == null) { // anonymous
				this.name = this.space = this.full = null;
				return;
			}
			int lastDot = name.lastIndexOf('.');
			if (lastDot < 0) { // unqualified name
				this.name = validateName(name);
			} else { // qualified name
				space = name.substring(0, lastDot); // get space from name
				this.name = validateName(name.substring(lastDot + 1, name.length()));
			}
			if ("".equals(space))
				space = null;
			this.space = space;
			this.full = (this.space == null) ? this.name : this.space.concat(".") + this.name;
			// {@link String#concat(String)} is faster for empty strings
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof Name))
				return false;
			Name that = (Name) o;
			return Objects.equals(full, that.full);
		}

		@Override
		public int hashCode() {
			return full == null ? 0 : full.hashCode();
		}

		@Override
		public String toString() {
			return full;
		}

		public String getQualified(String defaultSpace) {
			return (space == null || space.equals(defaultSpace)) ? name : full;
		}
	}

	private abstract static class NamedCobolSchema extends CobolSchema {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		transient final Name name;
		Set<Name> aliases;

		public NamedCobolSchema(Type type, Name name) {
			super(type);
			this.name = name;
			if (PRIMITIVES.containsKey(name.full)) {
				throw new CobolSchemaException("Schemas may not be named after primitives: " + name.full);
			}
		}

		@Override
		public String getName() {
			return name.name;
		}

		public String getNamespace() {
			return name.space;
		}

		public String getFullName() {
			return name.full;
		}

		public void addAlias(String alias) {
			addAlias(alias, null);
		}

		public void addAlias(String name, String space) {
			if (aliases == null)
				this.aliases = new LinkedHashSet<>();
			if (space == null)
				space = this.name.space;
			aliases.add(new Name(name, space));
		}

		public Set<String> getAliases() {
			Set<String> result = new LinkedHashSet<>();
			if (aliases != null)
				for (Name alias : aliases)
					result.add(alias.full);
			return result;
		}

		public boolean equalNames(NamedCobolSchema that) {
			return this.name.equals(that.name);
		}
	}

	class LockingArrayList<E> extends ArrayList<E> {
		boolean isLocked = false;
		
		@Override
		public boolean add(E e) {
			if(isLocked) {
				return super.add(e);
			} else {
				throw new IllegalStateException();
			}
		}
		
		@Override
		public void add(int index, E element) {
			if(isLocked) {
				
			}
			super.add(index, element);
		}
		
		public LockingArrayList<E> lock() {
			isLocked = true;
			return this;
		}
	}
}
