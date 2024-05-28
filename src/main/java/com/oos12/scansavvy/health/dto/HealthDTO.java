package com.oos12.scansavvy.health.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
@Document(collection = "healthResult")
public class HealthDTO {

    private HealthCheckResult HealthCheckResult;

    @Data
    public static class HealthCheckResult {
        private String ExaminationDate;
        private String BusinessName;
        private String StoreName;
        private TestItems TestItems;
        private String MedicalInstitutionCode;
        private String TestType;
        private String ReferenceValues;
        private String Name;
        private String Result;
        private String ResidentRegistrationNumber;
        private String NotificationDate;
        private String DepartmentName;
        private String ExaminationAgencyName;
        private AttendingPhysician AttendingPhysician;
        private String JudgmentDate;

        @Data
        public static class TestItems {
            private String BloodPressure;
            private Urinalysis Urinalysis;
            private String CervicalSmear_CervicalCancerTest;
            private BloodTest BloodTest;
            private String HepatitisTest;
            private String BodyFat;
            private String UrineTest;
            private String Weight;
            private String Hearing;
            private String ChestXRay;
            private String Vision;
            private String Hemoglobin;
            private String Height;
            private String BloodSugar_Fasting;
            private String Electrocardiogram_ECG;

            @Data
            public static class Urinalysis {
                private String pH;
                private String Proteinuria;
            }

            @Data
            public static class BloodTest {
                private String SerumGPT;
                private String GammaGTP;
                private String SerumGOT;
                private String TotalCholesterol;
            }
        }

        @Data
        public static class AttendingPhysician {
            private String DoctorName;
            private String OpinionsAndMeasures;
        }
    }
}
