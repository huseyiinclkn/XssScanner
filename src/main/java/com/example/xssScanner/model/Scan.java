    package com.example.xssScanner.model;

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

        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinTable(
                name = "lookup_scan_scan_data_relation",
                joinColumns = {@JoinColumn(name = "scan_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "scan_data_id", referencedColumnName = "id")}
        )
        private List<ScanData> data;

    }