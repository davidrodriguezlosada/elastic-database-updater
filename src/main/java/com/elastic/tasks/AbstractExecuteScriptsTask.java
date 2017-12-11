package com.elastic.tasks;

import java.io.IOException;

import com.elastic.exceptions.TaskExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.properties.DatabaseProperties;
import com.elastic.tasks.objects.Database;

public abstract class AbstractExecuteScriptsTask extends AbstractTask {

    Database database;

    public AbstractExecuteScriptsTask(ContinuousDeliveryProperties properties, DatabaseProperties databaseProperties) {
	super(properties);

	this.database = new Database(databaseProperties);
    }

    @Override
    public void execute() throws TaskExecutionException {
	try {
	    this.database.executeScripts(this.properties.getDatabaseUpdateScriptsPath());
	} catch (IOException e) {
	    AbstractTask.getLogger().error(e.getMessage(), e);

	    throw new TaskExecutionException(this);
	}
    }
}