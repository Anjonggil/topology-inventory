package dev.davivieira.topologyinventory.application.port.input;

import dev.davivieira.topologyinventory.application.port.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.application.usecase.NetworkManagementUseCase;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.service.NetworkService;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.domain.vo.Network;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class NetworkManagementInputPort implements NetworkManagementUseCase {

    private final RouterManagementOutputPort routerManagementOutputPort;

    @Override
    public Network createNetwork(IP networkAddress, String networkName, int networkCidr) {
        return Network.builder()
                .networkAddress(networkAddress)
                .networkName(networkName)
                .networkCidr(networkCidr)
                .build();
    }

    @Override
    public Switch addNetworkToSwitch(Network network, Switch networkSwitch) {
        networkSwitch.addNetworkToSwitch(network);
        return networkSwitch;
    }

    @Override
    public Switch removeNetworkFromSwitch(String networkName, Switch networkSwitch) {
        Id routerId = networkSwitch.getRouterId();
        Id switchId = networkSwitch.getId();
        EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
                .retrieveRouter(routerId);
        Switch switchToRemoveNetwork = edgeRouter
                .getSwitches()
                .get(switchId);
        Predicate<Network> networkPredicate = Network.getNetworkNamePredicate(networkName);
        var network = NetworkService.
                findNetwork(switchToRemoveNetwork.getSwitchNetworks(), networkPredicate);
        switchToRemoveNetwork.removeNetworkFromSwitch(network);
        routerManagementOutputPort.persistRouter(edgeRouter);
        return switchToRemoveNetwork.removeNetworkFromSwitch(network)
                ? switchToRemoveNetwork
                : null;
    }
}
