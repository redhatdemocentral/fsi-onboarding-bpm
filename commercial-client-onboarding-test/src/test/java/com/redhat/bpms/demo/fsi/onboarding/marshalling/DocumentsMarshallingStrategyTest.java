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
	public void testSingleDocMarshalUnmarshal() throws IOException, ClassNotFoundException {
		DocumentMarshallingStrategy docMarshallingStrategy = new DocumentMarshallingStrategy();
		Document document = getDocument("documentOne");
		byte[] marshalledDocument = docMarshallingStrategy.marshal(null, null, document);
		Document unmarshalledDocument = (Document) docMarshallingStrategy.unmarshal(null, null, marshalledDocument, this.getClass().getClassLoader());
	
		assertEquals(document.getName(), unmarshalledDocument.getName());
		assertEquals(document.getLink(), unmarshalledDocument.getLink());
	}
	
	@Test
	public void testNoDocumentsMarshallUnmarshall() throws IOException, ClassNotFoundException {
		Documents docs = new Documents();
		
		byte[] marshalledDocuments = docsMarshallingStrategy.marshal(null, null, docs);
		Documents unmarshalledDocuments = (Documents) docsMarshallingStrategy.unmarshal(null, null, marshalledDocuments, this.getClass().getClassLoader());
	
		assertEquals(docs.getDocuments().size(), unmarshalledDocuments.getDocuments().size());
		
	}
	
	
	private Document getDocument(String documentName) {
		Document documentOne = new DocumentImpl();
		documentOne.setIdentifier(documentName);
		documentOne.setLastModified(new Date());
		documentOne.setLink("http://" +  documentName);
		documentOne.setName(documentName + " Name");
		documentOne.setSize(1);
		documentOne.setContent(documentName.getBytes());	
		return documentOne;
	}
	
	private List<Document> getDocuments() {
		
		List<Document> documents = new ArrayList<>();
		
		documents.add(getDocument("documentOne"));
		documents.add(getDocument("documentTwo"));
		
		return documents;
	}

}
