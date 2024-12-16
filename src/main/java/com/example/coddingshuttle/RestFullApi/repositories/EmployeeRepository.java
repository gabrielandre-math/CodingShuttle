package com.example.coddingshuttle.RestFullApi.repositories;

import com.example.coddingshuttle.RestFullApi.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
