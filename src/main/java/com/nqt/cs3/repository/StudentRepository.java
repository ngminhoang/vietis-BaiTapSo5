package com.nqt.cs3.repository;

import com.nqt.cs3.domain.Student;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // boolean existsById(long id);
    // boolean existsByEmail(String email);
    Student findByEmail(String email);
    List<Student> findAll();
    @Query("SELECT s FROM Student s WHERE s.createdAt BETWEEN :startDate AND :endDate")
    List<Student> findAllByCreatedAtBetween(@Param("startDate") Instant startDate,
                                            @Param("endDate") Instant endDate);
}
