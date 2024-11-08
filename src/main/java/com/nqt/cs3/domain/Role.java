package com.nqt.cs3.domain;

import java.util.List;

import com.nqt.cs3.constant.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    private String description;

    @OneToMany(mappedBy = "role")
    private List<Student> students;
}
