package com.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrunchifyGetPropertyValues {

	Logger logger = LoggerFactory.getLogger(CrunchifyGetPropertyValues.class);

	final static String propFileName = "holiday.properties";

	String result = "";
	InputStream inputStream;

	public Properties getPropValue() throws IOException {

		Properties prop = new Properties();

		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(new InputStreamReader(inputStream, "UTF-8"));
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

		} catch (Exception e) {
			logger.debug("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return prop;
	}
}
