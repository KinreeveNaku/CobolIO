/**
 * 
 */
package com.github.cobolio.internal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @author Andrew
 *
 */
class ObjectStream {
	static ObjectMapper mapper = new ObjectMapper();
	static ObjectWriter writer = new ObjectMapper().writer();
	static ObjectReader reader = new ObjectMapper().reader();
	static ObjectOutputStream output;
	static ObjectInputStream input;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		File file = new File("./src/main/resources/test.jo");
		boolean exists = file.exists() ? true : file.createNewFile();
		OutputStream out = new FileOutputStream(file);
		ObjectMapper buf = new ObjectMapper(); 
		output = new ObjectOutputStream(out);
		output.writeObject(mapper);
		output.close();
		InputStream in = new FileInputStream(file);
		input = new ObjectInputStream(in);
		ObjectMapper rebuf = (ObjectMapper) input.readObject();
		System.out.println(buf);
		System.out.println(rebuf);
		System.out.println(rebuf.toString().equals(buf.toString()));
		
		
	}
	
	
}
class Wrapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int i;
	public int j;
	public int k;
	
	/**
	 * 
	 */
	public Wrapper(int i, int j, int k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	@Override
	public String toString() {
		return "i:" + i + ", j:" + j + "k:" + k;
	}
}