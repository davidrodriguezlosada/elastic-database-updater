package com.elastic.tasks;

import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Database;

public class RestoreOriginDatabaseInDestinationDatabaseTask extends AbstractTask implements IRollbackable {

    public RestoreOriginDatabaseInDestinationDatabaseTask(ContinuousDeliveryProperties properties) {
	super(properties);
    }

    @Override
    public void execute() {
	Database destinationDatabase = new Database(properties.getDestinationDatabaseProperties());
	// TODO: Get backup path ¿make pipeline global?
	// destinationDatabase.restoreDatabase(originDatabaseBackupPath);
    }

    @Override
    public void rollback() {

    }
}