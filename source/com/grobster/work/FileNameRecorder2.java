package com.grobster.work;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.io.*;

public class FileNameRecorder2 implements FileNameRecorderInterface, FileRenamerObserver {
	private String baseFileName;
	private FileRenamerInterface renamer;
	private final String fileNameSeperator = "  <----  ";
	private final String inFolderString = "Folder: ";
	
	public FileNameRecorder2(FileRenamerInterface renamer) {
		this.renamer = renamer;
		renamer.registerObserver((FileRenamerObserver) this);
		baseFileName = "_renamer_log.txt";
	}
	
	// this method needs work--file names are not being append to the end of the file
	public void saveFileName(String fileName) {
		String dateString = CalendarStringCreator.createSixDigitYearMonthDay();
		StringBuilder completedFileName = new StringBuilder();
		completedFileName.append(dateString);
		completedFileName.append(baseFileName);
		
		try {
			Path logDirectory = Paths.get(System.getProperty("user.home") + System.getProperty("file.separator") + "FileRenamer" + System.getProperty("file.separator") + "logs");
			if (Files.exists(logDirectory)) {
				File log = new File(completedFileName.toString());
				Path logPath = log.toPath();
				Path newCompletedPath = logDirectory.resolve(logPath);
				FileWriter fw = new FileWriter(newCompletedPath.toString(), true);
				BufferedWriter writer = new BufferedWriter(fw);
				writer.write(fileName + System.getProperty("line.separator"));
				writer.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void update() {
		String oldName = renamer.getOldFileName();
		String newName = renamer.getNewFileName();
		String fullPathFolderName = renamer.getFullPathName();
		String shortenedFolderName = FolderNameShortener.shortenFolderNameTwoLevels(fullPathFolderName);
		StringBuilder oldAndNew = new StringBuilder();
		oldAndNew.append(inFolderString + shortenedFolderName + "\t" + newName +"\t" + fileNameSeperator + "\t" + oldName);
		String oldAndNewString = oldAndNew.toString();
		saveFileName(oldAndNewString);
	}
}