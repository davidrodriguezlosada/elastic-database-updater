package com.elastic.properties;

/**
 * This class groups all properties needed to define a database connection:
 * <li>URL access</li>
 * <li>Database name</li>
 * <li>Database user</li>
 * <li>Database password</li>
 *
 * @author David Rodriguez Losada
 */
public class DatabaseProperties {

    private final String url;
    private final String name;
    private final String user;
    private final String password;

    public DatabaseProperties(String url, String name, String user, String password) {
	this.url = url;
	this.name = name;
	this.user = user;
	this.password = password;
    }

    public String getUrl() {
	return this.url;
    }

    public String getName() {
	return this.name;
    }

    public String getUser() {
	return this.user;
    }

    public String getPassword() {
	return this.password;
    }
}