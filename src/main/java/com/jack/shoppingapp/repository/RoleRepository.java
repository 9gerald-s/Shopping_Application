package com.jack.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jack.shoppingapp.entity.Role;
import com.jack.shoppingapp.models.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Enum<ERole>>{

}
