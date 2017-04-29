package com.elastic;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.CommandExecutionException;
import com.elastic.exceptions.DifferentDaysException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.Application;
import com.elastic.tasks.Database;
import com.elastic.tasks.Service;
import com.elastic.tasks.UpdateFilesChecker;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

	logger.info("INIT MAIN PROCESS");

	try {
	    ContinuousDeliveryProperties properties = new ContinuousDeliveryProperties();

	    UpdateFilesChecker.check(properties);

	    Service service = new Service(properties.getServiceName());
	    service.stop();

	    Database originDatabase = new Database(properties.getOriginDatabaseProperties());
	    String originDatabaseBackupPath = originDatabase.backupDatabase(properties.getBackupsPath());

	    Database destinationDatabase = new Database(properties.getDestinationDatabaseProperties());
	    destinationDatabase.backupDatabase(properties.getBackupsPath());
	    destinationDatabase.restoreDatabase(originDatabaseBackupPath);
	    destinationDatabase.executeScripts(properties.getDatabaseUpdateScriptsPath());

	    Application testApplication = new Application(properties.getTomcatAppName(), properties.getTomcatPath());
	    testApplication.backup(properties.getBackupsPath());
	    testApplication.remove();
	    testApplication.deploy(properties.getWarPath());

	    service.start();

	    logger.info("MAIN PROCESS SUCCESFULY FINISHED");

	} catch (IOException | DifferentDaysException | InterruptedException | CommandExecutionException
		| URISyntaxException e) {
	    logger.error(e.getMessage(), e);

	    // TODO: Send mail :
	    // http://stackoverflow.com/questions/3649014/send-email-using-java

	    // TODO: Rollback all process
	}
    }
}