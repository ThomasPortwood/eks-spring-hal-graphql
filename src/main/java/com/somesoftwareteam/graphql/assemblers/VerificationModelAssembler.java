package com.somesoftwareteam.graphql.assemblers;

import com.somesoftwareteam.graphql.entities.Verification;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class VerificationModelAssembler implements RepresentationModelAssembler<Verification, EntityModel<Verification>> {

    private final RepositoryEntityLinks repositoryEntityLinks;

    public VerificationModelAssembler(RepositoryEntityLinks repositoryEntityLinks) {
        this.repositoryEntityLinks = repositoryEntityLinks;
    }

    @NotNull
    @Override
    public EntityModel<Verification> toModel(@NotNull Verification verification) {
        return EntityModel.of(verification,
                repositoryEntityLinks.linkToItemResource(Verification.class, verification.getId()).withSelfRel(),
                repositoryEntityLinks.linkToCollectionResource(Verification.class).withRel("verifications"));
    }
}