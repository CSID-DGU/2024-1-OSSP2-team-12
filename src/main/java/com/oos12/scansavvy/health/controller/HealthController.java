package com.oos12.scansavvy.health.controller;

import com.oos12.scansavvy.health.dto.HealthDTO;
import com.oos12.scansavvy.health.model.Health;
import com.oos12.scansavvy.health.service.HealthServiceImpl;
import com.oos12.scansavvy.member.service.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/health")
public class HealthController {
    @Autowired
    private HealthServiceImpl healthService;
    @Autowired
    private ChatGPTController chatController;
    @Value("${naver.service.secretKey}")
    private String secretKey;
    private static final String UPLOAD_DIR = "/temp";
    @GetMapping(value = "/allHealth")
    public List<HealthDTO> getAllHealths(){
        return ObjectMapperUtils.mapAll(healthService.findAll(), HealthDTO.class);
    }
    @GetMapping(value = "/byEmail/{email}")
    public HealthDTO getByEmail(@PathVariable("email") String email){
        return ObjectMapperUtils.map(healthService.findByEmail(email), HealthDTO.class);
    }

    @GetMapping("/naverOcr")
    public ResponseEntity<?> ocr(String path) throws IOException{
        String fileName = "건강검진테스트.png";
        //File file = ResourceUtils.getFile("classpath:static/image/" + fileName);
        File file = ResourceUtils.getFile(path);
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
    public ResponseEntity<?> OcrToJson(String path) throws IOException, ParseException {
        String OcrText = String.valueOf(ocr(path));
        String promptMessage = "When you get the medical certificate in context, Convert it into a json file in korean. The keys are fixed. Certainly print it out according to the json format\n" +
                "if cannot find right value for the key, put value null instead\n" +
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
        String result = String.valueOf(chatController.generate(promptMessage));
        result = result.substring(result.indexOf('{')+1, result.lastIndexOf('}'));
        result = result.substring(result.indexOf('{'), result.lastIndexOf('}')+1);
        JSONParser jsonParser= new JSONParser();
        Object obj = jsonParser.parse(result);
        JSONObject jsonObject = (JSONObject) obj;
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
    @GetMapping("/generateOpinion")
    public ResponseEntity<?> generateOpinion(@RequestBody HealthDTO healthDTO) throws Exception {
        String dotorOpinion = healthDTO.getHealthCheckResult().getAttendingPhysician().getOpinionsAndMeasures();
        String promtmessage = "When you get the doctor's opinion for health, generate specific actions step by step in korean.\n" +
                "doctors's opinion: " + dotorOpinion + "\n" +
                "Specific method:";
        String result = String.valueOf(chatController.generate(promtmessage));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/healthReport/{email}")
    public ResponseEntity<?> healthReport(@PathVariable("email") String email, String path) throws Exception {
        ResponseEntity<?> jsonResponse = OcrToJson(path);
        log.info("OCR to JSON response: " + jsonResponse.getBody());

        JSONObject jsonObject = (JSONObject) jsonResponse.getBody();

        if (jsonObject == null) {
            log.error("OCR to JSON conversion failed");
            return new ResponseEntity<>("OCR to JSON conversion failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HealthDTO healthDTO = ObjectMapperUtils.map(jsonObject, HealthDTO.class);
        log.info("Mapped HealthDTO: " + healthDTO);

        ResponseEntity<?> opinionResponse = generateOpinion(healthDTO);
        String generatedOpinion = (String) opinionResponse.getBody();
        log.info("Generated doctor's opinion: " + generatedOpinion);

        if (generatedOpinion == null) {
            log.error("Doctor's opinion generation failed");
            return new ResponseEntity<>("Doctor's opinion generation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        healthDTO.getHealthCheckResult().getAttendingPhysician().setOpinionsAndMeasures(generatedOpinion);
        healthDTO.setEmail(email);
        ResponseEntity<?> saveResponse = saveHealth(healthDTO);
        log.info("Save response: " + saveResponse.getBody());

        if (!saveResponse.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to save health report");
            return new ResponseEntity<>("Failed to save health report", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Health report generated and saved successfully");
        return new ResponseEntity<>("Health report generated and saved successfully", HttpStatus.OK);
    }
    @PostMapping(value = "/jsonSave", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveHealth(@RequestBody HealthDTO healthDTO){
        String responseMessage = "Health Result saved success";
        if (healthDTO.getHealthCheckResult().getAttendingPhysician().getOpinionsAndMeasures() == null){
            responseMessage = "Registration Number null";
        }
        else healthService.saveHealth(ObjectMapperUtils.map(healthDTO, Health.class));

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    @GetMapping("/upload-form")
    public String uploadForm() throws Exception{
        return "/upload-form";
    }
    @PostMapping("/uploadAndOcr/{email}")
    public ResponseEntity<?> uploadAndOcr(@RequestParam("file") MultipartFile file,@PathVariable("email") String email) throws IOException, ParseException {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        // 저장 경로 설정
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);


        // 파일 저장
        try {
            Files.createDirectories(filePath.getParent());
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            log.error("Failed to save uploaded file", e);
            return new ResponseEntity<>("Failed to save uploaded file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            // OCR 처리 및 건강 보고서 생성
            ResponseEntity<?> healthReportResponse = healthReport(email, filePath.toString());

            if (!healthReportResponse.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<>("Failed to generate health report", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return healthReportResponse;
        } catch (Exception e) {
            log.error("Error generating health report", e);
            return new ResponseEntity<>("Error generating health report", HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            // 파일 삭제
            try {
                Files.delete(filePath);
                log.info("Temporary file deleted: " + filePath);
            } catch (IOException e) {
                log.error("Failed to delete temporary file: " + filePath, e);
            }
        }
    }
}
