package dev.davivieira.topologyinventory.framwork.output;

import dev.davivieira.topologyinventory.application.port.output.SwitchManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.Switch;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.framwork.output.data.SwitchData;
import dev.davivieira.topologyinventory.framwork.output.mapper.RouterH2Mapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Repository
public class SwitchManagementH2Adapter implements SwitchManagementOutputPort {

    private static SwitchManagementH2Adapter instance;

    @PersistenceContext
    private EntityManager em;

    private SwitchManagementH2Adapter(){
        setUpH2Database();
    }

    @Override
    public Switch retrieveSwitch(Id id) {
        var switchData = em.getReference(SwitchData.class, id.getId());
        return RouterH2Mapper.switchDataToDomain(switchData);
    }

    private void setUpH2Database() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("inventory");
        EntityManager em =
                entityManagerFactory.createEntityManager();
        this.em = em;
    }

    public static SwitchManagementH2Adapter getInstance() {
        if (instance == null) {
            instance = new SwitchManagementH2Adapter();
        }
        return instance;
    }
}
