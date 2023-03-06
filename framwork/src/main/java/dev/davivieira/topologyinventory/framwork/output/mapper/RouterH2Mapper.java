package dev.davivieira.topologyinventory.framwork.output.mapper;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framwork.output.data.*;

import java.util.*;

public class RouterH2Mapper {

    public static Router routerDataToDomain(RouterData routerData){
        var router = RouterFactory.getRouter(
                Id.withId(routerData.getRouterId().toString()),
                Vendor.valueOf(routerData.getRouterVendor().toString()),
                Model.valueOf(routerData.getRouterModel().toString()),
                IP.fromAddress(routerData.getIp().getAddress()),
                locationDataToLocation(routerData.getRouterLocation()),
                RouterType.valueOf(routerData.getRouterType().name()));
        if (routerData.getRouterType().equals(RouterTypeData.CORE)){
            var coreRouter = (CoreRouter) router;
            coreRouter.setRouters(getRoutersFromData(routerData.getRouters()));
            return coreRouter;
        } else {
            var edgeRouter = (EdgeRouter) router;
            edgeRouter.setSwitches(getSwitchedFromData(routerData.getSwitches()));
            return edgeRouter;
        }
    }

    private static  Map<Id,Switch> getSwitchedFromData(List<SwitchData> switches) {
        Map<Id,Switch> switchMap = new HashMap<>();
        for (SwitchData switchData : switches) {
            switchMap.put(
                    Id.withId(switchData.getSwitchId().toString()),
                    switchDataToDomain(switchData));
        }
        return switchMap;
    }

    private static Map<Id, Router> getRoutersFromData(List<RouterData> routers) {
        Map<Id,Router> routerMap = new HashMap<>();
        for (RouterData routerData : routers){
            routerMap.put(
                    Id.withId(routerData.getRouterId().toString()),
                    routerDataToDomain(routerData));
        }
        return routerMap;
    }

    private static Location locationDataToLocation(LocationData locationData) {
        return Location.builder()
                .address(locationData.getAddress())
                .city(locationData.getCity())
                .state(locationData.getState())
                .zipcode(locationData.getZipCode())
                .country(locationData.getCountry())
                .latitude(locationData.getLatitude())
                .longitude(locationData.getLongitude())
                .build();
    }

    public static RouterData routerDomainToData(Router router) {
        var routerData = RouterData.builder().
                routerId(router.getId().getId()).
                routerVendor(VendorData.valueOf(router.getVendor().toString())).
                routerModel(ModelData.valueOf(router.getModel().toString())).
                ip(IPData.fromAddress(router.getIp().getIpAddress())).
                routerLocation(locationDomainToLocationData(router.getLocation())).
                routerType(RouterTypeData.valueOf(router.getRouterType().toString())).
                build();
        if(router.getRouterType().equals(RouterType.CORE)) {
            var coreRouter = (CoreRouter) router;
            routerData.setRouters(getRoutersFromDomain(coreRouter.getRouters()));
        } else {
            var edgeRouter = (EdgeRouter) router;
            routerData.setSwitches(getSwitchesFromDomain(edgeRouter.getSwitches()));
        }
        return routerData;
    }

    private static List<SwitchData> getSwitchesFromDomain(Map<Id, Switch> switches) {
        List<SwitchData> switchDataList = new ArrayList<>();
        if(switches!=null) {
            switches.values().stream().forEach(aSwitch -> {
                switchDataList.add(switchDomainToData(aSwitch));
            });
        }
        return switchDataList;
    }

    private static SwitchData switchDomainToData(Switch aSwitch) {
        return  SwitchData.builder().
                switchId(aSwitch.getId().getId()).
                routerId(aSwitch.getRouterId().getId()).
                switchVendor(VendorData.valueOf(aSwitch.getVendor().toString())).
                switchModel(ModelData.valueOf(aSwitch.getModel().toString())).
                ip(IPData.fromAddress(aSwitch.getIp().getIpAddress())).
                switchLocation(locationDomainToLocationData(aSwitch.getLocation())).
                switchType(SwitchTypeData.valueOf(aSwitch.getSwitchType().toString())).
                networks(getNetworksFromDomain(aSwitch.getSwitchNetworks(), aSwitch.getId().getId())).
                build();
    }

    private static List<NetworkData> getNetworksFromDomain(List<Network> switchNetworks, UUID id) {
        return null;
    }

    private static List<RouterData> getRoutersFromDomain(Map<Id, Router> routers) {
        return null;
    }

    private static LocationData locationDomainToLocationData(Location location) {
        return LocationData.builder()
                .address(location.getAddress())
                .city(location.getCity())
                .state(location.getState())
                .zipCode(location.getZipcode())
                .country(location.getCountry())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    public static Switch switchDataToDomain(SwitchData switchData) {
        return null;
    }
}
