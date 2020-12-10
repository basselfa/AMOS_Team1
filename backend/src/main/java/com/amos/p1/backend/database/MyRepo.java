package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;


@Component
public class MyRepo {

    private static final MyRepo instance = new MyRepo();
    private EntityManager em;
    private EntityManagerFactory emf;

    private MyRepo() {
        emf = Persistence.createEntityManagerFactory("MyRepo");
        em = emf.createEntityManager();
    }

    public static EntityManager getEntityManager(){
        return instance.em;
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return instance.emf;
    }

    public static void InsertIncident(List<Incident> incidents) {
        instance.em.getTransaction().begin();
        for(Incident incident : incidents) {
            instance.em.persist(incident);
        }
        instance.em.getTransaction().commit();
    }

    public static void insertRequest(Request request){
        //TODO implement it. Request is the main table. Also incidents saving
        List<Incident> incidents =request.getIncidents();
        InsertIncident(incidents);
        request.setIncidentsSavedInDb(true);
        // update incidents id
        request.addIncidents(incidents);

        instance.em.getTransaction().begin();
        instance.em.persist(request);
        instance.em.getTransaction().commit();
    }

    public static Request getRequest(LocalDateTime localDateTime){
        //TODO implement it.
        List<Request> requests =  instance.em.createNamedQuery("geRequestFromTime")
                .setParameter("requestTime" ,localDateTime )
                .getResultList();
        requests.get(0).setIncidentsSavedInDb(true);
        return requests.get(0);
    }

}
