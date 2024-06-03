package com.oos12.scansavvy.health.model;

import com.oos12.scansavvy.health.dto.HealthDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "healthResult")
public class Health {
    @Id
    private String id;
    @NotBlank
    private String Name;

    @NotBlank
    private String ResidentRegistrationNumber;

    private HealthDTO.HealthCheckResult HealthCheckResult;

    @Data
    public static class HealthCheckResult {
        private String ExaminationDate;
        private String BusinessName;
        private String StoreName;
        private HealthDTO.HealthCheckResult.TestItems TestItems;
        private String MedicalInstitutionCode;
        private String TestType;
        private String ReferenceValues;
        private String Name;
        private String Result;
        private String ResidentRegistrationNumber;
        private String NotificationDate;
        private String DepartmentName;
        private String ExaminationAgencyName;
        private HealthDTO.HealthCheckResult.AttendingPhysician AttendingPhysician;
        private String JudgmentDate;

        @Data
        public static class TestItems {
            private String BloodPressure;
            private HealthDTO.HealthCheckResult.TestItems.Urinalysis Urinalysis;
            private String CervicalSmear_CervicalCancerTest;
            private HealthDTO.HealthCheckResult.TestItems.BloodTest BloodTest;
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
    /*
    @Override
    public String toString(){
        return "healthResult{" +
                "Name='" + Name + '\'' +
                "ResidentRegistrationNumber='" + ResidentRegistrationNumber + '\'' +
                ", BusinessName='" + BusinessName +'\'' +
                ", StoreName='" + StoreName +'\'' +
                ", DepartmentName=" + DepartmentName + '\'' +
                ", Result=" + Result + '\'' +
                ", ReferenceValues=" + ReferenceValues + '\'' +
                ", TestType=" + TestType + '\'' +
                ", Height=" + Height + '\'' +
                ", Weight=" + Weight + '\'' +
                ", BodyFat=" + BodyFat + '\'' +
                ", Vision=" + Vision + '\'' +
                ", Hearing=" + Hearing + '\'' +
                ", BloodPressure=" + BloodPressure + '\'' +
                ", UrineTest=" + UrineTest + '\'' +
                ", Proteinuria=" + Proteinuria + '\'' +
                ", pH=" + pH + '\'' +
                ", Hemoglobin=" + Hemoglobin + '\'' +
                ", BloodSugar=" + BloodSugar +'\'' +
                ", TotalCholesterol=" + TotalCholesterol +'\'' +
                ", SerumGOT=" + SerumGOT +'\'' +
                ", SerumGPT=" + SerumGPT +'\'' +
                ", GammaGTP=" + GammaGTP +'\'' +
                ", HepatitisTest=" + HepatitisTest +'\'' +
                ", ChestXRay=" + ChestXRay +'\'' +
                ", CervicalSmear=" + CervicalSmear +'\'' +
                ", Electrocardiogram=" + Electrocardiogram +'\'' +
                ", DoctorName=" + DoctorName +'\'' +
                ", OpinionsAndMeasures=" + OpinionsAndMeasures +'\'' +
                ", MedicalInstitutionCode=" + MedicalInstitutionCode +'\'' +
                ", ExaminationAgencyName=" + ExaminationAgencyName +'\'' +
                ", ExaminationDate=" + ExaminationDate +'\'' +
                ", JudgmentDate=" + JudgmentDate +'\'' +
                ", NotificationDate=" + NotificationDate +'\'' +
                ", GeneratedText=" + GeneratedText +'\'' +
                '}';
    }

     */
}
