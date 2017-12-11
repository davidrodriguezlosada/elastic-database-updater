package com.elastic.tasks;

import com.elastic.exceptions.TaskExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Database;

public class RestoreOriginDatabaseInDestinationDatabaseTask extends AbstractTask implements IRollbackableTask {

    public RestoreOriginDatabaseInDestinationDatabaseTask(ContinuousDeliveryProperties properties) {
	super(properties);
    }

    @Override
    public void execute() throws TaskExecutionException {
	Database destinationDatabase = new Database(this.properties.getDestinationDatabaseProperties());
	// TODO: Get backup path ¿make pipeline global?
	// destinationDatabase.restoreDatabase(originDatabaseBackupPath);
    }

    @Override
    public void rollback() {

    }
}