package com.grobster.work;

public class FileRenamerRevised implements FileRenamerInterface { // this is an adapter class for FileRenamer. it contains cleaner code
	private FileRenamerPath fileRenamerPath;
	
	public FileRenamerRevised(FileRenamerPath fileRenamerPath) {
		this.fileRenamerPath = fileRenamerPath;
	}

	public void renameFiles() {
		fileRenamerPath.renameFiles();
	}
	
	public int getNumberOfFilesRenamed() {
		return fileRenamerPath.getNumberFilesRenamed();
	}
	
	public void setBaseFolderName(String directoryPath){
		fileRenamerPath.setBaseDirectoryPath(directoryPath);
	} 
	
	public void setManualFolderName(String manualFolderName) {
		fileRenamerPath.manuallyAppendFolderName(manualFolderName);
	}
	
	public void setAutoFolderName() {
		fileRenamerPath.autoAppendFolderName();
	}
	
	public void setFileEnding(String fileEnding) {
		fileRenamerPath.setFileEnding(fileEnding);
	}
	
	public void registerObserver(FileRenamerObserver o) {
		fileRenamerPath.registerObserver(o);
	}
	public void removeObserver(FileRenamerObserver o) {
		fileRenamerPath.removeObserver(o);
	}
	public void notifyObservers() {
		fileRenamerPath.notifyObservers();
	}
	public String getOldFileName() {
		return fileRenamerPath.getOldFileName();
	}
	public String getNewFileName() {
		return fileRenamerPath.getNewFileName();
	}
	public String getFullPathName() {
		return fileRenamerPath.getFullDirectoryPath();
	}
	
	public String createFolderName() { // not needed
		return null;
	}
	public String getFileEnding() {
		return fileRenamerPath.getFileEnding();
	}
	
	public void setFullDirectoryPath(String path) {
		fileRenamerPath.setFullDirectoryPath(path);
	}
}