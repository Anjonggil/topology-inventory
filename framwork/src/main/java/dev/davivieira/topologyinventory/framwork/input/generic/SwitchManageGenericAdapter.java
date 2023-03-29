package dev.davivieira.topologyinventory.framwork.input.generic;

import dev.davivieira.topologyinventory.application.port.input.RouterManagementInputPort;
import dev.davivieira.topologyinventory.application.port.input.SwitchManagementInputPort;
import dev.davivieira.topologyinventory.application.usecase.RouterManagementUseCase;
import dev.davivieira.topologyinventory.application.usecase.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framwork.output.RouterManagementH2Adapter;
import dev.davivieira.topologyinventory.framwork.output.SwitchManagementH2Adapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SwitchManageGenericAdapter {

    private final SwitchManagementUseCase switchManagementUseCase;
    private final RouterManagementUseCase routerManagementUseCase;

    @GetMapping("/{switchId}")
    public Switch retrieveSwitch(@PathVariable Id switchId){
        return switchManagementUseCase.retrieveSwitch(switchId);
    }

    @PostMapping("")
    public EdgeRouter createAndAddSwitchToEdgeRouter(  Vendor vendor,
                                                       Model model,
                                                       IP ip,
                                                       Location location,
                                                       SwitchType switchType,
                                                       Id routerId){
        Switch newSwitch = switchManagementUseCase.createSwitch(vendor,model,ip,location,switchType);
        Router edgeRouter = routerManagementUseCase.retrieveRouter(routerId);

        if (!edgeRouter.getRouterType().equals(RouterType.EDGE)){
            throw new UnsupportedOperationException("Please inform the id of an edge router to add a switch");
        }

        Router router = switchManagementUseCase.addSwitchToEdgeRouter(newSwitch, (EdgeRouter) edgeRouter);
        return (EdgeRouter) routerManagementUseCase.persistRouter(router);
    }
}
