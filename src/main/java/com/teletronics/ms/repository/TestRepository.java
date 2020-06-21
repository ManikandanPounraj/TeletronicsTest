package com.teletronics.ms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import com.teletronics.ms.model.entity.TestEntity;



@Repository
public interface TestRepository extends MongoRepository<TestEntity, String>{

	TestEntity findByUserNameAndProjectDetails_ProjectId(String userName, String projectId);
	TestEntity findByUserName(String userName);
}
