package com.elastic.tasks;

import com.elastic.properties.ContinuousDeliveryProperties;

public class ExecuteScriptsInDestinationDatabaseTask extends AbstractExecuteScriptsTask {

    public ExecuteScriptsInDestinationDatabaseTask(ContinuousDeliveryProperties properties) {
	super(properties, properties.getDestinationDatabaseProperties());
    }
}