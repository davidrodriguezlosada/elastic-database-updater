package com.elastic.exceptions;

/**
 * This exception is used when two file dates are checked and they have been
 * created in different dates
 * 
 * @author David
 */
public class DifferentDaysException extends RuntimeException {

    /***/
    private static final long serialVersionUID = 1L;

    private String fileNameA;
    private String fileNameB;

    /**
     * @param fileNameA
     * @param fileNameB
     */
    public DifferentDaysException(String fileNameA, String fileNameB) {
	this.fileNameA = fileNameA;
	this.fileNameB = fileNameB;
    }

    public String getFileNameA() {
	return fileNameA;
    }

    public String getFileNameB() {
	return fileNameB;
    }
}
