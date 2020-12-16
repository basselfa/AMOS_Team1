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
        ACCIDENT(Incident.IncidentTypes.ACCIDENT),
        CONGESTION(Incident.IncidentTypes.CONGESTION),
        DISABLEDVEHICLE(Incident.IncidentTypes.DISABLEDVEHICLE),
        ROADHAZARD(Incident.IncidentTypes.ROADHAZARD),
        CONSTRUCTION(Incident.IncidentTypes.ROADWORKS),
        PLANNEDEVENT(Incident.IncidentTypes.PLANNEDEVENT),
        MASSTRANSIT(Incident.IncidentTypes.MISC),
        OTHERNEWS(Incident.IncidentTypes.MISC),
        WEATHER(Incident.IncidentTypes.WEATHER),
        MISC(Incident.IncidentTypes.MISC),
        ROADCLOSURE(Incident.IncidentTypes.ROADCLOSURE),
        LANERESTRICTION(Incident.IncidentTypes.LANERESTRICTION);

        private final Incident.IncidentTypes incidentType;

        HereIncidents(Incident.IncidentTypes incidentType) {
            this.incidentType = incidentType;
        }

        public int getID() {
            return incidentType.getId();
        }
    }

    private String mapIncidentType(String incidentStr) {
        try {
            return HereIncidents.valueOf(incidentStr.toUpperCase()).toString();
        } catch (IllegalArgumentException ex) {
            // todo: log - there is a new incident type defined by the API
            return Incident.IncidentTypes.MISC.toString();
        }
    }

    @Override
    public Incident normalizeOneIncident(String json) {
        Incident incidentObj = new Incident();
        try {
            JSONObject incidentData = new JSONObject(json);
            incidentObj.setId(incidentData.getLong("ORIGINAL_TRAFFIC_ITEM_ID"));
            incidentObj.setType(String.valueOf(mapIncidentType(incidentData.getString("TRAFFIC_ITEM_TYPE_DESC"))));
            incidentObj.setDescription(
                    incidentData.getString("TRAFFIC_ITEM_TYPE_DESC") +
                            " & " +
                            incidentData.getJSONArray("TRAFFIC_ITEM_DESCRIPTION").getJSONObject(0).getString("value")
            );

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
            incidentObj.setEntryTime(
                    parseDate(incidentData.getString("ENTRY_TIME"))
            );
            incidentObj.setEndTime(
                    parseDate(incidentData.getString("END_TIME"))
            );
            incidentObj.setVerified(
                    incidentData.getBoolean("VERIFIED") ? 1 : 0
            );
            incidentObj.setProvider("0");
            incidentObj.setLengthInMeter(
                    incidentData.getJSONObject("LOCATION")
                            .getDouble("LENGTH")    //todo: is it really meter??
            );
            incidentObj.setEdges(parseHereEdges(incidentData));
            parseHereAdress(incidentData, incidentObj);

            // todo: remove ?
            if (incidentData.has("CRITICALITY")) {
                int hereCriticality = (int) incidentData.getJSONObject("CRITICALITY").getLong("ID");
                int criticality = (4 - hereCriticality) * 3;
                incidentObj.setSize(criticality+"");
            } else {
                incidentObj.setSize(-1 +"");
            }


        } catch (JSONException e) {
            //json cant be parsed
            throw new IllegalArgumentException(e);
        }

        return incidentObj;
    }

    private String parseHereEdges(JSONObject incidentData) {
        String edgesString = "";
        try {
            JSONArray geoLocationsArr = incidentData.getJSONObject("LOCATION")
                    .getJSONObject("GEOLOC")
                    .getJSONObject("GEOMETRY")
                    .getJSONObject("SHAPES")
                    .getJSONArray("SHP");

            for (int i = 0; i < geoLocationsArr.length(); i++) {
                String partialShape = geoLocationsArr.getJSONObject(i).getString("value");
                for (String location : partialShape.split(" ")) {
                    edgesString += location.replace(",", ":") + ",";
                }
            }
            return edgesString;
        } catch (JSONException jex) {
            return "";
        }
    }

    private void parseHereAdress(JSONObject incidentData, Incident incidentObj) {

        try {
            // intersection or street
            JSONObject locationJSON = null;
            if (incidentData.getJSONObject("LOCATION").has("INTERSECTION")) {
                locationJSON = incidentData.getJSONObject("LOCATION").getJSONObject("INTERSECTION");
                incidentObj.setStartPositionStreet(
                        locationJSON
                                .getJSONObject("ORIGIN")
                                .getJSONObject("STREET1")
                                .getString("ADDRESS1") +
                                " - " +
                                locationJSON
                                        .getJSONObject("ORIGIN")
                                        .getJSONObject("STREET2")
                                        .getString("ADDRESS1")
                );
                incidentObj.setEndPositionStreet(
                        locationJSON
                                .getJSONObject("TO")
                                .getJSONObject("STREET1")
                                .getString("ADDRESS1") +
                                " - " +
                                locationJSON
                                        .getJSONObject("TO")
                                        .getJSONObject("STREET2")
                                        .getString("ADDRESS1")
                );
                incidentObj.setCity(
                        locationJSON
                                .getJSONObject("ORIGIN")
                                .getString("COUNTY")
                );

            } else {
                locationJSON = incidentData.getJSONObject("LOCATION").getJSONObject("DEFINED");

                incidentObj.setStartPositionStreet(
                        locationJSON
                                .getJSONObject("ORIGIN")
                                .getJSONObject("ROADWAY")
                                .getJSONArray("DESCRIPTION")
                                .getJSONObject(0)
                                .getString("value") +
                                " - " +
                                locationJSON
                                        .getJSONObject("ORIGIN")
                                        .getJSONObject("POINT")
                                        .getJSONArray("DESCRIPTION")
                                        .getJSONObject(0)
                                        .getString("value")
                );

                incidentObj.setEndPositionStreet(
                        locationJSON
                                .getJSONObject("TO")
                                .getJSONObject("ROADWAY")
                                .getJSONArray("DESCRIPTION")
                                .getJSONObject(0)
                                .getString("value") +
                                " - " +
                                locationJSON
                                        .getJSONObject("TO")
                                        .getJSONObject("POINT")
                                        .getJSONArray("DESCRIPTION")
                                        .getJSONObject(0)
                                        .getString("value")
                );
                // todo: no City available
            }


        } catch (JSONException jex) {
//            System.out.println(incidentData.toString());
//            jex.printStackTrace();
//            throw new IllegalArgumentException(jex);
        }
    }

    @Override
    public List<Incident> normalize(String json) {
        List<Incident> incidents = new LinkedList<>();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray trafficItems = obj.getJSONObject("TRAFFIC_ITEMS").getJSONArray("TRAFFIC_ITEM");

            // iterate through all incident
            for (int i = 0; i < trafficItems.length(); i++) {       //todo: improve performance with java parallel Streams ?
                JSONObject trafficItem = trafficItems.getJSONObject(i);
                Incident incident = normalizeOneIncident(trafficItem.toString());

                // ignore null elements
                if (incident != null)
                    incidents.add(incident);
            }

        } catch (JSONException e) {
            //json cant be paresed
            throw new IllegalArgumentException(e);
        }

        return incidents;
    }

    private LocalDateTime parseDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        return LocalDateTime.parse(timeStr, formatter);
    }
}
