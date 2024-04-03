package com.example.xssScanner.service;

import com.example.xssScanner.dataAccess.ScanDataRepository;
import com.example.xssScanner.dataAccess.ScanRepository;
import com.example.xssScanner.model.Scan;
import com.example.xssScanner.model.ScanData;
import com.example.xssScanner.model.ScanResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.sql.Timestamp;

@Service
public class DalfoxService {

    private final WebClient webClient = WebClient.create("http://192.168.56.101:6664"); // API sunucusunun adresi ve portu

    private final Scan scan;
    private final ScanDataRepository scanDataRepository;
    private final ScanRepository scanRepository;

    public DalfoxService(Scan scan, ScanDataRepository scanDataRepository, ScanRepository scanRepository) {
        this.scan = scan;
        this.scanDataRepository = scanDataRepository;
        this.scanRepository = scanRepository;
    }

    public void addScan(Scan scan) {
        scanRepository.save(scan);
    }

    public String scanUrl(String url) throws IOException {

        String scanUrl = "/scan";


        String requestBody = "{\"options\": {\"timeout\": 50, \"worker\": 30, \"mining-dict\": true, \"mining-dom\": true, \"user-agent\": \"ChromeTestUA\", \"trigger\": \"url\"}, \"url\": \"" + url + "\"}";

        return webClient.post()
                .uri(scanUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getScanResult(String msg, String severity) throws JsonProcessingException {
        String scanUrl = "/scan/" + msg;


            String responseBody = webClient.get()
                    .uri(scanUrl)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ScanResult scanResult = objectMapper.readValue(responseBody, ScanResult.class);

            if (scanResult.getMsg() == null) {
                return "Tarama sonucu alınırken bir hata oluştu: 'msg' değeri boş.";
            }

            if (scanResult.getMsg().equals("finish")) {
                if (scanResult.getData() != null && !scanResult.getData().isEmpty()) {
                    for (ScanData data : scanResult.getData()) {
                        if (severity.equals(data.getSeverity())) {
                            scan.setScanEndTime(new Timestamp(System.currentTimeMillis()));
                            addScan(scan);
                            scanDataRepository.save(data);
                            return data.getMessage_str();
                        }
                    }
                }
                return "Veri bulunamadı";
            }

            return "Tarama Henüz Tamamlanmadı.....";

        }
}
