package com.amos.p1.backend.normalization;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Location;
import com.amos.p1.backend.data.Locations;
import com.amos.p1.backend.data.Point;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TomTomNormalization implements JsonToIncident {
    enum TomTomIncidents {
        ACCIDENT(Incident.IncidentTypes.ACCIDENT),
        JAM(Incident.IncidentTypes.CONGESTION),
        BROKENDOWNVEHICLE(Incident.IncidentTypes.DISABLEDVEHICLE),
        DANGEROUSCONDITIONS(Incident.IncidentTypes.ROADHAZARD),
        ROADWORKS(Incident.IncidentTypes.ROADWORKS),
        DETOUR(Incident.IncidentTypes.MISC),
        FOG(Incident.IncidentTypes.WEATHER),
        RAIN(Incident.IncidentTypes.WEATHER),
        ICE(Incident.IncidentTypes.WEATHER),
        WIND(Incident.IncidentTypes.WEATHER),
        FLOODING(Incident.IncidentTypes.WEATHER),
        UNKNOWN(Incident.IncidentTypes.MISC),
        ROADCLOSED(Incident.IncidentTypes.ROADCLOSURE),
        CLUSTER(Incident.IncidentTypes.MISC),
        LANECLOSED(Incident.IncidentTypes.LANERESTRICTION);

        private final Incident.IncidentTypes value;

        TomTomIncidents(Incident.IncidentTypes value) {
            this.value = value;
        }

        public int getID() {
            return value.getId();
        }
    }

    private String mapICNumberToString(Integer ic) {
        String[] icStrs = {
                "UNKNOWN",
                "ACCIDENT",
                "FOG",
                "DANGEROUSCONDITIONS",
                "RAIN",
                "ICE",
                "JAM",
                "LANECLOSED",
                "ROADCLOSED",
                "ROADWORKS",
                "WIND",
                "FLOODING",
                "DETOUR",
                "CLUSTER",
                "BROKENDOWNVEHICLE"
        };

        return icStrs[ic];
    }

    @Override
    public Incident normalizeOneIncident(String json) {
        Incident incJObj = new Incident();

        try {
            JSONObject incJSONObj = new JSONObject(json);

            incJObj.setProvider("1");

         //   incJObj.setTrafficId(Long.valueOf(incJSONObj.getString("id").substring(0, 5), 16)); // ID is hex and way to big for a long therefore as workaround cut to 5 chars but has to be fixed appropriately
            incJObj.setDescription(incJSONObj.getString("d"));
            incJObj.setType(String.valueOf(TomTomIncidents.valueOf(mapICNumberToString(incJSONObj.getInt("ic"))).toString()));
            incJObj.setStartPositionStreet(incJSONObj.getString("f"));
            incJObj.setEndPositionStreet(incJSONObj.getString("t"));
            incJObj.setSize(Integer.toString(incJSONObj.getInt("l")));
            incJObj.setEntryTime(parseDate(incJSONObj.getString("sd")));

            if (!incJSONObj.isNull("ed"))
                incJObj.setEndTime(parseDate(incJSONObj.getString("ed")));

            // sometimes v (shape as polyline) isn't given hence we can't decode it
            if (!incJSONObj.isNull("v")) {
                PolyLineDecoder PLDecoder = new PolyLineDecoder();
                String polyline = incJSONObj.getString("v");

                List<Point> allPoints = PLDecoder.decode(polyline);
                List<Point> edges = new ArrayList<>(allPoints);

                Point startPoint = edges.remove(0);
                Point endPoint = edges.remove(edges.size() - 1);

                //incJObj.setEdges(edges.toString());   // todo maybe change name to shape
                incJObj.setStartPositionLatitude(startPoint.toString().split(":")[0]);
                incJObj.setStartPositionLongitude(startPoint.toString().split(":")[1]);
                incJObj.setEndPositionLatitude(endPoint.toString().split(":")[0]);
                incJObj.setEndPositionLongitude(endPoint.toString().split(":")[1]);

                Locations lEdges = new Locations();
                for (Point point : edges) {
                    Location edge = new Location(String.valueOf(point.getLat()), String.valueOf(point.getLng()));
                    lEdges.addLocation(edge);
                }
                incJObj.setEdgesAsLocations(lEdges);
            }


        } catch (JSONException e) { //There was an Exception with the JSONObject
            throw new IllegalStateException(e);
        }

        return incJObj;
    }

    @Override
    public List<Incident> normalize(String json) {
        List<Incident> incidentList = new LinkedList<>();

        try {
            JSONObject incJSONObj = new JSONObject(json);
            JSONObject wrapper;
            JSONArray clusterArray;
            List<JSONArray> clusterPointArray;

            // try to determine if this is a single incident based on existence of description and from key
            if (!incJSONObj.isNull("d") && !incJSONObj.isNull("f")) {
                incidentList.add(normalizeOneIncident(json));
                return incidentList;
            }

            // if theres no wrapper return null
            if (incJSONObj.isNull("tm"))
                return null;

            // get the wrapper "tm"
            wrapper = getWrapper(incJSONObj);

            // if "wrapper" has no array of clusters return null
            if (wrapper.isNull("poi"))
                return null;

            clusterArray = getClusterArray(wrapper);

            // this means there were no clusters in this wrapper
            if (clusterArray.length() < 1)
                return null;

            clusterPointArray = getClusterPointArray(clusterArray);

            // normalize one incident at a time
            for (JSONArray cPoints : clusterPointArray) { //todo: improve performance with java parallel Streams ?
                for (int j = 0; j < cPoints.length(); j++)
                    incidentList.add(normalizeOneIncident(cPoints.getJSONObject(j).toString()));
            }


        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }


        return incidentList;
    }

    public JSONObject getWrapper(JSONObject obj) {
        JSONObject wrapper;
        try {
            wrapper = obj.getJSONObject("tm");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return wrapper;
    }

    public JSONArray getClusterArray(JSONObject wrapper) {
        JSONArray clusterArray;
        try {
            clusterArray = wrapper.getJSONArray("poi");
        } catch (JSONException e) { // couldn't get poi
            e.printStackTrace();
            return null;
        }
        return clusterArray;
    }

    public List<JSONArray> getClusterPointArray(JSONArray clusterArray) {
        List<JSONArray> clusterPointArray = new LinkedList<>();
        try {
            for (int i = 0; i < clusterArray.length(); i++) {
                JSONObject c = clusterArray.getJSONObject(i);

                // try to determine if this is a single incident based on existence of description and from key
                if (!c.isNull("d") && !c.isNull("f")) {
                    JSONArray jsonArray = new JSONArray();
                    JSONObject cpoiWrap = new JSONObject();

                    jsonArray.put(c);
                    cpoiWrap.put("cpoi", jsonArray);
                    c = cpoiWrap;
                }

                // if this cluster has no clusterPoints
                if (c.isNull("cpoi"))
                    continue;

                clusterPointArray.add(c.getJSONArray("cpoi"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return clusterPointArray;
    }


    private LocalDateTime parseDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return LocalDateTime.parse(timeStr, formatter);


    }

}
