package com.elastic.exceptions;

import com.elastic.tasks.AbstractTask;

/**
 * Common exception to be thrown when the execution of any task encountered any
 * problem that couldn't be solved.
 *
 * @author David Rodriguez Losada
 */
public class TaskExecutionException extends Exception {

    /***/
    private static final long serialVersionUID = 1L;

    AbstractTask task;

    public TaskExecutionException(AbstractTask task) {
	this.task = task;
    }
}