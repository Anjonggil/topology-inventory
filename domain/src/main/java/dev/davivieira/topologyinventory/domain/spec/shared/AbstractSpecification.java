package dev.davivieira.topologyinventory.domain.spec.shared;

import dev.davivieira.topologyinventory.domain.exception.GenericSpecificationException;

public abstract class AbstractSpecification<T> implements Specification<T>{
    @Override
    public abstract boolean isSatisfiedBy(T t);

    public abstract void check(T t) throws GenericSpecificationException;

    @Override
    public Specification<T> and(Specification<T> specification){
        return new AndSpecification<T>(this, specification);
    }
}
