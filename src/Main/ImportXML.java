package Main;

import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ImportXML {

	private static final Document Document = null;
	
	// Import XML Path
	public static void Edit(String XML_Path, String FileName) {
		
		//XML PATH C:\work\Split_Documents\1-247_U_I-614_84.xml

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder;
	    String XML_String_Path = XML_Path;
			
	        try {
	            dBuilder = dbFactory.newDocumentBuilder();

	            // parse xml file and load into document
	            Document doc = dBuilder.parse(XML_String_Path);


	            doc.getDocumentElement().normalize();
	            

	            // update Element value
	            // Working on split docs in two folders founded and watcher
	           //updateElementValue(doc,FileName); TEMP COMMENT


	            // add new element
	            addElement(doc);
	            
	            // write the updated document to file or console
	            writeXMLFile(doc,FileName);

	        } catch (SAXException | ParserConfigurationException | IOException e1) {
	            e1.printStackTrace();
	        }

	}

	private static void writeXMLFile(Document doc, String FileName) {
		System.out.println("############ writeXMLFile ######## ") ;
		  doc.getDocumentElement().normalize();
			  
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = null;
			try {
				transformer = transformerFactory.newTransformer();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new File("C:\\work\\Split_Documents\\Added\\"+FileName));
	        transformer.setOutputProperty(OutputKeys.INDENT, "no");
	        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
	        try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
	        System.out.println("XML file updated successfully");
		
	}
	
    /**
     * Add a new element salary to user element.
     * @param doc
     */
    private static void addElement(Document doc) {
        NodeList users = doc.getElementsByTagName("Predmet");
        Element emp = null;

        // loop for each user
        for (int i = 0; i < users.getLength(); i++) {
            emp = (Element) users.item(i);
            Element salaryElement = doc.createElement("Sud_Id");
            salaryElement.appendChild(doc.createTextNode("Brčko"));
            emp.appendChild(salaryElement);
        }
    }

	private static void updateElementValue(Document doc,String FileName) {
		
		//Settings for Select files
		String SourceDirectory = "C:\\work\\Split_Documents";
		
		File SourceFile = new File(SourceDirectory +"\\"+ FileName); // XML
		File SourceFile_Dir = new File(SourceDirectory +"\\"+ FileName.replace(".xml", "")); // Folder
		
		NodeList PravnaFormaSubjekta = doc.getElementsByTagName("Predmet");
		Element PravnaForma_e = null;
		for (int i = 0; i < PravnaFormaSubjekta.getLength(); i++) {
			PravnaForma_e = (Element) PravnaFormaSubjekta.item(i);
        	Node PravnaForma_Name = PravnaForma_e.getElementsByTagName("PravnaFormaSubjekta").item(0).getFirstChild();
        	
        	String PravnaForma_Value = PravnaForma_Name.getTextContent().toString();
        	
        	System.out.println ("Working on Document " + FileName) ;
        	// Filter 
        	if(
        			PravnaForma_Value.equals("Radna organizacija") || 
        			PravnaForma_Value.equals("Ostali oblik organizovanja")
        		) {

        		File DestinationFile = new File(SourceDirectory + "\\Found\\" + FileName); 
        		File DestinationFile_Dir = new File(SourceDirectory +"\\Found\\"+ FileName.replace(".xml", "")); // Folder
        		
        		System.out.println("DestinationFile : " + DestinationFile) ; 
        		System.out.println("DestinationFile_Dir : " + DestinationFile_Dir) ; 
        		
        		try {
					Files.move(SourceFile.toPath(), DestinationFile.toPath(), REPLACE_EXISTING);
					Files.move(SourceFile_Dir.toPath(), DestinationFile_Dir.toPath(), REPLACE_EXISTING); // Transfer Directory
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    			
        	} else {
        		File DestinationFile = new File(SourceDirectory + "\\Viewed\\" + FileName); 
        		File DestinationFile_Dir = new File(SourceDirectory +"\\Viewed\\"+ FileName.replace(".xml", "")); // Folder
        		
        		System.out.println("DestinationFile : " + DestinationFile) ; 
        		System.out.println("DestinationFile_Dir : " + DestinationFile_Dir) ; 
        		
        		try {
					Files.move(SourceFile.toPath(), DestinationFile.toPath(), REPLACE_EXISTING);
					Files.move(SourceFile_Dir.toPath(), DestinationFile_Dir.toPath(), REPLACE_EXISTING); // Transfer Directory
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        	}
        		
   	
		}
		// TODO Auto-generated method stub
		
	}
}
