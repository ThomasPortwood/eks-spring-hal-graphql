package com.somesoftwareteam.graphql.rest;

import com.somesoftwareteam.graphql.datasources.mysql.entities.Verification;
import com.somesoftwareteam.graphql.utility.HalResourceBase;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;
import org.junit.jupiter.api.Test;

/**
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/pdf/testing-webtestclient.pdf
 * https://docs.spring.io/spring-hateoas/docs/current/reference/html/#client.web-test-client
 */
class VerificationHalApiShould extends HalResourceBase<Verification> {

    @Test
    public void createReadUpdateDelete() {
        Verification verification = new Verification("TestVerification", null, JacksonUtil.toJsonNode("{}"));
        createReadUpdateDelete(verification, "verifications");
    }
}