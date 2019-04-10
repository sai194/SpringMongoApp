package com.sye.microservices.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sye.microservices.mongo.domain.Country;

public interface CountryRepository extends MongoRepository<Country, String>{

}
