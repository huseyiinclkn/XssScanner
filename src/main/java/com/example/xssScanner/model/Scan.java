    package com.example.xssScanner.model;

    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import lombok.ToString;
    import org.springframework.stereotype.Component;

    import java.sql.Timestamp;
    import java.util.List;


    @Entity
    @Component
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @Table(name = "scans")
    public class Scan {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "scanName")
        private String scanName;

        @Column(name = "scanUrl")
        private String scanUrl;

        @Column(name="scanStartTime")
        private Timestamp scanStartTime;

        @Column(name = "scanEndTime")
        private Timestamp scanEndTime;


        @OneToMany(mappedBy = "scan")

        private List<ScanData> data;

    }