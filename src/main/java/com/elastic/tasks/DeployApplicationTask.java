package com.elastic.tasks;

import java.io.IOException;

import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Application;

public class DeployApplicationTask extends AbstractTask implements IRollbackable {

    public DeployApplicationTask(ContinuousDeliveryProperties properties) {
	super(properties);
    }

    @Override
    public void execute() {

	try {
	    Application testApplication = new Application(properties.getTomcatAppName(), properties.getTomcatPath());
	    testApplication.backup(properties.getBackupsPath());
	    testApplication.remove();
	    testApplication.deploy(properties.getWarPath());
	} catch (IOException e) {
	    getLogger().error(e.getMessage(), e);
	}
    }

    @Override
    public void rollback() {
	// TODO Auto-generated method stub

    }

}
