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

    public static void insertRequest(List<Incident> incidents){
        //TODO implement it. Request is the main table. Also incidents saving
        InsertIncident(incidents);


        instance.em.getTransaction().begin();

        Request request = new Request();
        request.addIncidents(incidents);
        request.setRequestTime(LocalDateTime.now());

        instance.em.persist(request);
        instance.em.getTransaction().commit();
    }

    public static Request getRequest(LocalDateTime localDateTime){
        //TODO implement it.
        return null;
    }

}
