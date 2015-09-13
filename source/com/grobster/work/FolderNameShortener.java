package com.grobster.work;

public class FolderNameShortener {
	public static String shortenFolderName(String folderName) {
		String[] tokens = folderName.split("\\\\");
		String shortenedFolderName = tokens[(tokens.length - 1)];
		return shortenedFolderName;
	}
	
	public static String shortenFolderNameTwoLevels(String folderName) {
		String[] tokens = folderName.split("\\\\");
		String levelOne = tokens[(tokens.length - 2)];
		String levelTwo = tokens[(tokens.length - 1)];
		String twoLevelFolderName = levelOne + "\\" + levelTwo;
		return twoLevelFolderName;
	}
}