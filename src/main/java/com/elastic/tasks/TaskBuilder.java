package com.elastic.tasks;

import com.elastic.exceptions.UnknownTaskException;
import com.elastic.properties.ContinuousDeliveryProperties;

public class TaskBuilder {

    public static AbstractTask createTask(String taskName, ContinuousDeliveryProperties properties)
	    throws UnknownTaskException {
	AbstractTask task;

	switch (taskName) {
	case "STOP_SERVICE":
	    task = new StopServiceTask(properties);
	    break;
	case "START_SERVICE":
	    task = new StartServiceTask(properties);
	    break;
	case "BACKUP_ORIGIN_DATABASE":
	    task = new BackupOriginDatabaseTask(properties);
	    break;
	case "BACKUP_DESTINATION_DATABASE":
	    task = new BackupDestinationDatabaseTask(properties);
	    break;
	case "RESTORE_ORIGIN_DATABASE_IN_DESTINATION_DATABASE":
	    task = new RestoreOriginDatabaseInDestinationDatabaseTask(properties);
	    break;
	case "EXECUTE_SCRIPTS_IN_DESTINATION_DATABASE":
	    task = new ExecuteScriptsInDestinationDatabaseTask(properties);
	    break;
	case "EXECUTE_SCRIPTS_IN_ORIGIN_DATABASE":
	    task = new ExecuteScriptsInOriginDatabaseTask(properties);
	    break;
	case "DEPLOY_APPLICATION":
	    task = new DeployApplicationTask(properties);
	    break;
	default:
	    throw new UnknownTaskException(taskName);
	}

	return task;
    }
}
