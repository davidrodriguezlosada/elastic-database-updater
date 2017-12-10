package com.elastic.tasks.objects;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.CommandExecutionException;
import com.elastic.utils.CommandLineUtils;

/**
 * This class provides methods to administer a Windows Service
 * 
 * @author David Rodriguez Losada
 */
public class Service {

    private static Logger logger = LoggerFactory.getLogger(Service.class);

    private String serviceName;

    public Service(String serviceName) {
	this.serviceName = serviceName;
    }

    /**
     * Starts the service
     * 
     * @throws IOException
     * @throws InterruptedException
     * @throws CommandExecutionException
     */
    public void start() throws IOException, InterruptedException, CommandExecutionException {

	logger.info("Starting service {}", serviceName);

	CommandLineUtils.executeCommand("net start " + serviceName);

	logger.info("Service {} succesfully started", serviceName);
    }

    /**
     * Stops the service
     * 
     * @throws IOException
     * @throws InterruptedException
     * @throws CommandExecutionException
     */
    public void stop() throws IOException, InterruptedException, CommandExecutionException {

	logger.info("Stopping service {}", serviceName);
	try {
	    CommandLineUtils.executeCommand("net stop " + serviceName);

	    logger.info("Service {} succesfully stoped", serviceName);
	} catch (CommandExecutionException ex) {
	    if (ex.getError().toString().contains("3521")) {
		logger.info("Service {} already stoped", serviceName);
	    } else {
		throw ex;
	    }
	}
    }
}