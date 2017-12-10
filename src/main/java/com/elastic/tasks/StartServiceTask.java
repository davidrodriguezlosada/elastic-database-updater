package com.elastic.tasks;

import java.io.IOException;

import com.elastic.exceptions.CommandExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Service;

public class StartServiceTask extends AbstractTask implements IRollbackable {

    Service service;

    public StartServiceTask(ContinuousDeliveryProperties properties) {
	super(properties);

	service = new Service(properties.getServiceName());
    }

    @Override
    public void execute() {
	try {
	    service.start();
	} catch (IOException | InterruptedException | CommandExecutionException e) {
	    getLogger().error(e.getMessage(), e);
	}
    }

    @Override
    public void rollback() {
	try {
	    service.stop();
	} catch (IOException | InterruptedException | CommandExecutionException e) {
	    getLogger().error(e.getMessage(), e);
	}
    }
}