package com.elastic.tasks;

import java.io.IOException;

import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.properties.DatabaseProperties;
import com.elastic.tasks.objects.Database;

public abstract class AbstractExecuteScriptsTask extends AbstractTask {

    Database database;

    public AbstractExecuteScriptsTask(ContinuousDeliveryProperties properties, DatabaseProperties databaseProperties) {
	super(properties);

	database = new Database(databaseProperties);
    }

    @Override
    public void execute() {
	try {
	    database.executeScripts(properties.getDatabaseUpdateScriptsPath());
	} catch (IOException e) {
	    getLogger().error(e.getMessage(), e);
	}
    }
}