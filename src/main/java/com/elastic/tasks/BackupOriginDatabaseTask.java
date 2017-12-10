package com.elastic.tasks;

import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Database;

public class BackupOriginDatabaseTask extends AbstractBackupDatabaseTask {

    public BackupOriginDatabaseTask(ContinuousDeliveryProperties properties) {
	super(new Database(properties.getOriginDatabaseProperties()), properties);
    }
}