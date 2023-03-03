package dev.davivieira.topologyinventory.framwork.output.mapper;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.entity.factory.RouterFactory;
import dev.davivieira.topologyinventory.domain.vo.*;
import dev.davivieira.topologyinventory.framwork.output.data.LocationData;
import dev.davivieira.topologyinventory.framwork.output.data.RouterData;
import dev.davivieira.topologyinventory.framwork.output.data.RouterTypeData;
import dev.davivieira.topologyinventory.framwork.output.data.SwitchData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static Location locationDataToLocation(LocationData routerLocation) {
        return null;
    }

    public static RouterData routerDomainToData(Router router) {
        return null;
    }

    public static Switch switchDataToDomain(SwitchData switchData) {
        return null;
    }
}
