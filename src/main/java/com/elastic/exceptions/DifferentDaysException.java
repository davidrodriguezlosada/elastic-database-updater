package com.elastic.exceptions;

/**
 * This exception is used when two file dates are checked and they have been
 * created in different dates
 *
 * @author David Rodriguez Losada
 */
public class DifferentDaysException extends RuntimeException {

    /***/
    private static final long serialVersionUID = 1L;

    private final String fileNameA;
    private final String fileNameB;

    /**
     * @param fileNameA
     * @param fileNameB
     */
    public DifferentDaysException(String fileNameA, String fileNameB) {
	this.fileNameA = fileNameA;
	this.fileNameB = fileNameB;
    }

    public String getFileNameA() {
	return this.fileNameA;
    }

    public String getFileNameB() {
	return this.fileNameB;
    }
}
