package com.amos.p1.backend.database;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;


@Component
public class MyRepo {

    private static final MyRepo instance = new MyRepo();
    private EntityManager em;
    private EntityManagerFactory emf;
    private EntityManager emTest;
    private EntityManagerFactory emfTest;
    private boolean useTestDatabase =false;

    private MyRepo() {
        emf = Persistence.createEntityManagerFactory("MyRepo");
        em = emf.createEntityManager();

        try {
            intialiseTestDB();

            emfTest = Persistence.createEntityManagerFactory("MyTestRepo");
            emTest = emfTest.createEntityManager();
        } catch (Exception e) {

            System.out.println("Couldn't start test database");
        }


    }

    public static boolean isUseTestDatabase() {  return instance.useTestDatabase;}
    public static void setUseTestDatabase(boolean useTestDatabase) { instance.useTestDatabase = useTestDatabase; }

    public  void intialiseTestDB() throws SQLException, FileNotFoundException {
        final  String URl = "jdbc:mysql://localhost:3306/testdb3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
        final  String userpass ="&createDatabaseIfNotExist=true";
        final  String id = "root";
        final  String  password = "root";
        final String jdbcDriver = "com.mysql.cj.jdbc.Driver";

//        create database if not created
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con1 = DriverManager.getConnection(URl + userpass, id, password);
        Statement statement = con1.createStatement();

//intialise schema in datadb
        Connection con2 = DriverManager.getConnection(URl ,id,password);

          ScriptRunner scriptRunner = new ScriptRunner(con2);
        FileReader fileReader = new FileReader("src/main/resources/schema.sql");
        scriptRunner.setLogWriter(null);
        scriptRunner.runScript(new BufferedReader(fileReader));


    };
    public static EntityManager getEntityManager(){
        if (instance.useTestDatabase == true) return instance.emTest;
        return instance.em;
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        if (instance.useTestDatabase == true) return instance.emfTest;
        return instance.emf;
    }



    public static void dropAll(){
        String  URl = "jdbc:mysql://remotemysql.com:3306/lIkqLjf1AL";
        String   id = "lIkqLjf1AL";
        String   password = "yddtBbLwx1";

         String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        if (instance.useTestDatabase == true)
        {
             URl = "jdbc:mysql://localhost:3306/testdb3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
             id = "root";
              password = "root";

        }
        Connection con2 = null;
        try {
            con2 = DriverManager.getConnection(URl ,id,password);
            ScriptRunner scriptRunner = new ScriptRunner(con2);
            FileReader fileReader = new FileReader("src/main/resources/schema.sql");
            scriptRunner.setLogWriter(null);
            scriptRunner.runScript(new BufferedReader(fileReader));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }



    public static void insertIncident(List<Incident> incidents) {
        for(Incident incident : incidents) {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(incident);
            getEntityManager().getTransaction().commit();

        }
    }
    public static List<Incident> getIncidents(Long id) {
        List<Incident> resultList = MyRepo.getEntityManager()
                .createNamedQuery("getFromids")
                .setParameter("id", id)
                .getResultList();
        return resultList;
    }


    public static void insertRequest(Request request){
        //TODO implement it. Request is the main table. Also incidents saving
        getEntityManager().getTransaction().begin();
        List<Incident> incidents =request.getIncidents();
        insertIncident(incidents);
        request.setIncidentsSavedInDb(true);
        // update incidents id
        request.setIncidents(incidents);

        getEntityManager().persist(request);
        getEntityManager().getTransaction().commit();
    }

    public static Request getRequest(LocalDateTime localDateTime){
        //TODO implement it.
        List<Request> requests =  getEntityManager().createNamedQuery("geRequestFromTime")
                .setParameter("requestTime" ,localDateTime )
                .getResultList();
        requests.get(0).setIncidentsSavedInDb(true);
        return requests.get(0);
    }

}
