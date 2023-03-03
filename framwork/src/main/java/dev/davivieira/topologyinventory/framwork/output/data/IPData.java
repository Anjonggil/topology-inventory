package dev.davivieira.topologyinventory.framwork.output.data;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
public class IPData {
    private String address;

    @Enumerated(value = EnumType.STRING)
    @Embedded
    private ProtocolData protocol;

    private IPData(String address){
        if(address == null)
            throw new IllegalArgumentException("Null IP address");
        this.address = address;
        if(address.length()<=15) {
            this.protocol = ProtocolData.IPV4;
        } else {
            this.protocol = ProtocolData.IPV6;
        }
    }

    public IPData() {

    }

    public static IPData fromAddress(String address){
        return new IPData(address);
    }
}
