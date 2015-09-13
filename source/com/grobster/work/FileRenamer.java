package com.grobster.work;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import static java.nio.file.StandardCopyOption.*;
import java.util.*;

/* @version 10.0.1
 * @author Jeffery Quarles
*/

public class FileRenamer implements FileRenamerInterface {
	private String autoCreatedFolderName;
	private Path baseFolderName;
	private Path fullPathName;
	private int numberOfFilesRenamed;
	private String fileEnding;
	private ArrayList<FileRenamerObserver> observers;
	private String oldFileName;
	private String newFileName;
	
	public FileRenamer() {
		baseFolderName = Paths.get("\\\\mycommunity.ugs.com@SSL\\DavWWWRoot\\IT\\GSM\\SS\\DC\\cvgops\\PKI\\Tentech\\");
		numberOfFilesRenamed = 0;
		fileEnding = ".jpg";
		System.out.println(baseFolderName);
		fullPathName = Paths.get(baseFolderName.toString());
		observers = new ArrayList<FileRenamerObserver>();
	}
	
	public void renameFiles() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		numberOfFilesRenamed = 0;
		
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(fullPathName, "*" + fileEnding)) { // using Paths and filtering all jpegs in a directory
			for (Path entry: stream) { // iterate over path
				int periodCount = 0;
				
				for (int i = 0; i < entry.getFileName().toString().length(); i++) {
					if (entry.getFileName().toString().charAt(i) == '.') {
						periodCount++;
					}
				}
				
				if (periodCount >= 2) {
					String[] tokens = entry.getFileName().toString().split("\\.");
					String gid = tokens[periodCount - 1];
					Path target = Paths.get(fullPathName.toString() + "\\" + gid + fileEnding);
					Files.move(entry, target, REPLACE_EXISTING, ATOMIC_MOVE);
					oldFileName = entry.getFileName().toString();
					newFileName = gid + fileEnding;
					if (Files.exists(target)) {
						notifyObservers();
						numberOfFilesRenamed++;
					}
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Elapsed Time: " + elapsedTime + " milliseconds");
	}

	public int getNumberOfFilesRenamed() {
		// TODO Auto-generated method stub
		return numberOfFilesRenamed;
	}

	public String createFolderName() { 
		autoCreatedFolderName = CalendarStringCreator.createSixDigitYearMonthDay(); // calls static method to create folderName
		return autoCreatedFolderName;
	}

	public void setBaseFolderName(String directoryPath) { // won't be called much if at all -- future functionality
		// TODO Auto-generated method stub
		fullPathName = Paths.get(directoryPath);
	}

	public void setManualFolderName(String manualFolderName) { // call when manual want to change folder name
		// TODO Auto-generated method stub
		String manFolder = baseFolderName.toString() + "\\" + manualFolderName + "\\";
		fullPathName = Paths.get(manFolder);
		System.out.println(fullPathName);
	}

	public void setAutoFolderName() { // call when automatically want to change folder name based on date
		// TODO Auto-generated method stub
		createFolderName();
		String autoFolder = baseFolderName.toString() + "\\" + autoCreatedFolderName + "\\";
		fullPathName = Paths.get(autoFolder);
		System.out.println(fullPathName);
	}
	
	public void setFileEnding(String fileEnding) {
		this.fileEnding = fileEnding;
	}
	public String getFileEnding() {
		return fileEnding;
	}
	
	public void registerObserver(FileRenamerObserver o) {
		observers.add(o);
	}
	public void removeObserver(FileRenamerObserver o) {
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			FileRenamerObserver observer = (FileRenamerObserver) observers.get(i);
			observer.update();
		}
	}
	
	public String getOldFileName() {
		return oldFileName;
	}
	
	public String getNewFileName() {
		return newFileName;
	}
	
	public String getFullPathName() {
		return fullPathName.toString();
	}
	
	public void setFullDirectoryPath(String path) {
		fullPathName = Paths.get(path);
	}

}
