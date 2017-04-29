package com.elastic.exceptions;

/**
 * Exception thrown when a shell command returned an error code. This class
 * stores the problematic command and
 * 
 * @author David
 */
public class CommandExecutionException extends Exception {

    /***/
    private static final long serialVersionUID = 1L;

    private String command;
    private StringBuffer error;

    public CommandExecutionException(String command, StringBuffer error) {
	this.command = command;
	this.error = error;
    }

    /**
     * @return The executed command
     */
    public String getCommand() {
	return command;
    }

    /**
     * @return The console error generated. It can have multiple lines
     */
    public StringBuffer getError() {
	return error;
    }
}