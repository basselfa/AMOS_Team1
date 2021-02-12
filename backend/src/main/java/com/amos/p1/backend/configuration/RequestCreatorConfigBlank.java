package com.amos.p1.backend.configuration;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;
import com.amos.p1.backend.service.requestcreator.RequestCreator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("blank")
public class RequestCreatorConfigBlank implements RequestCreatorConfig{

    @Override
    public RequestCreator getRequestCreator() {
        return new RequestCreatorBlank();
    }

    static class RequestCreatorBlank implements RequestCreator{

        @Override
        public List<Request> buildRequests() {
            return new ArrayList<>();
        }

        @Override
        public void setTimeStamp(LocalDateTime timestamp) {

        }

        @Override
        public void setCityBoundingBoxes(CityBoundingBoxesService cityBoundingBoxesService) {

        }
    }
}
