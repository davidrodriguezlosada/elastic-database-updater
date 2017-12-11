package com.elastic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private final List<String> fileList;
    private static final String OUTPUT_ZIP_FILE = "Folder.zip";
    private static final String SOURCE_FOLDER = "D:\\Reports"; // SourceFolder
							       // path

    public ZipUtils() {
	this.fileList = new ArrayList<>();
    }

    public static void main(String[] args) {
	ZipUtils appZip = new ZipUtils();
	appZip.generateFileList(new File(ZipUtils.SOURCE_FOLDER));
	appZip.zipIt(ZipUtils.OUTPUT_ZIP_FILE);
    }

    public void zipIt(String zipFile) {
	byte[] buffer = new byte[1024];
	String source = new File(ZipUtils.SOURCE_FOLDER).getName();
	FileOutputStream fos = null;
	ZipOutputStream zos = null;
	try {
	    fos = new FileOutputStream(zipFile);
	    zos = new ZipOutputStream(fos);

	    System.out.println("Output to Zip : " + zipFile);
	    FileInputStream in = null;

	    for (String file : this.fileList) {
		System.out.println("File Added : " + file);
		ZipEntry ze = new ZipEntry(source + File.separator + file);
		zos.putNextEntry(ze);
		try {
		    in = new FileInputStream(ZipUtils.SOURCE_FOLDER + File.separator + file);
		    int len;
		    while ((len = in.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		    }
		} finally {
		    in.close();
		}
	    }

	    zos.closeEntry();
	    System.out.println("Folder successfully compressed");

	} catch (IOException ex) {
	    ex.printStackTrace();
	} finally {
	    try {
		zos.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public void generateFileList(File node) {
	// add file only
	if (node.isFile()) {
	    this.fileList.add(this.generateZipEntry(node.toString()));
	}

	if (node.isDirectory()) {
	    String[] subNote = node.list();
	    for (String filename : subNote) {
		this.generateFileList(new File(node, filename));
	    }
	}
    }

    private String generateZipEntry(String file) {
	return file.substring(ZipUtils.SOURCE_FOLDER.length() + 1, file.length());
    }
}

// /**
// * Unzips a file to indicated folder
// *
// * @param zipFile
// * @param outputDirectory
// * @throws IOException
// */
// public static void unzip(String zipFile, String outputDirectory) throws
// IOException {
// // create a buffer to improve copy performance later.
// byte[] buffer = new byte[2048];
//
// Path outDir = Paths.get(outputDirectory);
//
// try (
// // we open the zip file using a java 7 try with resources block
// // so
// // that we don't need a finally.
// ZipInputStream stream = new ZipInputStream(new FileInputStream(zipFile))) {
// LOG.info("Zip file: " + zipFile + " has been opened");
//
// // now iterate through each file in the zip archive. The get
// // next entry call will return a ZipEntry for each file in
// // the stream
// ZipEntry entry;
// while ((entry = stream.getNextEntry()) != null) {
// // We can read the file information from the ZipEntry.
// String fileInfo = String.format("Entry: [%s] len %d added %TD",
// entry.getName(), entry.getSize(),
// new Date(entry.getTime()));
// LOG.info(fileInfo);
//
// Path filePath = outDir.resolve(entry.getName());
//
// // Now we can read the file data from the stream. We now
// // treat the stream like a usual input stream reading from
// // it until it returns 0 or less.
// try (FileOutputStream output = new FileOutputStream(filePath.toFile())) {
// LOG.info("Writing file: " + filePath);
// int len;
// while ((len = stream.read(buffer)) > 0) {
// output.write(buffer, 0, len);
// }
// }
// }
// }
// }
//
// /**
// * This method creates the ZIP archive and then goes through each file in
// * the chosen directory, adding each one to the archive. Note the use of the
// * try with resource to avoid any finally blocks.
// */
// public static void createZip(String dirName, String outputFile) {
// // the directory to be zipped
// Path directory = Paths.get(dirName);
//
// // the zip file name that we will create
// File zipFileName = Paths.get(outputFile).toFile();
//
// // open the zip stream in a try resource block, no finally needed
// try (ZipOutputStream zipStream = new ZipOutputStream(new
// FileOutputStream(zipFileName))) {
//
// // traverse every file in the selected directory and add them
// // to the zip file by calling addToZipFile(..)
// DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory);
// dirStream.forEach(path -> addToZipFile(path, zipStream));
//
// LOG.info("Zip file created in " + directory.toFile().getPath());
// } catch (IOException | ZipParsingException e) {
// LOG.log(Level.SEVERE, "Error while zipping.", e);
// }
// }
//
// /**
// * Adds an extra file to the ZIP archive, copying in the created date and a
// * comment.
// *
// * @param file
// * file to be archived
// * @param zipStream
// * archive to contain the file.
// */
// private static void addToZipFile(Path file, ZipOutputStream zipStream) {
// String inputFileName = file.toFile().getPath();
// try (FileInputStream inputStream = new FileInputStream(inputFileName)) {
//
// // create a new ZipEntry, which is basically another file
// // within the archive. We omit the path from the filename
// ZipEntry entry = new ZipEntry(file.toFile().getName());
// entry.setCreationTime(FileTime.fromMillis(file.toFile().lastModified()));
// entry.setComment("Created by TheCodersCorner");
// zipStream.putNextEntry(entry);
//
// LOG.info("Generated new entry for: " + inputFileName);
//
// // Now we copy the existing file into the zip archive. To do
// // this we write into the zip stream, the call to putNextEntry
// // above prepared the stream, we now write the bytes for this
// // entry. For another source such as an in memory array, you'd
// // just change where you read the information from.
// byte[] readBuffer = new byte[2048];
// int amountRead;
// int written = 0;
//
// while ((amountRead = inputStream.read(readBuffer)) > 0) {
// zipStream.write(readBuffer, 0, amountRead);
// written += amountRead;
// }
//
// LOG.info("Stored " + written + " bytes to " + inputFileName);
//
// } catch (IOException e) {
// throw new ZipParsingException("Unable to process " + inputFileName, e);
// }
// }
// }