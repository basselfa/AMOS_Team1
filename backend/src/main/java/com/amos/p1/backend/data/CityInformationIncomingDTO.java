package com.amos.p1.backend.data;

import javax.persistence.*;
import java.util.Objects;

public class CityInformationIncomingDTO {
        private String cityName;
        private String centreLatitude;
        private String centreLongitude;
        private int searchRadiusInMeter;

        @Basic
        @Column(name = "cityName", nullable = true)
        public String getCityName() {
            return cityName;
        }
        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        @Basic
        @Column(name = "centreLatitude", nullable = true)
        public String getCentreLatitude() {
            return centreLatitude;
        }
        public void setCentreLatitude(String centreLatitude) {
            this.centreLatitude = centreLatitude;
        }

        @Basic
        @Column(name = "centreLongitude", nullable = true)
        public String getCentreLongitude() {
            return centreLongitude;
        }
        public void setCentreLongitude(String centreLongitude) {
            this.centreLongitude = centreLongitude;
        }

        @Basic
        @Column(name = "searchRadiusInMeter", nullable = true)
        public int getSearchRadiusInMeter() {
            return searchRadiusInMeter;
        }
        public void setSearchRadiusInMeter(int searchRadiusInMeter) {
            this.searchRadiusInMeter = searchRadiusInMeter;
        }

        @Override
        public String toString() {
            return "CityInformationIncomingDTO{" +
                    ", cityName='" + cityName + '\'' +
                    ", centreLatitude='" + centreLatitude + '\'' +
                    ", centreLongitude='" + centreLongitude + '\'' +
                    ", searchRadiusInMeter=" + searchRadiusInMeter +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(cityName, centreLatitude, centreLongitude, searchRadiusInMeter);
        }
}