package com.somesoftwareteam.graphql.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.somesoftwareteam.graphql.datasources.mysql.entities.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class DocumentBuilder {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    private ObjectNode attributes;
    private Document document;

    public DocumentBuilder createNewEntityWithDefaults() {
        attributes = objectMapper.createObjectNode();
        document = new Document();
        document.setAttributes(attributes);
        document.setDescription("my document description");
        document.setName("my property");
        document.setOwnerId("google|12345");
        document.setUrl("dummy url");
        return this;
    }

    public DocumentBuilder useAttribute(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    @Transactional
    public DocumentBuilder persist() {
        entityManager.persist(document);
        return this;
    }

    public Document build() {
        return document;
    }
}
