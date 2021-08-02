package Main;

import java.util.*;
import java.io.File;

public class ImportFiles {

	public static void listDir(String dir) {
		String Exstension = "";
		File f = new File(dir);
		File[] list = f.listFiles();
		
		  if (list == null) {
	            return;
	        }
		  
		  for (File entry : list) {

			  	// Check file ext. in Folder
				Exstension = getFileExtension(entry.getAbsolutePath());
 
				if(Exstension.equals("xml")) {
					ImportXML Import_XML = new ImportXML();
					Import_XML.Edit(entry.getAbsolutePath().toString(),entry.getName());
				}
				
				
			  
		  }
	}
	
	private static String getFileExtension(String fullName) {
		// Function for checking exstension type
		String fileName = new File(fullName).getName();
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
}


