package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
    
}
