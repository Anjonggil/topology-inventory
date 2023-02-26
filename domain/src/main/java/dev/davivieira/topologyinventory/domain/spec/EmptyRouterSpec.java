package dev.davivieira.topologyinventory.domain.spec;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.exception.GenericSpecificationException;
import dev.davivieira.topologyinventory.domain.spec.shared.AbstractSpecification;

public class EmptyRouterSpec extends AbstractSpecification<CoreRouter> {

    @Override
    public boolean isSatisfiedBy(CoreRouter coreRouter) {
        return coreRouter.getRouters() == null || coreRouter.getRouters().isEmpty();
    }

    @Override
    public void check(CoreRouter coreRouter) throws GenericSpecificationException {
        if(!isSatisfiedBy(coreRouter))
            throw new GenericSpecificationException("It isn't allowed to remove a core router with other routers attached to it");

    }
}
