package com.grobster.work;

public class FileRenamerTestDriveSimpleConsentRepair {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//LmsSimpleConsentRepair consentRepair = new LmsSimpleConsentRepair("C:\\Users\\quarles\\Desktop\\LMS CH");
		//consentRepair.renameFiles();
		
		
		FileRenamerInterface renamer = new FileRenamerAllPhotosLms(new SimpleConsentRepair(new LmsSimpleConsentRepair()));
		FileNameRecorderInterface recorder = new FileNameRecorder2(renamer);
		RenamerViewConfigLms view = new RenamerViewConfigLms(renamer);
		view.createView();

			
	}

}