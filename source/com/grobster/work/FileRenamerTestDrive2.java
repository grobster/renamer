package com.grobster.work;

public class FileRenamerTestDrive2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileRenamerInterface renamer = new FileRenamerAllPhotos(new FileRenamer());
		FileNameRecorderInterface recorder = new FileNameRecorder(renamer);
		RenamerView view = new RenamerView(renamer);
		view.createView();

			
	}

}
