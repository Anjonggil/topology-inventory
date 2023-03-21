package dev.davivieira.topologyinventory.application.port.input;

import dev.davivieira.topologyinventory.application.port.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.application.usecase.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RouterManagementInputPort implements RouterManagementUseCase {
    RouterManagementOutputPort routerManagementOutputPort;

    public RouterManagementInputPort(RouterManagementOutputPort routerNetworkOutputPort){
        this.routerManagementOutputPort = routerNetworkOutputPort;
    }

    @Override
    public Router createRouter(Vendor vendor, Model model, IP ip, Location location, RouterType routerType) {
        return RouterFactory.getRouter(null, vendor, model, ip, location, routerType);
    }

    @Override
    public CoreRouter addRouterToCoreRouter(Router router, CoreRouter coreRouter) {
        return (CoreRouter) coreRouter.addRouter(router);
    }

    @Override
    public Router removeRouterFromCoreRouter(Router router, CoreRouter coreRouter) {
        return coreRouter.removeRouter(router);
    }

    @Override
    public Router retrieveRouter(Id id) {
        return routerManagementOutputPort.retrieveRouter(id);
    }

    @Override
    public Router persistRouter(Router router) {
        return routerManagementOutputPort.persistRouter(router);
    }

    @Override
    public Router removeRouter(Id id) {
        return null;
    }
}
