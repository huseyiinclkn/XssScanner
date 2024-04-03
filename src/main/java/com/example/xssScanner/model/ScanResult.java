package com.example.xssScanner.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScanResult  {
    private int code;
    private String msg;
    private List<ScanData> data;
    private List<Scan> scan;

}




