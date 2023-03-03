package dev.davivieira.topologyinventory.framwork.output.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location")
@MappedSuperclass
public class LocationData {
    @Id
    @Column(name = "location_id")
    private int locationId;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipCode")
    private int zipCode;

    @Column(name = "country")
    private String country;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;
}
