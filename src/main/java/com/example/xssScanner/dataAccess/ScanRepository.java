package com.example.xssScanner.dataAccess;

import com.example.xssScanner.model.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository <Scan,Long > {


}
