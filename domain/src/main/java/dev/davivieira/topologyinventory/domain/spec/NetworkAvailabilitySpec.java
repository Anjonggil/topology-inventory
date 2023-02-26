package dev.davivieira.topologyinventory.domain.spec;

import dev.davivieira.topologyinventory.domain.entity.Equipment;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.exception.GenericSpecificationException;
import dev.davivieira.topologyinventory.domain.spec.shared.AbstractSpecification;
import dev.davivieira.topologyinventory.domain.vo.IP;
import dev.davivieira.topologyinventory.domain.vo.Network;

public class NetworkAvailabilitySpec  extends AbstractSpecification<Equipment> {
    private IP address;
    private String name;
    private int cidr;

    public NetworkAvailabilitySpec(Network network) {
        this.address = network.getNetworkAddress();
        this.name = network.getNetworkName();
        this.cidr = network.getNetworkCidr();
    }

    @Override
    public boolean isSatisfiedBy(Equipment switchNetworks) {
        return switchNetworks != null && isNetworkAvailable(switchNetworks);
    }

    private boolean isNetworkAvailable(Equipment switchNetworks) {
        var availability = true;
        for (Network network : ((Switch)switchNetworks).getSwitchNetworks()){
            if (network.getNetworkAddress().equals(address)){
                if (network.getNetworkAddress().equals(address) &&
                        network.getNetworkName().equals(name) &&
                        network.getNetworkCidr() == cidr){
                    availability = false;
                    break;
                }
            }
        }
        return availability;
    }

    @Override
    public void check(Equipment equipment) throws GenericSpecificationException {
        if(!isSatisfiedBy(equipment))
            throw new GenericSpecificationException("This network already exists");
    }
}
