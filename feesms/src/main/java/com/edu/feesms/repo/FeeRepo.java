package com.edu.feesms.repo;

import com.edu.feesms.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeeRepo extends JpaRepository<Fee, Integer> {
    Optional<List<Fee>> findByStudentId(Integer studentId);
}
