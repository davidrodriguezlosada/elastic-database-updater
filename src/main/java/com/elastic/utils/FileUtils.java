package com.elastic.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FileUtils {

    /**
     * Returns file contents of a file stored in the project folder
     * 
     * @param fileName
     * @return
     */
    public String getFileContents(String fileName) {
	return new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))
		.lines().collect(Collectors.joining("\n"));
    }
}