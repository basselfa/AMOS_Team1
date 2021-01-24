package com.amos.p1.backend.service.requestcreator;

import com.amos.p1.backend.data.Request;
import com.amos.p1.backend.service.cityboundingbox.CityBoundingBoxesService;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestCreator {

    List<Request> buildRequests();

    void setTimeStamp(LocalDateTime timestamp);

    void setCityBoundingBoxes(CityBoundingBoxesService cityBoundingBoxesService);
}
