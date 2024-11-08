package com.nqt.cs3.service.IService;

import com.nqt.cs3.domain.Student;
import com.nqt.cs3.dto.RegisterDTO;

import java.util.List;

public interface IStudentService {
    Student save(Student student);

    Student findById(long id);

    Student findByEmail(String email);

    List<Student> findAll();

    Student update(Student student);

    void delete(long id);

    Student registerDtoToStudent(RegisterDTO registerDTO);
}
