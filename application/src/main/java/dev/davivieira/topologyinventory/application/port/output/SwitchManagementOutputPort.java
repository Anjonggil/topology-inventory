package dev.davivieira.topologyinventory.application.port.output;

import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;

public interface SwitchManagementOutputPort {
    Switch retrieveSwitch(Id id);
}
