package dev.davivieira.topologyinventory.framwork.input.generic;

import dev.davivieira.topologyinventory.application.port.input.RouterManagementInputPort;
import dev.davivieira.topologyinventory.application.usecase.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framwork.output.RouterManagementH2Adapter;

public class RouterManagementGenericAdapter {

    private RouterManagementUseCase routerManagementUseCase;

    public RouterManagementGenericAdapter() {
        setPorts();
    }

    private void setPorts() {
        this.routerManagementUseCase = new RouterManagementInputPort(RouterManagementH2Adapter.getInstance());
    }

    public Router retrieveRouter(Id id){
        return routerManagementUseCase.retrieveRouter(id);
    }

    public Router removeRouter(Id id){
        return routerManagementUseCase.removeRouter(id);
    }

    public Router createRouter(Vendor vendor,
                               Model model,
                               IP ip,
                               Location location,
                               RouterType routerType){
        var router = routerManagementUseCase.createRouter(
                vendor,
                model,
                ip,
                location,
                routerType
        );
        return routerManagementUseCase.persistRouter(router);
    }

    public Router addRouterToCoreRouter(Id routerId, Id coreRouterId){
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.
                addRouterToCoreRouter(router, coreRouter);
    }

    public Router removeRouterFromCoreRouter(Id routerId, Id coreRouterId){
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.
                removeRouterFromCoreRouter(router, coreRouter);
    }
}
