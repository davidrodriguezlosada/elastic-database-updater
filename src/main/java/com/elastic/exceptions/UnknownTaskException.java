package com.elastic.exceptions;

public class UnknownTaskException extends Exception {

    /***/
    private static final long serialVersionUID = 1L;

    String taskName;

    public UnknownTaskException(String taskName) {
	this.taskName = taskName;
    }
}