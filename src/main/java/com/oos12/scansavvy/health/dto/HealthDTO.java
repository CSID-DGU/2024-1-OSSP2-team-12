package com.oos12.scansavvy.health.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
public class HealthDTO {
    private String id;
    private String name;
    private String email;
    private String residentRegistrationNumber;
    private HealthCheckResult healthCheckResult;

    @Data
    public static class HealthCheckResult {
        private String examinationDate;
        private String businessName;
        private String storeName;
        private TestItems testItems;
        private String medicalInstitutionCode;
        private String testType;
        private String referenceValues;
        private String result;
        private String notificationDate;
        private String departmentName;
        private String examinationAgencyName;
        private AttendingPhysician attendingPhysician;
        private String judgmentDate;

        @Data
        public static class TestItems {
            private String bloodPressure;
            private Urinalysis urinalysis;
            private String cervicalSmearCervicalCancerTest;
            private BloodTest bloodTest;
            private String hepatitisTest;
            private String bodyFat;
            private String urineTest;
            private String weight;
            private String hearing;
            private String chestXRay;
            private String vision;
            private String hemoglobin;
            private String height;
            private String bloodSugarFasting;
            private String electrocardiogramECG;

            @Data
            public static class Urinalysis {
                private String pH;
                private String proteinuria;
            }

            @Data
            public static class BloodTest {
                private String serumGPT;
                private String gammaGTP;
                private String serumGOT;
                private String totalCholesterol;
            }
        }

        @Data
        public static class AttendingPhysician {
            private String doctorName;
            private String opinionsAndMeasures;
        }
    }
}
