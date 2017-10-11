package com.redhat.bpms.demo.fsi.onboarding.marshalling;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jbpm.document.Document;
import org.jbpm.document.marshalling.DocumentMarshallingStrategy;
import org.jbpm.document.service.impl.DocumentImpl;
import org.junit.Test;

import com.redhat.bpms.demo.fsi.onboarding.model.Documents;

public class DocumentsMarshallingStrategyTest {
	
	private DocumentsMarshallingStrategy docsMarshallingStrategy = new DocumentsMarshallingStrategy(new DocumentMarshallingStrategy());
	
	@Test
	public void testMarshallUnmarshall() throws IOException, ClassNotFoundException {
		Documents docs = new Documents();
		docs.setDocuments(getDocuments());
		
		byte[] marshalledDocs = docsMarshallingStrategy.marshal(null, null, docs);
		
		Documents unmarshalledDocs = (Documents) docsMarshallingStrategy.unmarshal(null, null, marshalledDocs, this.getClass().getClassLoader());
		
		assertEquals(docs.getDocuments().size(), unmarshalledDocs.getDocuments().size());
		
		List<Document> unmarshalledDocumentsList = unmarshalledDocs.getDocuments();
		
		assertEquals(unmarshalledDocumentsList.get(0).getName(), docs.getDocuments().get(0).getName());
		assertEquals(unmarshalledDocumentsList.get(0).getLink(), docs.getDocuments().get(0).getLink());
		assertEquals(unmarshalledDocumentsList.get(1).getName(), docs.getDocuments().get(1).getName());
		assertEquals(unmarshalledDocumentsList.get(1).getLink(), docs.getDocuments().get(1).getLink());
	}
	
	
	@Test
	public void testSingleDocMarshallUnmarshall() throws IOException, ClassNotFoundException {
		DocumentMarshallingStrategy docMarshallingStrategy = new DocumentMarshallingStrategy();
		Document document = getDocument();
		byte[] marshalledDocument = docMarshallingStrategy.marshal(null, null, document);
		Document unmarshalledDocument = (Document) docMarshallingStrategy.unmarshal(null, null, marshalledDocument, this.getClass().getClassLoader());
	
		assertEquals(document.getName(), unmarshalledDocument.getName());
		assertEquals(document.getLink(), unmarshalledDocument.getLink());
		
	}
	
	
	private Document getDocument() {
		Document documentOne = new DocumentImpl();
		documentOne.setIdentifier("docOne");
		documentOne.setLastModified(new Date());
		documentOne.setLink("http://documentOne");
		documentOne.setName("DocumentOne Name");
		documentOne.setSize(1);
		documentOne.setContent("documentOne".getBytes());	
		return documentOne;
	}
	
	private List<Document> getDocuments() {
		
		List<Document> documents = new ArrayList<>();
		
		
		Document documentOne = new DocumentImpl();
		documentOne.setIdentifier("docOne");
		documentOne.setLastModified(new Date());
		documentOne.setLink("http://documentOne");
		documentOne.setName("DocumentOne Name");
		documentOne.setSize(1);
		documentOne.setContent("documentOne".getBytes());
		
		documents.add(documentOne);
		
		
		Document documentTwo = new DocumentImpl();
		documentTwo.setIdentifier("docTwo");
		documentTwo.setLastModified(new Date());
		documentTwo.setLink("http://documentTwo");
		documentTwo.setName("DocumentTwo Name");
		documentTwo.setSize(1);
		documentTwo.setContent("documentTwo".getBytes());
		
		documents.add(documentTwo);
		
		return documents;
	}

}
