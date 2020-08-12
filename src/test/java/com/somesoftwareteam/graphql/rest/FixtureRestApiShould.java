package com.somesoftwareteam.graphql.rest;

import com.somesoftwareteam.graphql.datasources.mysql.entities.Fixture;
import org.junit.jupiter.api.Test;

/**
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/pdf/testing-webtestclient.pdf
 * https://docs.spring.io/spring-hateoas/docs/current/reference/html/#client.web-test-client
 */
class FixtureRestApiShould extends RestApiResourceBase<Fixture> {

    @Test
    public void createReadUpdateDelete() {
        Fixture fixture = fixtureBuilder.createNewEntityWithDefaults().build();
        createReadUpdateDelete(fixture, "fixtures");
    }
}
