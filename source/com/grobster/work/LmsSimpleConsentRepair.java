package com.grobster.work;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.*;

public class LmsSimpleConsentRepair {
	private Path baseDirectoryPath;
	private Path completedDirectoryPath;
	private int numberFilesRenamed;
	private String oldFileName;
	private String newFileName;
	private String fileEnding;
	private ArrayList<FileRenamerObserver> observers;
	
	public LmsSimpleConsentRepair(String directoryPathString) {
		baseDirectoryPath = Paths.get(directoryPathString);
		fileEnding = ".pdf"; // need to change as needed
		observers = new ArrayList<>();
	}
	
	public LmsSimpleConsentRepair() {
		fileEnding = ".pdf"; // need to change as needed
		observers = new ArrayList<>();
	}	
	
	public void renameFiles() {
		numberFilesRenamed = 0;
		
		try(DirectoryStream<Path> stream = Files.newDirectoryStream(completedDirectoryPath, "*" + fileEnding)){ // filter based on file type
			for (Path entry: stream) {
				int periodCount = 0;
				
				if (entry.getFileName().toString().contains("_") && !entry.getFileName().toString().contains("Photo")) { // search for files containing an underscore
					for (int i = 0; i < entry.getFileName().toString().length(); i++) {
						if (entry.getFileName().toString().charAt(i) == '.') {
							periodCount++; // increase period count
						}
					}
					
					if (periodCount == 1) { // if file only has one period do the following
						String[] tokens = entry.getFileName().toString().split("\\."); // split file on the period
						String lastFirstName = tokens[0]; // take the first part of the split
						//recreate file name
						Path target = Paths.get(completedDirectoryPath.toString() + "\\" + lastFirstName.trim() + " - Photo Consent" + fileEnding);
						Files.move(entry, target, REPLACE_EXISTING, ATOMIC_MOVE); //rename the file
						if (Files.exists(target)) { // recently added... ensures target path exists
							oldFileName = entry.getFileName().toString();
							newFileName = target.getFileName().toString();
							notifyObservers();
							numberFilesRenamed++; // increase file count
						}
					}
				}
				

			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setFullDirectoryPath(String path) {
		completedDirectoryPath = Paths.get(path);
	}
	
	public String getFullDirectoryPath() {
		return completedDirectoryPath.toString();
	}

	public void manuallyAppendFolderName(String manualFolderName) {}
	
	public void autoAppendFolderName() {}
	
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