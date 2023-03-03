package dev.davivieira.topologyinventory.framwork.output.data;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "routers")
@MappedSuperclass
public class RouterData {

    @Id
    @Column(name = "router_id",
            columnDefinition = "uuid",
            updatable = false)
    private UUID routerId;

    @Column(name = "router_parent_core_id")
    private UUID routerParentCoreId;

    @Embedded
    @Enumerated(value = EnumType.STRING)
    @Column(name = "router_vendor")
    private VendorData routerVendor;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name="router_model")
    private ModelData routerModel;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "address",
                    column = @Column(
                            name = "router_ip_address")),
            @AttributeOverride(
                    name = "protocol",
                    column = @Column(
                            name = "router_ip_protocol")),
    })
    private IPData ip;

    @ManyToOne
    @JoinColumn(name="location_id")
    private LocationData routerLocation;

    @Embedded
    @Enumerated(EnumType.STRING)
    @Column(name="router_type")
    private RouterTypeData routerType;

    @OneToMany
    @JoinColumn(table = "switches",
            name = "router_id",
            referencedColumnName = "router_id")
    @Setter
    private List<SwitchData> switches;

    @OneToMany
    @JoinTable(name="routers",
            joinColumns={@JoinColumn(name="router_parent_core_id")},
            inverseJoinColumns={@JoinColumn(name="router_id")})
    @Setter
    private List<RouterData> routers;
}
