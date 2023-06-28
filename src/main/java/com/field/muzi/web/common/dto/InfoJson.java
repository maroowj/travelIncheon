package com.field.muzi.web.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InfoJson {
    private String page;
    private int totalCnt;
    private String result;
    private Data data;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Data {
        private String infoTopBanner;
        private List<InfoOption> infoOption;

        @lombok.Data
        @AllArgsConstructor
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        public static class InfoOption {
            private String eng;
            private String type;
            private String used;
            private String title;
            private String required;
            private Radios radios;
            private Address addr;
            private Inputs input;
            private Checkboxes checkboxes;

            @lombok.Data
            @AllArgsConstructor
            @NoArgsConstructor(access = AccessLevel.PROTECTED)
            public static class Address {
                private String city;
                private String gu;
                private String dong;
            }

            @lombok.Data
            @AllArgsConstructor
            public static class Inputs {

            }

            @lombok.Data
            @AllArgsConstructor
            public static class Checkboxes {

            }

            @lombok.Data
            @AllArgsConstructor
            @NoArgsConstructor(access = AccessLevel.PROTECTED)
            public static class Radios {
                private int optionCnt;
                private List<Option> options;

                @lombok.Data
                @AllArgsConstructor
                @NoArgsConstructor(access = AccessLevel.PROTECTED)
                public static class Option {
                    private String option;
                    private String selected;
                }
            }
        }
    }
}
