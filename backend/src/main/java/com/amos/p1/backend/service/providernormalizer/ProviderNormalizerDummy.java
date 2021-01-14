package com.amos.p1.backend.service.providernormalizer;

import com.amos.p1.backend.Helper;
import com.amos.p1.backend.data.EvaluationCandidate;
import com.amos.p1.backend.data.Incident;
import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.normalization.HereNormalization;
import com.amos.p1.backend.normalization.JsonToIncident;
import com.amos.p1.backend.normalization.TomTomNormalization;
import com.amos.p1.backend.service.evaluation.Evaluation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ProviderNormalizerDummy implements ProviderNormalizer {

    protected enum Provider {
        HERE,TOMTOM
    }

    protected final static String T2020_01_01_HERE_BERLIN_SMALL = "provider-dummy-data/2020-01-01_00:00_small/HereBerlin.json";
    protected final static String T2020_01_01_TOMTOM_BERLIN_SMALL = "provider-dummy-data/2020-01-01_00:00_small/TomTomBerlin.json";


    protected static final String T2020_01_14_HERE_BERLIN = "provider-dummy-data/2020-01-14_10:00/here-berlin.json";
    protected static final String T2020_01_14_TOMTOM_BERLIN = "provider-dummy-data/2020-01-14_10:00/here-berlin.json";

    protected static final String T2020_01_14_HERE_FRANKFURT = "provider-dummy-data/2020-01-14_10:00/here-frankfurt_am_main.json";
    protected static final String T2020_01_14_TOMTOM_FRANKFURT = "provider-dummy-data/2020-01-14_10:00/tomtom-frankfurt_am_main.json";

    protected static final String T2020_01_14_HERE_HAMBURG = "provider-dummy-data/2020-01-14_10:00/here-hamburg.json";
    protected static final String T2020_01_14_TOMTOM_HAMBURG = "provider-dummy-data/2020-01-14_10:00/tomtom_hamburg.json";

    protected static final String T2020_01_14_HERE_MUENCHEN = "provider-dummy-data/2020-01-14_10:00/here-muenchen.json";
    protected static final String T2020_01_14_TOMTOM_MUENCHEN = "provider-dummy-data/2020-01-14_10:00/tomtom-muenchen.json";

    protected static final String T2020_01_14_HERE_NUERNBERG = "provider-dummy-data/2020-01-14_10:00/here-nuernberg.json";
    protected static final String T2020_01_14_TOMTOM_NUERNBERG = "provider-dummy-data/2020-01-14_10:00/tomtom-nuernberg.json";


    protected static final String T2020_01_15_HERE_BERLIN = "provider-dummy-data/2020-01-15_00:00/here-berlin.json";
    protected static final String T2020_01_15_TOMTOM_BERLIN = "provider-dummy-data/2020-01-15_00:00/here-berlin.json";

    protected static final String T2020_01_15_HERE_MUENCHEN = "provider-dummy-data/2020-01-15_00:00/here-muenchen.json";
    protected static final String T2020_01_15_TOMTOM_MUENCHEN = "provider-dummy-data/2020-01-15_00:00/tomtom-muenchen.json";

    protected final Map<String, Map<Provider, String>> cities = new HashMap<>();

    protected Map<Provider, String> getProviderData(String hereData, String tomtomData){

        Map<Provider, String> providerData = new HashMap<>();
        providerData.put(Provider.HERE, hereData);
        providerData.put(Provider.TOMTOM, tomtomData);

        return providerData;
    }

    protected Request getRequest(String city, String Path, String tomtomPath, LocalDateTime timestamp) {
        JsonToIncident normalizationHere = new HereNormalization();
        List<Incident> normalizedHere = normalizationHere.normalize(Helper.getFileResourceAsString(Path));

        JsonToIncident normalizationTomTom = new TomTomNormalization();
        List<Incident> normalizedTomTom = normalizationTomTom.normalize(Helper.getFileResourceAsString(tomtomPath));

        List<Incident> incidents = new ArrayList<>();
        incidents.addAll(normalizedHere);
        incidents.addAll(normalizedTomTom);

        Request request = new Request();
        request.setCityName(city);
        request.setIncidents(incidents);
        request.setRequestTime(timestamp);

        Evaluation evaluation = new Evaluation();
        List<EvaluationCandidate> evaluationCandidates = evaluation.calculateCandidates(request);
        evaluationCandidates = evaluation.dropManifolds(evaluationCandidates);
        request.setEvaluatedCandidates(evaluationCandidates);

        return request;
    }

}
