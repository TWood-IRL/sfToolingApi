package com.sf.metadata.api.Example;

import com.sf.metadata.api.Session.MetadataApiSession;
import com.sforce.soap.tooling.QueryResult;
import com.sforce.soap.tooling.SoapConnection;
import com.sforce.soap.tooling.sobject.SObject;
import com.sforce.ws.ConnectionException;


public class TestClass {

	public static void main(String[] args) throws ConnectionException {
		SoapConnection api = MetadataApiSession.connection() ; 
		QueryResult result = api.query("SELECT id from CustomField") ; 
		for (SObject record : result.getRecords()) {
			System.out.println(record.getId());
		}
	}
}
