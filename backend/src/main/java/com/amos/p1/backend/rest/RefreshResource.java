package com.amos.p1.backend.rest;

import com.amos.p1.backend.configuration.CityBoundingBoxServiceConfigProduction;
import com.amos.p1.backend.configuration.RequestCreatorConfigProduction;
import com.amos.p1.backend.data.CityInformation;
import com.amos.p1.backend.database.MyRepo;
import com.amos.p1.backend.service.ProviderIntervalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("withDatabase")
public class RefreshResource {

    private static final Logger log = LoggerFactory.getLogger(RefreshResource.class);


    private final ProviderIntervalRequest providerIntervalRequest;

    public RefreshResource(@Autowired ProviderIntervalRequest providerIntervalRequest){
        this.providerIntervalRequest = providerIntervalRequest;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/refresh"
    )
    @ResponseBody
    public ResponseEntity<Void> refresh(){

        log.info("Refresh");
        providerIntervalRequest.providerCronJob();

        return ResponseEntity.ok().build();
    }

}
