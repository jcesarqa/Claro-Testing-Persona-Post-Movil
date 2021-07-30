package pe.com.claro.miclaroagileqa;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

	private static Properties properties;

	public static Properties getProperties() {
		InputStream is;
		if (properties == null) {
			try {
				properties = new Properties();
				String propFileName = getPropertiesFileName();
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				is = loader.getResourceAsStream(propFileName);
				properties.load(is);
				return properties;
			} catch (IOException e) {
				return null;
			}
		} else {
			return properties;
		}

	}

	private static String getPropertiesFileName() {
		String propFileName = System.getProperty(Constant.PROPERTIES_NAME_PARAMETER);
		if (propFileName == null) {
			propFileName = "props/miclaro.properties";
		}
		return propFileName;
	}
	
	public static String getPropety(String name){
		return getProperties().getProperty(name);
	}

}
