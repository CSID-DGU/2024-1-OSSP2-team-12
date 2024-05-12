package com.oos12.scansavvy.health.controller;

import com.oos12.scansavvy.health.model.EeveResponse;
import com.oos12.scansavvy.health.service.EeveServiceImpl;
import com.oos12.scansavvy.health.service.HealthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HealthController {
    @Autowired
    private HealthServiceImpl healthService;
    @Autowired
    private EeveServiceImpl eeveService;

    @Value("${naver.service.secretKey}")
    private String secretKey;
    @GetMapping("/naverOcr")
    public ResponseEntity<?> ocr() throws IOException{
        String fileName = "건강검진테스트.png";
        File file = ResourceUtils.getFile("classpath:static/image/" + fileName);

        List<String> result = healthService.callApi("POST", file.getPath(), secretKey, "png");
        if (result != null){
            for (String s : result){
                log.info(s);
            }
        }else {
            log.info("null");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/OcrToJson")
    public ResponseEntity<?> OcrToJson() throws IOException, ParseException {
        String OcrText = String.valueOf(ocr());
        String promptMessage = "When you get the medical certificate in context, Convert it into a json file in korean. The keys are fixed. Certainly print it out according to the json format\n" +
                "\n" +
                "### Example\n" +
                "Medical examination result certificate:\n" +
                "성 명: [성명] 주민등록번호: [주민등록번호] 사업장명: [사업장명] 영업소명: [영업소명] 부서명: [부서명] 결 과 참고 치: [참고치] 구분: 정상A(건강양호) 신장: [신장] cm 체중: [체중] kg 체위검사: [비만도]% 시력: [좌/우] 청력: [좌/우] 혈압(최고/최저): [혈압] 요 당: 음성 요검사: 음성 요잠헐: 음성 요 pH: [pH] 혈색소: [혈색소] g/dL 혈 당(식전): [혈당] mg/dL 혈액검사 총콜레스테롤: [총콜레스테롤] mg/dl 혈청 지오티: [혈청지오티] U/L 헐청 지피티: [헐청지피티] U/L 감마 지티피: [감마지티피] U/L 간염검사: 정상 흉부방사선검사: 정상, 비활동성 유형별: 유형1, 유형2 자궁질도말세포병리(자궁암검사): 정상 심전도 검사: 정상 진찰 과거병력: 외상및후유증 상담 생활습관: 일반상태 판정: 판정 면허번호: [면허번호] 의사명 (인): [의사명] 소견 및 조치사항: [소견 및 조치사항] 요양기관기호: [요양기관기호] 검진기관명: [검진기관명] 검진일: [검진일] 판정일: [판정일] 통보일: [통보일] 국민건강보험공단에서 공단전액부담으로 실시하는 건강검진을 받으셨습니다. 검진결과가 정상으로 판정되었더라도 지속적인 건강관리를 통해 현재의 건강을 계속 유지해주시고, 판정결과가 질환의심인 경우는 반드시 2차검진을 받으시기 바랍니다.\n" +
                "\n" +
                "Result:\n" +
                "\"HealthCheckResult\": {\n" +
                "    \"Name\": \"[Name]\",\n" +
                "    \"ResidentRegistrationNumber\": \"[ResidentRegistrationNumber]\",\n" +
                "    \"BusinessName\": \"[BusinessName]\",\n" +
                "    \"StoreName\": \"[StoreName]\",\n" +
                "    \"DepartmentName\": \"[DepartmentName]\",\n" +
                "    \"Result\": \"Normal A (Healthy)\",\n" +
                "    \"ReferenceValues\": \"[ReferenceValues]\",\n" +
                "    \"TestType\": \"Type 1 Checkup\",\n" +
                "    \"TestItems\": {\n" +
                "        \"Height\": \"[Height] cm\",\n" +
                "        \"Weight\": \"[Weight] kg\",\n" +
                "        \"BodyFat\": \"[BodyFat]%\",\n" +
                "        \"Vision\": \"[Left/Right]\",\n" +
                "        \"Hearing\": \"[Left/Right]\",\n" +
                "        \"BloodPressure\": \"[BloodPressure] mmHg\",\n" +
                "        \"UrineTest\": \"Negative\",\n" +
                "        \"Urinalysis\": {\n" +
                "            \"Proteinuria\": \"Negative\",\n" +
                "            \"pH\": \"[pH]\"\n" +
                "        },\n" +
                "        \"Hemoglobin\": \"[Hemoglobin] g/dL\",\n" +
                "        \"BloodSugar (Fasting)\": \"[BloodSugar] mg/dL\",\n" +
                "        \"BloodTest\": {\n" +
                "            \"TotalCholesterol\": \"[TotalCholesterol] mg/dl\",\n" +
                "            \"SerumGOT\": \"[SerumGOT] U/L\",\n" +
                "            \"SerumGPT\": \"[SerumGPT] U/L\",\n" +
                "            \"GammaGTP\": \"[GammaGTP] U/L\"\n" +
                "        },\n" +
                "        \"HepatitisTest\": \"Normal\",\n" +
                "        \"ChestXRay\": \"Normal, Inactive Type: Type 1, Type 2\",\n" +
                "        \"CervicalSmear(CervicalCancerTest)\": \"Normal\",\n" +
                "        \"Electrocardiogram(ECG)\": \"Normal\"\n" +
                "    },\n" +
                "    \"AttendingPhysician\": {\n" +
                "        \"DoctorName\": \"[DoctorName]\",\n" +
                "        \"OpinionsAndMeasures\": \"[Opinions and Measures]\"\n" +
                "    },\n" +
                "    \"MedicalInstitutionCode\": \"[MedicalInstitutionCode]\",\n" +
                "    \"ExaminationAgencyName\": \"[ExaminationAgencyName]\",\n" +
                "    \"ExaminationDate\": \"[ExaminationDate]\",\n" +
                "    \"JudgmentDate\": \"[JudgmentDate]\",\n" +
                "    \"NotificationDate\": \"[NotificationDate]\"\n" +
                "}\n" +
                "\n" +
                "### Input\n" +
                "Medical examination result certificate:" + OcrText;
        promptMessage = promptMessage + "} \n" +
                "Result:\n";
        String result = String.valueOf(eeveService.generateMessage(promptMessage));
        result = result.substring(result.indexOf('{'), result.lastIndexOf('}')+1);
        System.out.println(result);
        JSONParser jsonParser= new JSONParser();
        Object obj = jsonParser.parse(result);
        JSONObject jsonObject = (JSONObject) obj;
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
    @GetMapping("/upload-form")
    public String uploadForm() throws Exception{
        return "/upload-form";
    }
    @PostMapping("/uploadAndOcr")
    public String uploadAndOcr(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            return "error";
        }

        String naverSecretKey = secretKey;
        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);
        List<String> result = healthService.callApi("POST", tempFile.getPath(), naverSecretKey, "png");
        tempFile.delete();
        model.addAttribute("ocrResult", result);
        return "ocr-result";
    }
}
