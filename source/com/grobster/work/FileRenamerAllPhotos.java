package com.grobster.work;

/*
 * @author Jeffery Quarles
 * @version 1.0.1
 * can now handle '.png' files
 */



public class FileRenamerAllPhotos implements FileRenamerInterface { // decorator class
	private FileRenamerInterface renamer; // reference to object being decorated
	private String[] photoTypes = {".jpg", ".jpeg", ".bmp", ".png"}; // different photo types/file types
	private int numberOfFilesRenamed; // this decorator has its own numberOfFilesRenamed count 
	
	public FileRenamerAllPhotos(FileRenamerInterface renamer) {
		this.renamer = renamer;
		//renamer.setBaseFolderName("C:\\Users\\quarles\\Documents\\Desktop\\j401\\"); // test take out later
	}
	
	public void renameFiles() {
		numberOfFilesRenamed = 0;
		for (int i = 0; i < photoTypes.length; i++) { // iterate over photoTypes
			renamer.setFileEnding(photoTypes[i]); // change the fileEnding
			renamer.renameFiles(); // rename the file
			numberOfFilesRenamed += renamer.getNumberOfFilesRenamed(); // increase the numberOfFilesCounted
		}
	}
	public int getNumberOfFilesRenamed() {
		return numberOfFilesRenamed;
	}
	
	public String createFolderName() { // calls method that create six-digit yearMonthDay String
		return renamer.createFolderName();
	}
	
	public void setBaseFolderName(String directoryPath) {
		renamer.setBaseFolderName(directoryPath);
	}
	
	public void setManualFolderName(String manualFolderName) {
		renamer.setManualFolderName(manualFolderName);
	}
	
	public void setAutoFolderName() {
		renamer.setAutoFolderName();
	}
	
	public void setFileEnding(String fileEnding) {
		renamer.setFileEnding(fileEnding);
	}
	
	public String getFileEnding() {
		return renamer.getFileEnding();
	}
	
	public void registerObserver(FileRenamerObserver o) {
		renamer.registerObserver(o);
	}
	public void removeObserver(FileRenamerObserver o) {
		renamer.removeObserver(o);
	}
	public void notifyObservers() {
		renamer.notifyObservers();
	}
	
	public String getOldFileName() {
		return renamer.getOldFileName();
	}
	public String getNewFileName() {
		return renamer.getNewFileName();
	}
	
	public String getFullPathName() {
		return renamer.getFullPathName();
	}
	
	public void setFullDirectoryPath(String path) {
		renamer.setFullDirectoryPath(path);
	}
}