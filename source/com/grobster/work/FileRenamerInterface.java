package com.grobster.work;

public interface FileRenamerInterface {
	public void renameFiles();
	public int getNumberOfFilesRenamed();
	public String createFolderName();
	public void setBaseFolderName(String directoryPath);
	public void setManualFolderName(String manualFolderName);
	public void setAutoFolderName();
	public void setFileEnding(String fileEnding);
	public String getFileEnding();
	public void registerObserver(FileRenamerObserver o);
	public void removeObserver(FileRenamerObserver o);
	public void notifyObservers();
	public String getOldFileName();
	public String getNewFileName();
	public String getFullPathName();
	public void setFullDirectoryPath(String path);
}
