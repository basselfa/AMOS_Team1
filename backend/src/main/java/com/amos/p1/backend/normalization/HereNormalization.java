package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.Incident;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class HereNormalization implements JsonToIncident {
    enum HereIncidents {
        ACCIDENT(IncidentTypes.ACCIDENT),
        CONGESTION(IncidentTypes.CONGESTION),
        DISABLEDVEHICLE(IncidentTypes.DISABLEDVEHICLE),
        ROADHAZARD(IncidentTypes.ROADHAZARD),
        CONSTRUCTION(IncidentTypes.ROADWORKS),
        PLANNEDEVENT(IncidentTypes.PLANNEDEVENT),
        MASSTRANSIT(IncidentTypes.MISC),
        OTHERNEWS(IncidentTypes.MISC),
        WEATHER(IncidentTypes.WEATHER),
        MISC(IncidentTypes.MISC),
        ROADCLOSURE(IncidentTypes.ROADCLOSURE),
        LANERESTRICTION(IncidentTypes.LANERESTRICTION);

        private final IncidentTypes incidentType;

        HereIncidents(IncidentTypes incidentType) {
            this.incidentType = incidentType;
        }

        public int getID() {
            return incidentType.getId();
        }
    }

    private int mapIncidenType(String incidentStr) {
        try {
            return HereIncidents.valueOf(incidentStr.toUpperCase()).getID();
        }catch (IllegalArgumentException ex){
            // todo: log - there is a new incident type defined by the API
            return IncidentTypes.MISC.getId();
        }
    }

    @Override
    public Incident normalizeOneIncident(String json) {
        Incident incidentObj = new Incident();
        try {
            JSONObject incidentData = new JSONObject(json);
            incidentObj.setId(incidentData.getLong("ORIGINAL_TRAFFIC_ITEM_ID"));

            incidentObj.setType(
                    mapIncidenType(incidentData.getString("TRAFFIC_ITEM_TYPE_DESC"
                    ));


            incidentObj.setSize("");                                                        //toDo: was bedeutet das?
            incidentObj.setDescription(incidentData.getString("TRAFFIC_ITEM_TYPE_DESC")); //längerer Satz
            incidentObj.setCity(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("INTERSECTION")
                            .getJSONObject("ORIGIN")
                            .getString("COUNTY")
            );
            incidentObj.setCountry("DE");                                                   //toDo: warum, ich dachte nur Deutschland ?!
            incidentObj.setExitAvailable(0);                                                //toDo: wtf?

            // start piont
            incidentObj.setStartPositionLatitude(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("GEOLOC")
                            .getJSONObject("ORIGIN")
                            .getString("LATITUDE")
            );
            incidentObj.setStartPositionLongitude(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("GEOLOC")
                            .getJSONObject("ORIGIN")
                            .getString("LONGITUDE")
            );
            incidentObj.setStartPositionStreet(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("INTERSECTION")
                            .getJSONObject("ORIGIN")
                            .getJSONObject("STREET1")
                            .getString("ADDRESS1")
            );                                                                                  // toDo: ich bekomme eine Kreuzung und keine einzelne Straße

            // end piont
            incidentObj.setEndPositionLatitude(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("GEOLOC")
                            .getJSONArray("TO")
                            .getJSONObject(0)
                            .getString("LATITUDE")
            );
            incidentObj.setEndPositionLongitude(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("GEOLOC")
                            .getJSONArray("TO")
                            .getJSONObject(0)
                            .getString("LONGITUDE")
            );
            incidentObj.setEndPositionStreet(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("INTERSECTION")
                            .getJSONObject("TO")
                            .getJSONObject("STREET1")
                            .getString("ADDRESS1")
            );                                                                              // toDo: ich bekomme eine Kreuzung und keine einzelne Straße
            // toDo: umlaute werden nicht richtig übernommen (charset prüfen)

            incidentObj.setVerified(
                    incidentData.getBoolean("VERIFIED") ? 1 : 0
            );

            incidentObj.setProvider(0);
            // incidentObj.setDelay();                                                      // toDo: ????
            incidentObj.setEntryTime(
                    parseDate(incidentData.getString("ENTRY_TIME"))
            );
            incidentObj.setEndTime(
                    parseDate(incidentData.getString("END_TIME"))
            );
            // incidentObj.setEdges();                                                       // toDo: ????
            System.out.println(incidentObj);
        } catch (JSONException e) {
            //json cant be paresed
            e.printStackTrace();
            return null;
        }

        return incidentObj;
    }

    @Override
    public List<Incident> normalize(String json) {
        List<Incident> incidents = new LinkedList<>();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray trafficItems = obj.getJSONObject("TRAFFIC_ITEMS").getJSONArray("TRAFFIC_ITEM");

            // iterate through all incident
            for (int i = 0; i < trafficItems.length(); i++) {
                JSONObject trafficItem = trafficItems.getJSONObject(i);
                Incident incident = normalizeOneIncident(trafficItem.toString());

                // ignore null elements
                if (incident != null)
                    incidents.add(incident);
            }

        } catch (JSONException e) {
            //json cant be paresed
            e.printStackTrace();
            return null;
        }

        return incidents;
    }

    private LocalDateTime parseDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return LocalDateTime.parse(timeStr, formatter);
    }
}
