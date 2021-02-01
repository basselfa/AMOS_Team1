package com.amos.p1.backend.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class ComparisonEvaluationDTOTest {

    @Test
    void testMarshallingTime15_00() throws JsonProcessingException {
        ComparisonEvaluationDTO comparisonEvaluationDTO = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO.setDate(new Date(1577890800000L)); // 01/01/2020 @ 3:00pm (UTC)

        ObjectMapper objectMapper = new ObjectMapper();
        String marshaled = objectMapper.writeValueAsString(comparisonEvaluationDTO);

        assertThat(marshaled, hasJsonPath("$.date", equalTo("2020-01-01 15:00")));
    }

    @Test
    void testMarshallingTime00_20() throws JsonProcessingException {
        ComparisonEvaluationDTO comparisonEvaluationDTO = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO.setDate(new Date(1577838600000L)); // 01/01/2020 @ 12:30am (UTC)

        ObjectMapper objectMapper = new ObjectMapper();
        String marshaled = objectMapper.writeValueAsString(comparisonEvaluationDTO);

        assertThat(marshaled, hasJsonPath("$.date", equalTo("2020-01-01 00:30")));
    }
}