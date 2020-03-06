package com.amphora.framework.support.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

	private Properties properties;

	/**
	 * Load property file by taking specified property file path
	 * @param propertyFilePath - path of property file path
	 */
	public PropertiesFile(String propertyFilePath){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	/**
	 * Get value of specified key which is available in properties path
	 * @param propertyFilePath - path of property file path
	 */
	public String getProperty(String name) {
		return properties.getProperty(name);
	}

}
