package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.spec.EmptyRouterSpec;
import dev.davivieira.topologyinventory.domain.spec.EmptySwitchSpec;
import dev.davivieira.topologyinventory.domain.spec.SameCountrySpec;
import dev.davivieira.topologyinventory.domain.spec.SameIpSpec;
import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class CoreRouter extends Router{

    @Getter
    private Map<Id, Router> routers;

    @Builder
    public CoreRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType, Map<Id, Router> routers) {
        super(id, vendor, model, ip, location, routerType);
        this.routers = routers;
    }

    public Router addRouter(Router anyRouter){
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        sameCountryRouterSpec.check(anyRouter);
        sameIpSpec.check(anyRouter);

        return this.routers.put(anyRouter.getId(), anyRouter);
    }

    public Router removeRouter(Router anyRouter){
        var emptyRouterSpec = new EmptyRouterSpec();
        var emptySwitchSpec = new EmptySwitchSpec();

        switch (anyRouter.routerType){
            case CORE:
                var coreRouter = (CoreRouter) anyRouter;
                emptyRouterSpec.check(coreRouter);
                break;
            case EDGE:
                var edgeRouter = (EdgeRouter) anyRouter;
                emptySwitchSpec.check(edgeRouter);
        }
        return this.routers.remove(anyRouter.id);
    }
}
