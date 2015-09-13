package com.grobster.work;

public class TestDrive {
	public static void main(String[] args) {
		FileRenamerInterface renamer = new FileRenamerAllPhotos(new FileRenamer());
		//renamer.setBaseFolderName("C:\\Users\\quarles\\Desktop\\j401\\");
		//long startTime = System.currentTimeMillis();
		//renamer.renameFiles();
		//long stopTime = System.currentTimeMillis();
		//long elapsedTime = stopTime - startTime;
		//System.out.println("elapsed time: " + elapsedTime + " milliseconds");
		//System.out.println("Number of files renamed: " + renamer.getNumberOfFilesRenamed());
		
		RenamerView view = new RenamerView(renamer);
		view.createView();
	}
}