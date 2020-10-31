/**
 * 
 */
package com.github.cobolio.internal.cobol.fields;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.github.cobolio.internal.xml.cb2xml.EnhancedItem;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractArrayField<T extends AbstractField> extends AbstractField implements Iterable<T> {

	/**
	 * @param properties
	 */
	protected AbstractArrayField(EnhancedItem properties) {
		super(properties);
	}
	
	public abstract int size();

	public abstract T getElementAt(int index);
	
	public abstract Object getValueArrayAsObject(boolean reverseBits);

	  /**
	   * Generates an iterator to allow the array processing in loops.
	   *
	   * @return an iterator for the array
	   */
	  @SuppressWarnings("NullableProblems")
	  @Override
	  public Iterator<T> iterator() {
	    return new Iterator<T>() {
	      private int index = 0;

	      @Override
	      public boolean hasNext() {
	        return this.index < size();
	      }

	      @Override
	      public T next() {
	        if (this.index >= size()) {
	          throw new NoSuchElementException(this.index + ">=" + size());
	        }
	        return getElementAt(this.index++);
	      }

	      @Override
	      public void remove() {
	        throw new UnsupportedOperationException("Removing is unsupported here");
	      }

	    };
	  }
}
