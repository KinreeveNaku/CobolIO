/**
 * 
 */
package com.github.mfds2j.classgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import static org.apache.velocity.app.Velocity.*;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author Andrew
 *
 */
public class ClassGenerator {
	private Properties velocityProperties;
	private static String inputTemplate = "./src/test/java/test.vm";
	public ClassGenerator() {
		velocityProperties.setProperty(RUNTIME_LOG_NAME, "MFDC2J_LOG");
		velocityProperties.setProperty(STRICT_MATH, "true");
	}

	public ClassGenerator(MFDS2JProperties properties) {

	}

	public void generate(String packPath, String className, VelocityContext context) throws IOException {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();

		File file = new File(packPath.replaceAll(".", "\\|/") + "/" + className + ".java");
		if (!file.getParentFile().exists()) {
			Files.createDirectories(file.getParentFile().toPath());
		}
		if (!file.exists() && !file.createNewFile()) {
			throw new FileNotFoundException("The file did not exist and could not be created.");
		}
		Writer writer = new FileWriter(file);
		Velocity.mergeTemplate(inputTemplate, "UTF-8", context, writer);
		writer.flush();
		writer.close();
	}

	/** Parameter types for call with no parameters. */
	/*
	 * private static final CtClass[] NO_ARGS = {};
	 * 
	 *//** Parameter types for call with single int value. *//*
																 * private static final CtClass[] INT_ARGS = {
																 * CtClass.intType };
																 */
	/*
	 * Object[] getAll(); Class<?> getTypeOf(int i); <T> T get(int i) throws
	 * IllegalArgumentException;
	 */
	/*
	 * protected byte[] createClass(Class<?> tclas, Method gmeth, Method smeth,
	 * String cname, FieldsData datas) throws Exception {
	 * 
	 * // build generator for the new class String tname = tclas.getName();
	 * ClassPool pool = ClassPool.getDefault(); CtClass clas =
	 * pool.makeClass(cname); clas.addInterface(pool.get("IVirtual")); CtClass
	 * target = pool.get(tname);
	 * 
	 * // add target object field to class CtField field = new CtField(target,
	 * "m_target", clas); clas.addField(field);
	 * 
	 * // add public default constructor method to class CtConstructor cons = new
	 * CtConstructor(NO_ARGS, clas); cons.setBody(";"); clas.addConstructor(cons);
	 * 
	 * // add public setTarget method CtMethod meth = new CtMethod(CtClass.voidType,
	 * "setTarget", new CtClass[] { pool.get("java.lang.Object") }, clas);
	 * meth.setBody("m_target = (" + tclas.getName() + ")$1;");
	 * clas.addMethod(meth);
	 * 
	 * 
	 * for(FieldData data : datas) { field = new CtField(TODO, TODO, TODO);
	 * clas.addField(field);
	 * 
	 * meth = new CtMethod(data.getReturnType(), "get" + data.getName(),
	 * data.getParams(), clas); meth.setBody("return this." + data.getName() +
	 * "();"); } // add public get method meth = new CtMethod(CtClass.intType,
	 * "get", NO_ARGS, clas); meth.setBody("return this." + gmeth.getName() +
	 * "();"); clas.addMethod(meth);
	 * 
	 * // add public getTypeOf method meth = new
	 * CtMethod(pool.get("java.lang.Object"), "getTypeOf", INT_ARGS, clas);
	 * meth.setBody("return m_target." + gmeth.getName() + "();"); //TODO construct
	 * method content to iterate through all fields in the class.
	 * clas.addMethod(meth);
	 * 
	 * // add public setValue method meth = new CtMethod(CtClass.voidType,
	 * "setValue", INT_ARGS, clas); meth.setBody("m_target." + smeth.getName() +
	 * "($1);"); clas.addMethod(meth);
	 * 
	 * // return binary representation of completed class return clas.toBytecode();
	 * }
	 */
}
