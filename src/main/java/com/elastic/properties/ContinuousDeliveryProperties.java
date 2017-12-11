package com.elastic.properties;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class will load and store all properties configured on the file
 * <i>config.properties</i>
 *
 * @author David Rodriguez Losada
 */
public class ContinuousDeliveryProperties {

    private final Properties properties;

    public ContinuousDeliveryProperties() throws IOException {
	BufferedInputStream inputStream = new BufferedInputStream(
		ContinuousDeliveryProperties.class.getClassLoader().getResourceAsStream("config.properties"));
	this.properties = new Properties();
	this.properties.load(inputStream);
    }

    public String getPipeline() {
	return this.properties.getProperty("pipeline");
    }

    public String getServiceName() {
	return this.properties.getProperty("service.name");
    }

    public DatabaseProperties getOriginDatabaseProperties() {
	return new DatabaseProperties(this.properties.getProperty("origin.database.url"),
		this.properties.getProperty("origin.database.name"),
		this.properties.getProperty("origin.database.user"),
		this.properties.getProperty("origin.database.password"));
    }

    public DatabaseProperties getDestinationDatabaseProperties() {
	return new DatabaseProperties(this.properties.getProperty("destination.database.url"),
		this.properties.getProperty("destination.database.name"),
		this.properties.getProperty("destination.database.user"),
		this.properties.getProperty("destination.database.password"));
    }

    public String getTomcatPath() {
	return this.properties.getProperty("tomcat.path");
    }

    public String getTomcatAppName() {
	return this.properties.getProperty("tomcat.app.name");
    }

    public String getDatabaseUpdateScriptsPath() {
	return this.properties.getProperty("database.update.scripts.path");
    }

    public String getWarPath() {
	return this.properties.getProperty("war.path");
    }

    public String getBackupsPath() {
	return this.properties.getProperty("backups.path");
    }
}