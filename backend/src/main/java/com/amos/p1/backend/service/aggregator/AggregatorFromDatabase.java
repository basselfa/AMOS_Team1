package com.amos.p1.backend.service.aggregator;

import com.amos.p1.backend.data.ComparisonEvaluationDTO;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.database.MyRepo;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AggregatorFromDatabase implements Aggregator {

    @Override
    public List<Incident> getIncidents(String cityName, Optional<LocalDateTime> timestamp, Optional<List<String>> types) {

        List<Incident>  incidents = new ArrayList<Incident>();

        if(timestamp.isPresent()){
            List<Request>  requests;
            requests = MyRepo.getEntityManager()
                .createNamedQuery("geRequestFromCityNameAndTime", Request.class)
                .setParameter("cityName", cityName )
                .setParameter("requestTime", timestamp.get())
                    .getResultList();
            for (Request request : requests ) {
                incidents.addAll(request.getIncidents()) ;

            }
        }else{
            List<Request>  requests;
            requests = MyRepo.getEntityManager()
                    .createNamedQuery("geRequestFromCityName", Request.class)
                    .setParameter("cityName", cityName )
                    .getResultList();
            for (Request request : requests ) {
                incidents.addAll(request.getIncidents());
            }
        }


        if(types.isPresent()){
            incidents = filterIncidentsByType(incidents, types.get());
        }

        return incidents;
    }

    private List<Incident> filterIncidentsByType(List<Incident> incidents, List<String> types) {
        List<Incident> filteredIncidents = new ArrayList<>();

        for (Incident incident : incidents) {
            if(hasIncidentOneOfThisTypes(incident, types)){
                filteredIncidents.add(incident);
            }
        }

        return filteredIncidents;
    }

    private boolean hasIncidentOneOfThisTypes(Incident incident, List<String> types){
        for (String type : types) {
            if(incident.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<LocalDateTime> getTimestampsFromCity(String city) {
        List<LocalDateTime> timestampList = (List<LocalDateTime>) MyRepo.getEntityManager()
                .createNamedQuery("getTimestampsFromCity")
                .setParameter("city", city )
                .getResultList();

        return timestampList;
    }

    @Override
    public List<String> getCities() {
        return null;
    }


    // to be refined with entry time - getDataFromTime()
    @Override
    public List<Incident> getAllData() {
        return (List<Incident>) MyRepo.getEntityManager()
                .createNamedQuery("getAllData")
                .getResultList();
    }

    public List<Incident> getFromRequestTime(LocalDateTime requestTime) {
        return MyRepo.getRequest(requestTime).getIncidents();
    }
    @Override
    public List<EvaluationCandidate> getEvaluationCandidate(String cityName, LocalDateTime requestTime) {
      Request request= MyRepo.geRequestFromCityNameAndTime(cityName,requestTime);
        return request.getEvaluationCandidate();
    }
    @Override
    public List<ComparisonEvaluationDTO> getComparisonEvaluationOverTime(String cityName){

       // Get all requests from this city.
        List<Request> requests =  MyRepo.getEntityManager().createNamedQuery("geRequestFromCityName")
                .setParameter("cityName",  cityName)
                .getResultList();
        List<ComparisonEvaluationDTO> comparisonEvaluationDTOList = new ArrayList<ComparisonEvaluationDTO>();
        for (Request request : requests) {
            //TODO: split to here and tom tom incidents

            List<Incident> tomTomIncidents =  MyRepo.getEntityManager().createNamedQuery("getFromTomTom")
                    .setParameter("requestId",  request.getId())
                    .getResultList();
            List<Incident> hereIncidents =  MyRepo.getEntityManager().createNamedQuery("getFromHere")
                    .setParameter("requestId",  request.getId())
                    .getResultList();

            // Add two one comparison evaluation dto
            ComparisonEvaluationDTO comparisonEvaluationDTO = new ComparisonEvaluationDTO();
            comparisonEvaluationDTO.setDate(java.sql.Timestamp.valueOf(request.getRequestTime())); ;
            comparisonEvaluationDTO.setTomTomIncidentsAmount(tomTomIncidents.size()); ;
            comparisonEvaluationDTO.setHereIncidentsAmount(hereIncidents.size()); ;
            if(request.getEvaluationCandidate()!= null)
                comparisonEvaluationDTO.setSameIncidentAmount(request.getEvaluationCandidate().size()); ;

            // Add ComparisonEvaluationDTO to a list

            comparisonEvaluationDTOList.add(comparisonEvaluationDTO);


        }

        //Return list of ComparisonEvaluationDTO
        return comparisonEvaluationDTOList ;
    }

}