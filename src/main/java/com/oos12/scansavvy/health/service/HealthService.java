package com.oos12.scansavvy.health.service;

import com.oos12.scansavvy.health.model.Health;
import com.oos12.scansavvy.member.model.Member;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface HealthService {
    List<Health> findAll();
    Health findByResidentRegistrationNumber(String ResidentRegistrationNumber);
    List<String> callApi(String type, String filePath, String naver_secretKey,
                                String ext);

    Health saveHealth(Health health);
}
