package com.elastic.tasks;

import com.elastic.properties.ContinuousDeliveryProperties;

public class ExecuteScriptsInOriginDatabaseTask extends AbstractExecuteScriptsTask {

    public ExecuteScriptsInOriginDatabaseTask(ContinuousDeliveryProperties properties) {
	super(properties, properties.getOriginDatabaseProperties());
    }
}