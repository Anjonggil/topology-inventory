package dev.davivieira.topologyinventory.framwork.output.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "switches")
@MappedSuperclass
public class SwitchData {

    @Id
    @Column(name="switch_id",
            columnDefinition = "uuid",
            updatable = false )
    private UUID switchId;

    @Column(name="router_id")
    private UUID routerId;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name="switch_vendor")
    private VendorData switchVendor;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name="switch_model")
    private ModelData switchModel;

    @Enumerated(EnumType.STRING)
    @Embedded
    @Column(name = "switch_type")
    private SwitchTypeData switchType;

    @OneToMany
    @JoinColumn(table = "networks",
            name = "switch_id",
            referencedColumnName = "switch_id")
    private List<NetworkData> networks;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "address",
                    column = @Column(
                            name = "switch_ip_address")),
            @AttributeOverride(
                    name = "protocol",
                    column = @Column(
                            name = "switch_ip_protocol")),
    })
    private IPData ip;

    @ManyToOne
    @JoinColumn(name="location_id")
    private LocationData switchLocation;
}
