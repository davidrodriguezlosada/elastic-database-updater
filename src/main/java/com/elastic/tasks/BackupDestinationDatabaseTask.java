package com.elastic.tasks;

import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Database;

public class BackupDestinationDatabaseTask extends AbstractBackupDatabaseTask {

    public BackupDestinationDatabaseTask(ContinuousDeliveryProperties properties) {
	super(new Database(properties.getDestinationDatabaseProperties()), properties);
    }
}