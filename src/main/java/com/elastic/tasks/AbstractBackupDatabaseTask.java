package com.elastic.tasks;

import java.io.IOException;
import java.net.URISyntaxException;

import com.elastic.exceptions.CommandExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Database;

public abstract class AbstractBackupDatabaseTask extends AbstractTask implements IRollbackable {

    Database database;
    String databaseBackupPath;

    public AbstractBackupDatabaseTask(Database database, ContinuousDeliveryProperties properties) {
	super(properties);

	this.database = database;
    }

    @Override
    public void execute() {
	try {
	    databaseBackupPath = database.backupDatabase(properties.getBackupsPath());
	} catch (IOException | InterruptedException | CommandExecutionException e) {
	    getLogger().error(e.getMessage(), e);
	}
    }

    @Override
    public void rollback() {
	try {
	    database.restoreDatabase(databaseBackupPath);
	} catch (IOException | InterruptedException | CommandExecutionException | URISyntaxException e) {
	    getLogger().error(e.getMessage(), e);
	}
    }
}