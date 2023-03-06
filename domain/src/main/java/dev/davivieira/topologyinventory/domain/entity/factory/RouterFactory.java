package dev.davivieira.topologyinventory.domain.entity.factory;

import dev.davivieira.topologyinventory.domain.entity.CoreRouter;
import dev.davivieira.topologyinventory.domain.entity.EdgeRouter;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.*;

public class RouterFactory {

    public static Router getRouter(Id id,
                                   Vendor vendor,
                                   Model model,
                                   IP ip,
                                   Location location,
                                   RouterType routerType) {
        switch (routerType) {
            case CORE:
                return CoreRouter.builder()
                        .id(id == null ? Id.withoutId() : id)
                        .vendor(vendor)
                        .ip(ip)
                        .location(location)
                        .routerType(routerType)
                        .build();
            case EDGE:
                return EdgeRouter.builder()
                        .id(id == null ? Id.withoutId() : id)
                        .vendor(vendor)
                        .ip(ip)
                        .location(location)
                        .routerType(routerType)
                        .build();
            default:
                return null;
        }
    }
}
