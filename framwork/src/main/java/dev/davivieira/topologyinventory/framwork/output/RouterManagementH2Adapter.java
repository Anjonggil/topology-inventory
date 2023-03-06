package dev.davivieira.topologyinventory.framwork.output;

import dev.davivieira.topologyinventory.application.port.output.RouterManagementOutputPort;
import dev.davivieira.topologyinventory.domain.entity.Router;
import dev.davivieira.topologyinventory.domain.vo.Id;
import dev.davivieira.topologyinventory.framwork.output.data.RouterData;
import dev.davivieira.topologyinventory.framwork.output.mapper.RouterH2Mapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class RouterManagementH2Adapter implements RouterManagementOutputPort {

    private static RouterManagementH2Adapter instance;

    @PersistenceContext
    private EntityManager em;

    private RouterManagementH2Adapter() {
        setUpH2Database();
    }

    private void setUpH2Database() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("inventory");
        EntityManager em = emf.createEntityManager();
        this.em = em;
    }

    @Override
    public Router retrieveRouter(Id id) {
        var routerData = em.getReference(RouterData.class, id.getId());
        return RouterH2Mapper.routerDataToDomain(routerData);
    }

    @Override
    public Router persistRouter(Router router) {
        var routerData = RouterH2Mapper.routerDomainToData(router);
        em.persist(routerData);
        return router;
    }

    @Override
    public Router removeRouter(Id id) {
        var routerData = em.getReference(RouterData.class, id.getId());
        em.remove(routerData);
        return null;
    }

    public static RouterManagementH2Adapter getInstance(){
        if (instance == null){
            instance = new RouterManagementH2Adapter();
        }
        return instance;
    }
}
