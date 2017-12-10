package com.elastic;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.DifferentDaysException;
import com.elastic.exceptions.UnknownTaskException;
import com.elastic.pipeline.Pipeline;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.objects.UpdateFilesChecker;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
	Pipeline pipeline;

	logger.info("INIT MAIN PROCESS");

	try {
	    ContinuousDeliveryProperties properties = new ContinuousDeliveryProperties();

	    UpdateFilesChecker.check(properties);

	    pipeline = new Pipeline(properties);

	    pipeline.run();

	    logger.info("MAIN PROCESS SUCCESFULY FINISHED");

	} catch (UnknownTaskException | IOException | DifferentDaysException e) {
	    logger.error(e.getMessage(), e);

	    // TODO: Send mail :
	    // http://stackoverflow.com/questions/3649014/send-email-using-java

	    // TODO: Rollback all process
	}
    }
}