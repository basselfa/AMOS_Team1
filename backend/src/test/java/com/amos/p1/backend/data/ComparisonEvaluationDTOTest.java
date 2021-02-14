package com.amos.p1.backend.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@JsonTest
class ComparisonEvaluationDTOTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testMarshallingTime15_00() throws JsonProcessingException {

        LocalDateTime localDateTime = LocalDateTime.of(2020, 1,1,15,0);

        ComparisonEvaluationDTO comparisonEvaluationDTO = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO.setDate(localDateTime); // 01/01/2020 @ 3:00pm (UTC)

        String marshaled = objectMapper.writeValueAsString(comparisonEvaluationDTO);

        assertThat(marshaled, hasJsonPath("$.date", equalTo("2020-01-01 15:00")));
    }

    @Test
    void testMarshallingTime00_20() throws JsonProcessingException {

        LocalDateTime localDateTime = LocalDateTime.of(2020, 1,1,0,30);

        ComparisonEvaluationDTO comparisonEvaluationDTO = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO.setDate(localDateTime); // 01/01/2020 @ 12:30am (UTC)

        String marshaled = objectMapper.writeValueAsString(comparisonEvaluationDTO);

        assertThat(marshaled, hasJsonPath("$.date", equalTo("2020-01-01 00:30")));
    }

    @Test
    void testMarshallingDateFromLocalDateTime() throws JsonProcessingException {
        LocalDateTime localDateTime = LocalDateTime.of(2020, 1,1,15,0);

        ComparisonEvaluationDTO comparisonEvaluationDTO = new ComparisonEvaluationDTO();
        comparisonEvaluationDTO.setDate(localDateTime);

        String marshaled = objectMapper.writeValueAsString(comparisonEvaluationDTO);

        assertThat(marshaled, equalTo("{\"tomTomIncidentsAmount\":0,\"hereIncidentsAmount\":0,\"sameIncidentAmount\":0,\"date\":\"2020-01-01 15:00\"}"));
    }

    @Test
    void testDateVsLocalDateTime() throws JsonProcessingException {
        LocalDateTime localDateTime = LocalDateTime.of(2020, 1,1,15,0);
        Date date = Timestamp.valueOf(localDateTime);

        TestClass testClass = new TestClass();
        testClass.setLocalDateTime(localDateTime);
        testClass.setDate(date);

        String marshaled = objectMapper.writeValueAsString(testClass);

        assertThat(marshaled, equalTo("{\"localDateTime\":\"2020-01-01 15:00\",\"date\":\"2020-01-01 14:00\"}"));
    }


    private static class TestClass{

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime localDateTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        private Date date;

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}