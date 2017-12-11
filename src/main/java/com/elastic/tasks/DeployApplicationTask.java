package com.elastic.tasks;

import java.io.IOException;

import com.elastic.exceptions.TaskExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Application;

public class DeployApplicationTask extends AbstractTask implements IRollbackableTask {

    public DeployApplicationTask(ContinuousDeliveryProperties properties) {
	super(properties);
    }

    @Override
    public void execute() throws TaskExecutionException {

	try {
	    Application testApplication = new Application(this.properties.getTomcatAppName(),
		    this.properties.getTomcatPath());
	    testApplication.backup(this.properties.getBackupsPath());
	    testApplication.remove();
	    testApplication.deploy(this.properties.getWarPath());
	} catch (IOException e) {
	    AbstractTask.getLogger().error(e.getMessage(), e);

	    throw new TaskExecutionException(this);
	}
    }

    @Override
    public void rollback() {
	// TODO Auto-generated method stub

    }

}
