package com.sye.microservices.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sye.microservices.mongo.domain.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}