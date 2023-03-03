package dev.davivieira.topologyinventory.application.port.output;

import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.Id;

public interface RouterManagementOutputPort {
    Router retrieveRouter(Id id);

    Router persistRouter(Router router);

    Router removeRouter(Id id);
}
