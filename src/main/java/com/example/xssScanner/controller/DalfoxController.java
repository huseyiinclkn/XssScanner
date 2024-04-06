package com.example.xssScanner.controller;

import com.example.xssScanner.model.Scan;
import com.example.xssScanner.service.DalfoxService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;

@Controller
public class DalfoxController {


    private final DalfoxService dalfoxService;
    private final  Scan scan;


  DalfoxController(Scan scan ,DalfoxService dalfoxService){
      this.scan = scan;
      this.dalfoxService=dalfoxService;
  }

    @GetMapping("/scan")
    public String showScanForm() {
        return "scan";
    }

    @PostMapping("/addScanName")
    @ResponseBody
    public void addScanName(@RequestParam("scanName") String scanName) {

        scan.setScanName(scanName);
        dalfoxService.addScan(scan);
    }

    @PostMapping("/test")
    @ResponseBody
    public String scanUrl(@RequestParam("url") String url) {
        try {
            scan.setScanUrl(url);
            scan.setScanStartTime(new Timestamp(System.currentTimeMillis()));
            dalfoxService.addScan(scan);

            return dalfoxService.scanUrl(url);
        } catch (IOException e) {
            return "Error scanning URL: " + e.getMessage();
        }
    }

    @GetMapping("/scan/{msg}")
    @ResponseBody
    public ResponseEntity<String> getScanResult(@PathVariable(value = "msg") String msg) throws IOException {
        JSONObject jsonObject = new JSONObject(msg);
        String msgValue = jsonObject.getString("msg");

        String scanResult = dalfoxService.getScanResult(msgValue);

        return ResponseEntity.ok().body(scanResult);
    }


    @PostMapping("/filter")
    public ResponseEntity<String> filter(@RequestParam(value = "severity") String severity) {
        String filterResult = dalfoxService.getFilteredScanIndexes(severity).toString();
        return ResponseEntity.ok().body(filterResult);
    }


}
