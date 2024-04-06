package com.example.xssScanner.dataAccess;

import com.example.xssScanner.model.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Long> {
    @Query("SELECT s FROM Scan s JOIN s.data sd WHERE sd.severity = :severity")
    List<Scan> getScansWithFilter(@Param("severity") String severity);
}