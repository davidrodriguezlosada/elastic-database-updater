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

    private final String serviceName;

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

	Service.logger.info("Starting service {}", this.serviceName);

	CommandLineUtils.executeCommand("net start " + this.serviceName);

	Service.logger.info("Service {} succesfully started", this.serviceName);
    }

    /**
     * Stops the service
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws CommandExecutionException
     */
    public void stop() throws IOException, InterruptedException, CommandExecutionException {

	Service.logger.info("Stopping service {}", this.serviceName);
	try {
	    CommandLineUtils.executeCommand("net stop " + this.serviceName);

	    Service.logger.info("Service {} succesfully stoped", this.serviceName);
	} catch (CommandExecutionException ex) {
	    if (ex.getError().toString().contains("3521")) {
		Service.logger.info("Service {} already stoped", this.serviceName);
	    } else {
		throw ex;
	    }
	}
    }
}