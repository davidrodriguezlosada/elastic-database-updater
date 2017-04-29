package com.elastic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.CommandExecutionException;

public class CommandLineUtils {

    private static Logger logger = LoggerFactory.getLogger(CommandLineUtils.class);

    /**
     * Executes indicated command into shell.<br>
     * Both errors and info messages will be logged.
     * 
     * @param command
     * @throws IOException
     * @throws InterruptedException
     * @throws CommandExecutionException
     */
    public static void executeCommand(String command)
	    throws IOException, InterruptedException, CommandExecutionException {
	StringBuffer correctResponse = new StringBuffer();
	StringBuffer errorResponse = new StringBuffer();
	String line;

	logger.debug("Executing command: {}", command);

	Process p = Runtime.getRuntime().exec(command);
	p.waitFor();

	BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

	while ((line = reader.readLine()) != null) {
	    correctResponse.append(line + "\n");
	    logger.debug(line);
	}

	while ((line = error.readLine()) != null) {
	    errorResponse.append(line + "\n");
	    logger.error(line);
	}

	if (errorResponse.length() != 0) {
	    throw new CommandExecutionException(command, errorResponse);
	}
    }
}
