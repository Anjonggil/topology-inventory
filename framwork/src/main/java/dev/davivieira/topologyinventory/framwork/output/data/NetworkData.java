package dev.davivieira.topologyinventory.framwork.output.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "networks")
@MappedSuperclass
public class NetworkData {

    @Id
    @Column(name="network_id")
    private int id;

    @Column(name="switch_id")
    private UUID switchId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "address",
                    column = @Column(
                            name = "network_address")),
            @AttributeOverride(
                    name = "protocol",
                    column = @Column(
                            name = "network_protocol")),
    })
    IPData ip;

    @Column(name="network_name")
    String name;

    @Column(name="network_cidr")
    Integer cidr;

    public NetworkData(UUID switchId, IPData ip, String name, Integer cidr) {
        this.switchId = switchId;
        this.ip = ip;
        this.name = name;
        this.cidr = cidr;
    }
}
