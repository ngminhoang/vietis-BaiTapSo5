package com.nqt.cs3.repository;

import com.nqt.cs3.constant.RoleEnum;
import com.nqt.cs3.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getRoleByName(RoleEnum name);
}
