package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.Incident;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.LinkedList;
import java.util.List;

public class HereNormalization implements JsonToIncident {
    @Override
    public Incident normalizeOneIncident(String json) {
        Incident incidentObj = new Incident();
        try {
            JSONObject incidentData = new JSONObject(json);
            incidentObj.setId(incidentData.getLong("ORIGINAL_TRAFFIC_ITEM_ID"));         //toDo: lange oder kurze Beschreibung?
            incidentObj.setType(-1);                                                        //toDo: what types exist?
            incidentObj.setSize("");                                                        //toDo: was bedeutet das?
            incidentObj.setDescription(incidentData.getString("TRAFFIC_ITEM_TYPE_DESC"));
            incidentObj.setCity(
                    incidentData.getJSONObject("LOCATION")
                            .getJSONObject("INTERSECTION")
                            .getJSONObject("ORIGIN")
                            .getString("COUNTY")
            );
            incidentObj.setCountry("DE");                                                   //toDo: warum, ich dachte nur Deutschland ?!
            incidentObj.setExitAvailable(0);                                                //toDo: wtf?

            incidentObj.setStartPositionLatitude();
            incidentObj.setStartPositionLongitude();
            incidentObj.setStartPositionStreet();

            incidentObj.setEndPositionLatitude();
            incidentObj.setEndPositionLongitude();
            incidentObj.setEndPositionStreet();

            incidentObj.setVerified();
            incidentObj.setProvider();
            incidentObj.setDelay();
            incidentObj.setEntryTime();
            incidentObj.setEndTime();
            incidentObj.setEdges();

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
            JSONArray trafficItems = obj.getJSONArray("TRAFFIC_ITEM");

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
}
