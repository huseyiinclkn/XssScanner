package com.example.xssScanner.saveFile;

import com.example.xssScanner.model.ScanData;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class ScanSaveFile implements ScanResultAccessor {

    @Override
    public String getScanData(ScanData scanData) {
        try {

            String dataDirectory = "C:\\Datalar\\";


            String filePath = dataDirectory + "kaydetme.txt";


            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(scanData.getMessage_str());
            }


            return "Veri başarıyla kaydedildi!";

        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException("Dosyaya tarama sonucunu kaydederken hata oluştu: " + e.getMessage());
        }
    }
}
