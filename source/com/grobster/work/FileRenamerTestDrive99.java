package com.grobster.work;

public class FileRenamerTestDrive99 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileRenamerInterface renamer = new FileRenamerAllPhotos(new FileRenamerRevised(new FileRenamerPath()));
		FileNameRecorderInterface recorder = new FileNameRecorder2(renamer);
		//RenamerView11 view = new RenamerView11(renamer); commented on 7/22/14 to test new config view
		RenamerViewConfig view = new RenamerViewConfig(renamer);
		view.createView();

			
	}

}