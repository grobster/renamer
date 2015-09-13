package com.grobster.work;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.*;

public class FileRenamerPath {
	private Path baseDirectoryPath = Paths.get("\\\\mycommunity.ugs.com@SSL\\DavWWWRoot\\IT\\GSM\\SS\\DC\\cvgops\\PKI\\Tentech\\");
	private Path completedDirectoryPath;
	private int numberFilesRenamed;
	private String oldFileName;
	private String newFileName;
	private String fileEnding;
	private ArrayList<FileRenamerObserver> observers;
	
	public FileRenamerPath() {
		autoAppendFolderName();
		fileEnding = ".jpg";
		observers = new ArrayList<>();
	}
	
	public void renameFiles() {
		numberFilesRenamed = 0;
		
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(completedDirectoryPath, "*" + fileEnding)){
			for (Path entry: stream) {
				int periodCount = 0;
				
				for (int i = 0; i < entry.getFileName().toString().length(); i++) {
					if (entry.getFileName().toString().charAt(i) == '.') {
						periodCount++;
					}
				}
				
				if (periodCount >= 2) {
					String[] tokens = entry.getFileName().toString().split("\\.");
					String gid = tokens[periodCount - 1];
					Path target = Paths.get(completedDirectoryPath.toString() + System.getProperty("file.separator") + gid + fileEnding);
					Files.move(entry, target, REPLACE_EXISTING, ATOMIC_MOVE);
					if (Files.exists(target)) { // recently added... ensures target path exists
						oldFileName = entry.getFileName().toString();
						newFileName = gid + fileEnding;
						notifyObservers();
						numberFilesRenamed++;
					}
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setFullDirectoryPath(String path) { // changes the whole path. not gonna be used much
		completedDirectoryPath = Paths.get(path);
	}
	
	public String getFullDirectoryPath() {
		return completedDirectoryPath.toString();
	}

	public void manuallyAppendFolderName(String manualFolderName) { // will be used occasionally... when needing to change folder name based on different date
		completedDirectoryPath = Paths.get(baseDirectoryPath.toString() + System.getProperty("file.separator") + manualFolderName);
	}
	
	public void autoAppendFolderName() { // will be used most of the time
		completedDirectoryPath = Paths.get(baseDirectoryPath.toString() + System.getProperty("file.separator") + CalendarStringCreator.createSixDigitYearMonthDay());
	}
	
	public String getBaseDirectoryPath() {
		return baseDirectoryPath.toString();
	}
	
	public int getNumberFilesRenamed() {
		return numberFilesRenamed;
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
	
	public void setBaseDirectoryPath(String p) {
		baseDirectoryPath = Paths.get(p);
	}
}