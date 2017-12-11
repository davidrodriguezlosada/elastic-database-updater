package com.elastic.tasks.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.CommandExecutionException;
import com.elastic.properties.DatabaseProperties;
import com.elastic.utils.CommandLineUtils;
import com.elastic.utils.FileUtils;

/**
 * This class provides methods to administer a SQLServer database.
 *
 * @author David Rodriguez Losada
 */
public class Database {

    private static Logger logger = LoggerFactory.getLogger(Database.class);

    private static final String DATE_FORMAT = "yyyyMMdd_HHmm";

    private static final String RESTORATION_FILE = "restore_database_script.sql";

    private static final String RESTORATION_FILE_DATABASE_NAME_TAG = "%DATABASE_NAME%";

    private static final String RESTORATION_FILE_BACKUP_PATH_TAG = "%BACKUP_PATH%";

    private final DatabaseProperties databaseProperties;

    public Database(DatabaseProperties databaseProperties) {
	this.databaseProperties = databaseProperties;
    }

    /**
     * Backups current database to indicated folder. This method will generate
     * the file name as the concatenation of database's name plus actual date.
     *
     * @param backupFolder
     * @return The full path of the resulting file
     * @throws IOException
     * @throws InterruptedException
     * @throws CommandExecutionException
     */
    public String backupDatabase(String backupFolder)
	    throws IOException, InterruptedException, CommandExecutionException {

	String backupFileName = this.getBackupFileName();

	return this.backupDatabase(backupFolder, backupFileName);
    }

    private String getBackupFileName() {
	SimpleDateFormat dateFormatter = new SimpleDateFormat(Database.DATE_FORMAT);
	String currentDate = dateFormatter.format(new Date());

	String backupFileName = this.databaseProperties.getName() + "_" + currentDate + ".bak";
	return backupFileName;
    }

    /**
     * Backups current database to indicated folder and file name
     *
     * @param backupFolder
     * @param backupFileName
     * @return The full path of the resulting file
     * @throws IOException
     * @throws InterruptedException
     * @throws CommandExecutionException
     */
    public String backupDatabase(String backupFolder, String backupFileName)
	    throws IOException, InterruptedException, CommandExecutionException {

	Database.logger.info("Backup database {}", this.databaseProperties.getName());

	String fullBackupPath = backupFolder + backupFileName;

	String command = this.getCommandLineExpressionForQuery(
		"BACKUP DATABASE " + this.databaseProperties.getName() + " TO DISK='" + fullBackupPath + "'");

	CommandLineUtils.executeCommand(command);

	Database.logger.info("Database {} succesfully backup", this.databaseProperties.getName());

	return fullBackupPath;
    }

    /**
     * Restores this database with indicated file
     *
     * @param backupFile
     * @throws InterruptedException
     * @throws IOException
     * @throws CommandExecutionException
     * @throws URISyntaxException
     */
    public void restoreDatabase(String backupFile)
	    throws IOException, InterruptedException, CommandExecutionException, URISyntaxException {

	Database.logger.info("Restoring database {}", this.databaseProperties.getName());

	File tempFile = this.getRestorationScriptFile(backupFile);

	String command = this.getCommandLineExpressionForFile(tempFile.getAbsolutePath());

	CommandLineUtils.executeCommand(command);

	Database.logger.info("Database {} succesfully restored", this.databaseProperties.getName());
    }

    private File getRestorationScriptFile(String backupFile) throws IOException, FileNotFoundException {
	String restorationScript = new FileUtils().getFileContents(Database.RESTORATION_FILE);

	restorationScript = restorationScript
		.replaceAll(Database.RESTORATION_FILE_DATABASE_NAME_TAG, "'" + this.databaseProperties.getName() + "'")
		.replaceAll(Database.RESTORATION_FILE_BACKUP_PATH_TAG, "'" + backupFile.replace("\\", "\\\\") + "'");

	File tempFile = File.createTempFile(Database.RESTORATION_FILE, ".sql");

	try (PrintWriter out = new PrintWriter(tempFile)) {
	    out.println(restorationScript);
	}
	return tempFile;
    }

    /**
     * Execute all SQL script files in indicated path over current database
     *
     * @param path
     * @throws IOException
     */
    public void executeScripts(String path) throws IOException {
	try (DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get(path), "*.sql")) {
	    paths.forEach(filePath -> {
		if (Files.isRegularFile(filePath)) {
		    try {
			Database.logger.info("Executing script {} on database {}", filePath.toString(),
				this.databaseProperties.getName());

			CommandLineUtils
				.executeCommand(this.getCommandLineExpressionForFileWithDatabase(filePath.toString()));

			Database.logger.info("Script {} succesfully executed on database {}", filePath.toString(),
				this.databaseProperties.getName());
		    } catch (IOException | InterruptedException | CommandExecutionException e) {
			throw new RuntimeException(e);
		    }
		}
	    });
	}
    }

    /**
     * This method will return the command line sentence needed to execute
     * indicated query in current database.
     *
     * @param sqlQuery
     * @return
     */
    private String getCommandLineExpressionForQuery(String sqlQuery) {
	return "SQLCMD -S " + this.databaseProperties.getUrl() + " -U " + this.databaseProperties.getUser() + " -P "
		+ this.databaseProperties.getPassword() + " -Q \"" + sqlQuery + "\"";
    }

    /**
     * This method will return the command line sentence needed to execute
     * indicated script file in current database.
     *
     * @param scriptFilePath
     * @return
     */
    private String getCommandLineExpressionForFile(String scriptFilePath) {
	return "SQLCMD -S " + this.databaseProperties.getUrl() + " -U " + this.databaseProperties.getUser() + " -P "
		+ this.databaseProperties.getPassword() + " -i " + scriptFilePath;
    }

    /**
     * This method will return the command line sentence needed to execute
     * indicated script file in current database.
     *
     * @param scriptFilePath
     * @return
     */
    private String getCommandLineExpressionForFileWithDatabase(String scriptFilePath) {
	return "SQLCMD -S " + this.databaseProperties.getUrl() + " -U " + this.databaseProperties.getUser() + " -P "
		+ this.databaseProperties.getPassword() + " -d " + this.databaseProperties.getName() + " -i "
		+ scriptFilePath;
    }
}