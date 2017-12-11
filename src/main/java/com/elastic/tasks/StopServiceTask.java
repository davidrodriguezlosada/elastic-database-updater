package com.elastic.tasks;

import java.io.IOException;

import com.elastic.exceptions.CommandExecutionException;
import com.elastic.exceptions.TaskExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Service;

public class StopServiceTask extends AbstractTask implements IRollbackableTask {

    Service service;

    public StopServiceTask(ContinuousDeliveryProperties properties) {
	super(properties);

	this.service = new Service(properties.getServiceName());
    }

    @Override
    public void execute() throws TaskExecutionException {
	try {
	    this.service.stop();
	} catch (IOException | InterruptedException | CommandExecutionException e) {
	    AbstractTask.getLogger().error(e.getMessage(), e);

	    throw new TaskExecutionException(this);
	}
    }

    @Override
    public void rollback() {
	try {
	    this.service.start();
	} catch (IOException | InterruptedException | CommandExecutionException e) {
	    AbstractTask.getLogger().error(e.getMessage(), e);
	}
    }
}
