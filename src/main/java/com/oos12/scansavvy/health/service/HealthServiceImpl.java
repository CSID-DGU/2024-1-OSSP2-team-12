package com.oos12.scansavvy.health.service;

import com.oos12.scansavvy.health.model.Health;
import com.oos12.scansavvy.health.repository.HealthRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class HealthServiceImpl implements HealthService{

    @Autowired
    private HealthRepository healthRepository;
    @Autowired
    public HealthServiceImpl(HealthRepository healthRepository){
        this.healthRepository = healthRepository;
    }

    @Value("${naver.service.url}")
    private String url;

    public Health saveHealth(Health health){
        log.info("Saving health record " + health);
        healthRepository.save(health);
        return health;
    }

    @Override
    public List<Health> findAll(){ return healthRepository.findAll();}

    @Override
    public Health findByEmail(String email){
        return healthRepository.findByEmail(email);
    }

    public List<String> callApi(String type, String filePath, String naver_secretKey,
                                String ext) {
        String apiURL = url;
        String secretKey = naver_secretKey;
        String imageFile = filePath;
        List<String> parseData = null;

        log.info("callApi Start!");
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod(type);
            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary =" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V1");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", ext);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);
            String postParams = json.toString();

            con.connect();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            File file = new File(imageFile);
            writeMultipart(wr, postParams, file, boundary);
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            parseData = jsonparse(response);
        } catch (IOException | ParseException | org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
        return parseData;
    }
    private static void writeMultipart(OutputStream out, String jsonMessage, File file,
                                       String boundary) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--").append(boundary).append("\r\n");
        stringBuilder.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
        stringBuilder.append(jsonMessage);
        stringBuilder.append("\r\n");

        out.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        out.flush();

        if (file != null && file.isFile()) {
            out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
            StringBuilder fileString = new StringBuilder();
            fileString
                    .append("Content-Disposition:form-data; name=\"file\"; filename=");
            fileString.append("\"" + file.getName() + "\"\r\n");
            fileString.append("Content-Type: application/octet-stream\r\n\r\n");
            out.write(fileString.toString().getBytes("UTF-8"));
            out.flush();

            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }
                out.write("\r\n".getBytes());
            }

            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
        }
        out.flush();
    }
    private static List<String> jsonparse(StringBuffer response) throws ParseException, org.json.simple.parser.ParseException {
        //json 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
        //images 배열 obj 화
        JSONArray JSONArrayPerson = (JSONArray)jsonObject.get("images");
        JSONObject JSONObjImage = (JSONObject)JSONArrayPerson.get(0);
        JSONArray s = (JSONArray) JSONObjImage.get("fields");
        //
        List<Map<String, Object>> m = JsonUtils.getListMapFromJsonArray(s);
        List<String> result = new ArrayList<>();
        for (Map<String, Object> as : m) {
            result.add((String) as.get("inferText"));
        }

        return result;
    }
}
