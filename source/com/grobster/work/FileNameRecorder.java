package com.grobster.work;

import java.io.*;

public class FileNameRecorder implements FileNameRecorderInterface, FileRenamerObserver {
	private String baseFileName;
	private FileRenamerInterface renamer;
	private final String fileNameSeperator = "  <----  ";
	private final String inFolderString = "Folder: ";
	
	public FileNameRecorder(FileRenamerInterface renamer) {
		this.renamer = renamer;
		renamer.registerObserver((FileRenamerObserver) this);
		baseFileName = "_renamer_log.txt";
	}
	
	public void saveFileName(String fileName) {
		String dateString = CalendarStringCreator.createSixDigitYearMonthDay();
		StringBuilder completedFileName = new StringBuilder();
		completedFileName.append(dateString);
		completedFileName.append(baseFileName);
		
		try {
			File log = new File(completedFileName.toString());
			FileWriter fw = new FileWriter(log, true);
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write(fileName + System.getProperty("line.separator"));
			writer.close();
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