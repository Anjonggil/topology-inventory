package dev.davivieira.topologyinventory.framwork.input.generic;

import dev.davivieira.topologyinventory.application.usecase.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.application.usecase.SwitchManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NetworkManagementGenericAdapter {

    private final SwitchManagementUseCase switchManagementUseCase;
    private final NetworkManagementUseCase networkManagementUseCase;

    @PostMapping
    public Switch addNetworkToSwitch(Network network, Id switchId){
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.addNetworkToSwitch(network, networkSwitch);
    }

    @DeleteMapping
    public Switch removeNetworkFromSwitch(String networkName, Id switchId){
        Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
        return networkManagementUseCase.removeNetworkFromSwitch(networkName,networkSwitch);
    }
}
