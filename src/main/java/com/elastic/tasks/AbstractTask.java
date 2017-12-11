package com.elastic.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.TaskExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;

public abstract class AbstractTask {

    private static Logger logger = LoggerFactory.getLogger(AbstractTask.class);

    ContinuousDeliveryProperties properties;

    public AbstractTask(ContinuousDeliveryProperties properties) {
	this.properties = properties;
    }

    public static Logger getLogger() {
	return AbstractTask.logger;
    }

    public abstract void execute() throws TaskExecutionException;
}