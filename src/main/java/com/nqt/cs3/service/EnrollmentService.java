package com.nqt.cs3.service;

import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.repository.EnrollmentRepository;
import com.nqt.cs3.service.IService.IEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EnrollmentService implements IEnrollmentService {
    @Autowired
    public EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment findById(long id) {
        return this.enrollmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Enrollment> findAll() {
        return this.enrollmentRepository.findAll();
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        return this.enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        Enrollment currentEnrollment = this.findById(enrollment.getId());
        if(currentEnrollment != null){
            currentEnrollment.setId(enrollment.getId());
            currentEnrollment.setEnrollmentDate(enrollment.getEnrollmentDate());
            currentEnrollment.setDateStart(enrollment.getDateStart());
            currentEnrollment.setDateEnd(enrollment.getDateEnd());
            currentEnrollment.setStatus(enrollment.getStatus());
            return this.enrollmentRepository.save(currentEnrollment);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        this.enrollmentRepository.deleteById(id);
    }
}
