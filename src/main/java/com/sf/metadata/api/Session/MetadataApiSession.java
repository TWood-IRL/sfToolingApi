
package com.sf.metadata.api.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import com.sf.metadata.api.Properties.PropertiesFile;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;



public class MetadataApiSession {
	private static PartnerConnection connection;
	private static final Logger LOG = Logger.getLogger(MetadataApiSession.class.getName()) ; 
	private static  String org = "" ; 
	private static MetadataApiSession instance; 

	private static final String PROD_AUTH = "https://login.salesforce.com/services/Soap/u/41.0/"  ; 
	private static final String SANDBOX_AUTH = "https://test.salesforce.com/services/Soap/u/41.0/"  ; 

	
	private static PropertiesFile props = new PropertiesFile() ;
	

 	private MetadataApiSession() {
		org = PropertiesFile.org ;
		File apiLog = new File("./APILoggingFile.log") ;
		String endPoint = (props.getType()  == "SANDBOX" ) ? SANDBOX_AUTH : PROD_AUTH ; 
		if(apiLog.exists()) // if the file exists clear contents
		{
			try {
				Files.delete(apiLog.toPath());
			} catch (IOException e) {
				LOG.warning("Error Deleting existing API Log File !!");
			}
			
		}
	    ConnectorConfig config = new ConnectorConfig();
	    config.setUsername(props.getUsername());
	    config.setPassword(props.getPassword());
	    config.setTraceMessage(true);
	    config.setPrettyPrintXml(true);
	    config.setAuthEndpoint(endPoint);
	    
	    try {
			config.setTraceFile(apiLog.getAbsolutePath());
		} catch (FileNotFoundException e) {
			
		}


	    try {     
	    	LOG.info("API Performing Connection ! to " +org);
	    	 connection = Connector.newConnection(config);
	    	 
	    	 LOG.info("API Connnected Successfully ! - Refer to Log for more information - " + apiLog.getAbsolutePath());
	      } catch (ConnectionException e1) {
	    	  LOG.warning("API Connection UnSuccessfull ! - Check properties file \nExiting Application\n" +e1 );
	    	  
	    	  System.exit(1);
	      }  
	}
}
