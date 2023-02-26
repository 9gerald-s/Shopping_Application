package com.jack.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jack.shoppingapp.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

}
