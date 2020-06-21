package com.teletronics.ms.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teletronics.ms.exception.CustomCodeException;
import com.teletronics.ms.model.entity.ProjectDetails;
import com.teletronics.ms.model.entity.TestEntity;
import com.teletronics.ms.repository.TestRepository;

@Service
public class TestSearchService{

	@Autowired
	TestRepository repo;
	
	public TestEntity searchByUserName(String userName) throws CustomCodeException {
		TestEntity userResults = repo.findByUserName(userName);
		if(userResults != null)
		return userResults;
		else
	    throw new CustomCodeException("userResults are null");
	}

	public ProjectDetails searchByProjectId(String projectId, String userName) throws CustomCodeException {
		TestEntity userResults = repo.findByUserNameAndProjectDetails_ProjectId(userName, projectId);
	    if(userResults != null) {
	    	ProjectDetails details = null;
	    	for(ProjectDetails p : userResults.getProjectDetails()) {
	    		if(p.getProjectId().equalsIgnoreCase(projectId)) {
	    			details = p;
	    		}
	    	}
	    	return details;
	    }
	    else
	    throw new CustomCodeException("ProjectResults are Null");
	}

	

}
