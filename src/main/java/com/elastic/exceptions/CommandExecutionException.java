package com.elastic.exceptions;

/**
 * Exception thrown when a shell command returned an error code. This class
 * stores the problematic command and
 *
 * @author David Rodriguez Losada
 */
public class CommandExecutionException extends Exception {

    /***/
    private static final long serialVersionUID = 1L;

    private final String command;
    private final StringBuffer error;

    public CommandExecutionException(String command, StringBuffer error) {
	this.command = command;
	this.error = error;
    }

    /**
     * @return The executed command
     */
    public String getCommand() {
	return this.command;
    }

    /**
     * @return The console error generated. It can have multiple lines
     */
    public StringBuffer getError() {
	return this.error;
    }
}