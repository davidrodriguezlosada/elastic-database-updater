package com.elastic.tasks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.DifferentDaysException;
import com.elastic.properties.ContinuousDeliveryProperties;

/**
 * This class will check if the files involved in the process are correct
 * 
 * @author David
 */
public class UpdateFilesChecker {

    private static Logger logger = LoggerFactory.getLogger(UpdateFilesChecker.class);

    /**
     * Checks if war and script files exists and have been created on the same
     * date
     * 
     * @param properties
     * @throws IOException
     */
    public static void check(ContinuousDeliveryProperties properties) throws IOException, DifferentDaysException {

	logger.info("Checking update files");

	checkExistsWar(properties);

	checkFilesDates(properties);

	logger.info("Update files succesfully checked");
    }

    /**
     * Returns the path of the war file
     * 
     * @param properties
     * @return
     */
    private static Path getWarPath(ContinuousDeliveryProperties properties) {
	return Paths.get(properties.getWarPath() + properties.getTomcatAppName() + ".war");
    }

    /**
     * Returns the path of the SQL script files
     * 
     * @param properties
     * @return
     */
    private static Path getScriptsPath(ContinuousDeliveryProperties properties) {
	return Paths.get(properties.getDatabaseUpdateScriptsPath());
    }

    /**
     * Checks if war file exists
     * 
     * @param properties
     * @throws FileNotFoundException
     *             if war file doesn't exist
     */
    private static void checkExistsWar(ContinuousDeliveryProperties properties) throws FileNotFoundException {
	Path warPath = getWarPath(properties);

	if (!Files.exists(warPath)) {
	    throw new FileNotFoundException("Couldn't find war file " + properties.getWarPath());
	}
    }

    /**
     * Check if war file and all script files have the same day
     * 
     * @param properties
     * @throws IOException
     * @throws DifferentDaysException
     *             : If any file has been created in a different day. This
     *             exception will store those file names
     */
    private static void checkFilesDates(ContinuousDeliveryProperties properties)
	    throws IOException, DifferentDaysException {

	Path warPath = getWarPath(properties);

	LocalDate warCreationDate = getCreationDate(warPath);

	try (DirectoryStream<Path> paths = Files.newDirectoryStream(getScriptsPath(properties), "*.sql")) {

	    paths.forEach(filePath -> {
		if (Files.isRegularFile(filePath)) {
		    try {
			LocalDate scriptCreationDate = getCreationDate(filePath);

			if (!isSameDay(scriptCreationDate, warCreationDate)) {
			    throw new DifferentDaysException(filePath.toString(), warPath.toString());
			}
		    } catch (IOException e) {
			throw new RuntimeException(e);
		    }
		}
	    });
	}
    }

    /**
     * Returns the creation date of indicated file
     * 
     * @param file
     * @return
     * @throws IOException
     */
    private static LocalDate getCreationDate(Path file) throws IOException {
	BasicFileAttributes warAttributes = Files.readAttributes(file, BasicFileAttributes.class);
	long warCreationTime = warAttributes.creationTime().toMillis();

	LocalDate warCreationDate = new Date(warCreationTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	logger.debug("Loaded file creation date. File {}. Year: {}. Day of year: {}", file.getFileName(),
		warCreationDate.getYear(), warCreationDate.getDayOfYear());

	return warCreationDate;
    }

    /**
     * Returns true if both dates have the same day
     * 
     * @param dateA
     * @param dateB
     * @return
     */
    private static boolean isSameDay(LocalDate dateA, LocalDate dateB) {
	return dateA.getYear() == dateB.getYear() && dateA.getDayOfYear() == dateB.getDayOfYear();
    }
}
