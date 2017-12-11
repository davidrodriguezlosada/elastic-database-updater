package com.elastic.tasks.objects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.elastic.utils.ZipUtils;

/**
 * This class represents a Tomcat application and provides different methods to
 * manage it.
 *
 * @author David Rodriguez Losada
 */
public class Application {

    private static final String DATE_FORMAT = "yyyyMMdd_hhmm";

    private final String applicationName;
    private final String tomcatPath;

    public Application(String applicationName, String tomcatPath) {
	this.applicationName = applicationName;
	this.tomcatPath = tomcatPath;
    }

    /**
     * Copy the application to indicated path and
     *
     * @param backupsPath
     * @throws IOException
     */
    public void backup(String backupsPath) throws IOException {

	String source = this.getApplicationPath();

	SimpleDateFormat dateFormatter = new SimpleDateFormat(Application.DATE_FORMAT);
	String currentDate = dateFormatter.format(new Date());
	String target = backupsPath + this.applicationName + "_" + currentDate + ".zip";

	ZipUtils appZip = new ZipUtils();
	appZip.generateFileList(new File(source));
	appZip.zipIt(target);

	// ZipUtils.createZip(source, target);
    }

    /**
     * @throws IOException
     */
    public void remove() throws IOException {
	Files.delete(Paths.get(this.getApplicationPath()));
    }

    /**
     * Extracts the war to tomcat's directory
     *
     * @param warPath
     * @throws IOException
     */
    public void deploy(String warPath) throws IOException {
	// ZipUtils.unzip(warPath + applicationName, getApplicationPath());
    }

    /**
     * Returns the full path of the Tomcat application
     *
     * @return
     */
    private String getApplicationPath() {
	return this.tomcatPath + "\\webapps\\" + this.applicationName;
    }
}