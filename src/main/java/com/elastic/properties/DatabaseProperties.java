package com.elastic.properties;

/**
 * This class groups all properties needed to define a database connection:
 * <li>URL access</li>
 * <li>Database name</li>
 * <li>Database user</li>
 * <li>Database password</li>
 * 
 * @author David
 */
public class DatabaseProperties {

    private String url;
    private String name;
    private String user;
    private String password;

    public DatabaseProperties(String url, String name, String user, String password) {
	this.url = url;
	this.name = name;
	this.user = user;
	this.password = password;
    }

    public String getUrl() {
	return url;
    }

    public String getName() {
	return name;
    }

    public String getUser() {
	return user;
    }

    public String getPassword() {
	return password;
    }
}