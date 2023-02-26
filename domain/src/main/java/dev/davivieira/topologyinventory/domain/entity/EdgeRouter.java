package dev.davivieira.topologyinventory.domain.entity;

import dev.davivieira.topologyinventory.domain.spec.EmptyNetworkSpec;
import dev.davivieira.topologyinventory.domain.spec.SameCountrySpec;
import dev.davivieira.topologyinventory.domain.spec.SameIpSpec;
import dev.davivieira.topologyinventory.domain.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class EdgeRouter extends Router{

    private Map<Id, Switch> switches;

    @Builder
    public EdgeRouter(Id id, Vendor vendor, Model model, IP ip, Location location, RouterType routerType, Map<Id, Switch> switches) {
        super(id, vendor, model, ip, location, routerType);
        this.switches = switches;
    }

    public void addSwitch(Switch anySwitch){
        var sameCountryRouterSpec = new SameCountrySpec(this);
        var sameIpSpec = new SameIpSpec(this);

        sameCountryRouterSpec.check(anySwitch);
        sameIpSpec.check(anySwitch);

        this.switches.put(anySwitch.id,anySwitch);
    }

    public Switch removeSwitch(Switch anySwitch) {
        var emptyNetworkSpec = new EmptyNetworkSpec();
        emptyNetworkSpec.check(anySwitch);

        return this.switches.remove(anySwitch.id);
    }
}
