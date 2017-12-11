package com.elastic.tasks;

import java.io.IOException;
import java.net.URISyntaxException;

import com.elastic.exceptions.CommandExecutionException;
import com.elastic.exceptions.TaskExecutionException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.Database;

public abstract class AbstractBackupDatabaseTask extends AbstractTask implements IRollbackableTask {

    Database database;
    String databaseBackupPath;

    public AbstractBackupDatabaseTask(Database database, ContinuousDeliveryProperties properties) {
	super(properties);

	this.database = database;
    }

    @Override
    public void execute() throws TaskExecutionException {
	try {
	    this.databaseBackupPath = this.database.backupDatabase(this.properties.getBackupsPath());
	} catch (IOException | InterruptedException | CommandExecutionException e) {
	    AbstractTask.getLogger().error(e.getMessage(), e);

	    throw new TaskExecutionException(this);
	}
    }

    @Override
    public void rollback() {
	try {
	    this.database.restoreDatabase(this.databaseBackupPath);
	} catch (IOException | InterruptedException | CommandExecutionException | URISyntaxException e) {
	    AbstractTask.getLogger().error(e.getMessage(), e);
	}
    }
}