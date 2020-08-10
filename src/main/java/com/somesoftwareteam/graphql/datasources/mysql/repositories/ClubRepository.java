package com.somesoftwareteam.graphql.datasources.mysql.repositories;

import com.somesoftwareteam.graphql.datasources.mysql.entities.Club;
//import com.somesoftwareteam.graphql.datasources.mysql.entities.projections.ClubWithClubMembers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Security provides Spring Data integration that allows referring to the current user within your queries:
 * https://docs.spring.io/spring-security/site/docs/4.0.x/reference/htmlsingle/#data
 * https://docs.spring.io/spring-security/site/docs/4.0.x/reference/htmlsingle/#common-expressions
 * https://thorben-janssen.com/jpql/
 */
//@RepositoryRestResource(excerptProjection = ClubWithClubMembers.class)
@Repository
public interface ClubRepository extends JpaRepository<Club, UUID>, JpaSpecificationExecutor<Club> {

    @Query("select c from Club c join c.clubMembers m where m.memberId = :#{ authentication.name }")
    Page<Club> findAll(Pageable pageable);
}