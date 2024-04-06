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
import java.util.ArrayList;
import java.util.List;

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

    public String getScanResult(String msg) throws JsonProcessingException {
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
                    scan.setData(scanResult.getData());
                    scan.setScanEndTime(new Timestamp(System.currentTimeMillis()));
                    addScan(scan);




                }
                return "Tarama Kaydedildi.";
            }

            return "Tarama Henüz Tamamlanmadı.....";

        }
    public List<String> getFilteredScanIndexes(String severity) {
        List<String> filteredIndexes = new ArrayList<>();
        List<Scan> scanList = scanRepository.getScansWithFilter(severity);

        int index = 1;
        for (Scan scan : scanList) {
            for (ScanData scanData : scan.getData()) {
                if (scanData.getSeverity().equalsIgnoreCase(severity)) {
                    filteredIndexes.add(index++ + " - " + scan.getId());
                    break; // Birden fazla aynı severity değeri varsa, sadece bir kez eklemek için break kullanıyoruz.
                }
            }
        }

        return filteredIndexes;
    }
}
