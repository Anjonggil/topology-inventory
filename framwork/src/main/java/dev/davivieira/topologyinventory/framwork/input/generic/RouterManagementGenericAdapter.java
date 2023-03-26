package dev.davivieira.topologyinventory.framwork.input.generic;

import dev.davivieira.topologyinventory.application.usecase.RouterManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/router")
public class RouterManagementGenericAdapter {

    private final RouterManagementUseCase routerManagementUseCase;

    @GetMapping("/{id}")
    public Router retrieveRouter(@PathVariable Id id){
        return routerManagementUseCase.retrieveRouter(id);
    }

    @DeleteMapping("/{id}")
    public Router removeRouter(@PathVariable Id id){
        return routerManagementUseCase.removeRouter(id);
    }

    @PostMapping("")
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

    @PostMapping("/core-router")
    public Router addRouterToCoreRouter(Id routerId, Id coreRouterId){
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.
                addRouterToCoreRouter(router, coreRouter);
    }

    @DeleteMapping("/core-router")
    public Router removeRouterFromCoreRouter(Id routerId, Id coreRouterId){
        Router router = routerManagementUseCase.retrieveRouter(routerId);
        CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
        return routerManagementUseCase.
                removeRouterFromCoreRouter(router, coreRouter);
    }
}
