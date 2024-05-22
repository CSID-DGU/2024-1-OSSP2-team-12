package com.oos12.scansavvy.health.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "health")
public class Health {
    @NotBlank
    private String Name;
    @Id
    @NotBlank
    private String ResidentRegistrationNumber;
    private String BusinessName;
    private String StoreName;
    private String DepartmentName;
    private String Result;
    private String ReferenceValues;
    private String TestType;
    private String Height;
    private String Weight;
    private String BodyFat;
    private String Vision;
    private String Hearing;
    private String BloodPressure;
    private String UrineTest;
    private String Proteinuria;
    private String pH;
    private String Hemoglobin;
    private String BloodSugar;
    private String TotalCholesterol;
    private String SerumGOT;
    private String SerumGPT;
    private String GammaGTP;
    private String HepatitisTest;
    private String ChestXRay;
    private String CervicalSmear;
    private String Electrocardiogram;
    private String DoctorName;
    private String OpinionsAndMeasures;
    private String MedicalInstitutionCode;
    private String ExaminationAgencyName;
    private String ExaminationDate;
    private String JudgmentDate;
    private String NotificationDate;

    @Override
    public String toString(){
        return "String";
    }
}
