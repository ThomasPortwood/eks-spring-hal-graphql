package com.somesoftwareteam.graphql.repository;

import com.somesoftwareteam.graphql.datasources.mysql.entities.Item;
import com.somesoftwareteam.graphql.datasources.mysql.repositories.ItemRepository;
import com.somesoftwareteam.graphql.utility.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Help from:
 * https://www.baeldung.com/spring-boot-testing
 * https://www.baeldung.com/spring-jpa-test-in-memory-database
 * https://vladmihalcea.com/jpa-persist-and-merge/
 * https://github.com/eugenp/tutorials/blob/master/spring-security-modules/spring-security-acl/src/test/java/com/baeldung/acl/SpringACLIntegrationTest.java
 */
public class ItemRepositoryShould extends IntegrationTestBase {

    @Autowired
    private ItemRepository repository;

    @BeforeEach
    public void before() {
        myAclService.createNewSecurityIdentityIfNecessary("google|12345");
        myAclService.createNewSecurityIdentityIfNecessary("google|54321");
    }

    @Test
    @WithMockUser(username = "google|12345", authorities = {"SCOPE_read:items"})
    public void findAllForGroupMember() {
        Item item = itemBuilder.createNewItemWithDefaults().persist().build();
        accessControlListBuilder.configureAccessControlList("google|12345", Item.class, item.getId());
        Page<Item> resultFromFindAll = repository.findAll(PageRequest.of(0, 10));
        assertThat(resultFromFindAll.getContent().size()).isGreaterThan(0);
    }

    @Test
    @WithMockUser(username = "google|54321", authorities = {"SCOPE_read:items"})
    public void findNoneForNonGroupMember() {
        Item item = itemBuilder.createNewItemWithDefaults().persist().build();
        accessControlListBuilder.configureAccessControlList("google|12345", Item.class, item.getId());
        Page<Item> resultFromFindAll = repository.findAll(PageRequest.of(0, 10));
        assertThat(resultFromFindAll.getContent().size()).isEqualTo(0);
    }

    @Test
    @WithMockUser(username = "google|12345", authorities = {"SCOPE_read:items"})
    public void findByIdForGroupMember() {
        Item item = itemBuilder.createNewItemWithDefaults().persist().build();
        accessControlListBuilder.configureAccessControlList("google|12345", Item.class, item.getId());
        Item resultFromFindById = repository.findById(item.getId()).orElseThrow(ResourceNotFoundException::new);
        assertThat(resultFromFindById.getId()).isEqualTo(item.getId());
    }
}
