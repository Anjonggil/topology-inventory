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

public class SwitchManageGenericAdapter {

    private SwitchManagementUseCase switchManagementUseCase;
    private RouterManagementUseCase routerManagementUseCase;

    public SwitchManageGenericAdapter() {
        setPorts();
    }

    private void setPorts() {
        this.routerManagementUseCase = new RouterManagementInputPort(RouterManagementH2Adapter.getInstance());
        this.switchManagementUseCase = new SwitchManagementInputPort(SwitchManagementH2Adapter.getInstance());
    }

    public Switch retrieveSwitch(Id switchId){
        return switchManagementUseCase.retrieveSwitch(switchId);
    }

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
