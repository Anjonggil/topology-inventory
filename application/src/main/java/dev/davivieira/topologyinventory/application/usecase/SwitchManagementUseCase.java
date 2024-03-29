package dev.davivieira.topologyinventory.application.usecase;

import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.*;

public interface SwitchManagementUseCase {
    Switch createSwitch(Vendor vendor, Model model, IP ip, Location location, SwitchType switchType);
    EdgeRouter addSwitchToEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);
    EdgeRouter removeSwitchFromEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);

    Switch retrieveSwitch(Id switchId);
}
