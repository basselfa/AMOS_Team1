package com.amos.p1.backend;

import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.service.IncidentAggregator;
import com.amos.p1.backend.service.IncidentAggregatorDummy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Restcontroller only for static demo purposes. Only use dummy strings for returning to client
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("demo")
public class ResourceDemo {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/incidents",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<String> getIncidentsByCity(@RequestParam("city") String city){

        String response = getIncidentsResponseString();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/historization",
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    // to be refined with entryTime - getIncidentsByEntryTime()
    @ResponseBody
    public ResponseEntity<String> getAllIncidents(){

        String response = getAllIncidentsResponseString();
        return ResponseEntity.ok(response);
    }

    /*
    @ResponseBody
    public ResponseEntity<String> getIncidentsByEntryTime(@RequestParam("entryTime") String entryTime){
        String response = getHistorizationResponseString();

        return ResponseEntity.ok(response);
    }
    */

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/comparisons",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<String> getComparionsByCity(@RequestParam("city") String city){

        String response = getComparisonsResponseString();

        return ResponseEntity.ok(response);
    }

    private String getIncidentsResponseString() {
        String response = "{\n" +
                "  \"incidents\": [\n" +
                "    {\n" +
                "      \"providerId\": 1,\n" +
                "      \"shape\": [\n" +
                "        {\n" +
                "          \"latitude\": \"52.509041\",\n" +
                "          \"longitude\": \"13.326355\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.511908\",\n" +
                "          \"longitude\": \"13.322278\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.512626\",\n" +
                "          \"longitude\": \"13.323158\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.513089\",\n" +
                "          \"longitude\": \"13.330550\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"description\": \"Closed due construction\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"providerId\": 2,\n" +
                "      \"shape\": [\n" +
                "        {\n" +
                "          \"latitude\": \"52.508075\",\n" +
                "          \"longitude\": \"13.328351\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.512913\",\n" +
                "          \"longitude\": \"13.326806\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"description\": \"Closed\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"providerId\": 2,\n" +
                "      \"shape\": [\n" +
                "        {\n" +
                "          \"latitude\": \"52.514591\",\n" +
                "          \"longitude\": \"13.322257\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.516276\",\n" +
                "          \"longitude\": \"13.316517\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"description\": \"Demonstration\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return response;
    }

    private String getComparisonsResponseString() {
        String response = "{\n" +
                "  \"comparisons\": [\n" +
                "    {\n" +
                "      \"shape\": [\n" +
                "        {\n" +
                "          \"latitude\": \"52.509041\",\n" +
                "          \"longitude\": \"13.326355\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.511908\",\n" +
                "          \"longitude\": \"13.322278\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.512626\",\n" +
                "          \"longitude\": \"13.323158\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"latitude\": \"52.513089\",\n" +
                "          \"longitude\": \"13.330550\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"provider1\": {\n" +
                "        \"providerId\": 2,\n" +
                "        \"shape\": [\n" +
                "          {\n" +
                "            \"latitude\": \"52.508075\",\n" +
                "            \"longitude\": \"13.328351\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"latitude\": \"52.512913\",\n" +
                "            \"longitude\": \"13.326806\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"description\": \"Closed\"\n" +
                "      },\n" +
                "      \"provider2\": {\n" +
                "        \"providerId\": 1,\n" +
                "        \"shape\": [\n" +
                "          {\n" +
                "            \"latitude\": \"52.509041\",\n" +
                "            \"longitude\": \"13.326355\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"latitude\": \"52.511908\",\n" +
                "            \"longitude\": \"13.322278\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"latitude\": \"52.512626\",\n" +
                "            \"longitude\": \"13.323158\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"latitude\": \"52.513089\",\n" +
                "            \"longitude\": \"13.330550\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"description\": \"Closed due construction\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return response;
    }

    private String getAllIncidentsResponseString() {
        String response = "{\n" +
                "  \"incidents\": [\n" +
                "    {\n" +
                "      \"providerId\": 1,\n" +
                "      \"type\": \"construction\",\n" +
                "      \"city\": \"Berlin\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"providerId\": 2,\n" +
                "      \"type\": \"traffic jam\",\n" +
                "      \"city\": \"Berlin\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return response;
    }
}
