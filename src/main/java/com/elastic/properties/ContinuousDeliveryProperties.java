package com.elastic.properties;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class will load and store all properties configured on the file
 * <i>config.properties</i>
 * 
 * @author David
 */
public class ContinuousDeliveryProperties {

    private Properties properties;

    public ContinuousDeliveryProperties() throws IOException {
	BufferedInputStream inputStream = new BufferedInputStream(
		ContinuousDeliveryProperties.class.getClassLoader().getResourceAsStream("config.properties"));
	this.properties = new Properties();
	this.properties.load(inputStream);
    }

    public String getServiceName() {
	return properties.getProperty("service.name");
    }

    public DatabaseProperties getOriginDatabaseProperties() {
	return new DatabaseProperties(properties.getProperty("origin.database.url"),
		properties.getProperty("origin.database.name"), properties.getProperty("origin.database.user"),
		properties.getProperty("origin.database.password"));
    }

    public DatabaseProperties getDestinationDatabaseProperties() {
	return new DatabaseProperties(properties.getProperty("destination.database.url"),
		properties.getProperty("destination.database.name"),
		properties.getProperty("destination.database.user"),
		properties.getProperty("destination.database.password"));
    }

    public String getTomcatPath() {
	return properties.getProperty("tomcat.path");
    }

    public String getTomcatAppName() {
	return properties.getProperty("tomcat.app.name");
    }

    public String getDatabaseUpdateScriptsPath() {
	return properties.getProperty("database.update.scripts.path");
    }

    public String getWarPath() {
	return properties.getProperty("war.path");
    }

    public String getBackupsPath() {
	return properties.getProperty("backups.path");
    }
}