import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class VelocityTest {

	static String inputTemplate = "./src/test/java/test.vm";
	static String className = "VelocityExample";
	static String message = "Hello World!";
	static String packge = "./com/stats/";
	static String outputFile = className + ".java";

	public static void main(String[] args) throws IOException {

		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();

		VelocityContext context = new VelocityContext();
		context.put("className", className);
		context.put("message", message);
		File file = new File(packge + outputFile);
		if (!file.getParentFile().exists()) {
			Files.createDirectories(file.getParentFile().toPath());
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		Writer writer = new FileWriter(file);
		Velocity.mergeTemplate(inputTemplate, "UTF-8", context, writer);
		writer.flush();
		writer.close();

		System.out.println("Generated " + outputFile);
	}
}
