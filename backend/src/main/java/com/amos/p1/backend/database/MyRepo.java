package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    public static void insertIncident(List<Incident> incidents) {
        instance.em.getTransaction().begin();
        for(Incident incident : incidents) {
            instance.em.persist(incident);
        }
        instance.em.getTransaction().commit();
    }

    public static void insertRequest(Request request){
        List<Incident> incidents = request.getIncidents();

        insertIncident(incidents);
        request.setIncidentsId(getIncidentIds(incidents));

        instance.em.getTransaction().begin();
        instance.em.persist(request);
        instance.em.getTransaction().commit();
    }

    private static String getIncidentIds(List<Incident> incidents){
        String incidentIds = "";

        for (Incident incident : incidents) {

            incidentIds += incident.getId().toString();
            if (incident != incidents.get(incidents.size() - 1))
                incidentIds += ",";
        }

        return incidentIds;
    }

    public static Request getRequest(LocalDateTime localDateTime){
        List<Request> requests =  instance.em.createNamedQuery("geRequestFromTime", Request.class)
                .setParameter("requestTime" , localDateTime)
                .getResultList();
        Request request = requests.get(0);

        List<Incident> incidents = getIncidentFromDBByIds(request.getIncidentsId());
        request.setIncidents(incidents);

        return request;
    }

    private static List<Incident> getIncidentFromDBByIds(String incidentsId){
        String[] idSplit = incidentsId.split(",");
        List<Long> idSplitasLongs = Stream.of(idSplit).map(Long::valueOf).collect(Collectors.toList());

        return instance.em.createNamedQuery("getFromids", Incident.class)
                .setParameter("id", idSplitasLongs)
                .getResultList();
    }

}
