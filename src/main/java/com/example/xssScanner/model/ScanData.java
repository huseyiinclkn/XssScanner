package com.example.xssScanner.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Component
@Table(name = "vulnerability")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ScanData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @ManyToOne
    @JoinColumn(name = "scan_id")
    private Scan scan;

    @Column(name = "type")
    private String type;
    @Column(name="inject_type")
    private String inject_type;
    @Column(name="poc_type")
    private String poc_type;
    @Column(name = "method")
    private String method;
    @Column(name="data")
    private String data;
    @Column(name="param")
    private String param;
    @Column(name="payload")
    private String payload;
    @Column(name="evidence")
    private String evidence;
    @Column(name="cwe")
    private String cwe;
    @Column(name="severity")
    private String severity;
    @Column(name = "message_id")
    private int message_id;
    @Column(name="message_str")
    private String message_str;
    @Column(name="duraction")
    private long duration;
}