package com.teletronics.ms.controller.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.teletronics.ms.exception.CustomCodeException;
import com.teletronics.ms.exception.ErrorResponseDTO;
import com.teletronics.ms.model.entity.ProjectDetails;
import com.teletronics.ms.model.entity.TestEntity;
import com.teletronics.ms.repository.TestRepository;
import com.teletronics.ms.service.TestSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Teletronics Test MS", tags = "Teletronics Test MS")
@RestController
@Validated
@Slf4j
@RequestMapping(value = "/projects")
public class TestController{
	

    private TestSearchService testSearchService;


    
	@Autowired
	public TestController(TestRepository repo, TestSearchService testSearchService) {
		this.testSearchService=testSearchService;
		
	}
	
	 
	 @ApiOperation(
	            value = "UserDetails by UserName",
	            notes = "UserDetails by UserName"
	    )
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, response = TestEntity.class, message = "Success"),
	            @ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
	            @ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
	            @ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
	            @ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error")})
	    @ResponseStatus(code = HttpStatus.OK)
	  @RequestMapping(
	            path = "{username}",
	            method = {RequestMethod.GET},
	            produces = APPLICATION_JSON_UTF8_VALUE)
	    public TestEntity searchUserName(
	            @PathVariable(value = "username")
	            String userName) throws CustomCodeException {
		 
		 log.info("Recieved the request for Getting the Details Using UserName = {}",userName);
		return testSearchService.searchByUserName(userName);
		 
	    }	
	 
	 
	 @ApiOperation(
	            value = "Project Details by UserName & ProjectId",
	            notes = "Project Details by UserName & ProjectId"
	    )
	    @ApiResponses(value = {
	            @ApiResponse(code = 200, response = ProjectDetails.class, message = "Success"),
	            @ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
	            @ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
	            @ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
	            @ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error")})
	    @ResponseStatus(code = HttpStatus.OK)
	  @RequestMapping(
	            path = "{username}/{project_id}",
	            method = {RequestMethod.GET},
	            produces = APPLICATION_JSON_UTF8_VALUE)
	    public ProjectDetails searchProjectDetails(
	            @PathVariable(value = "username")
	            String userName,
	            @PathVariable(value = "project_id")
	            String projectId) throws CustomCodeException {
		 
		 log.info("Recieved the request for Getting the Details Using UserName = {} & ProjectId = {}",userName);
		return testSearchService.searchByProjectId(projectId, userName);
		 
	    }	 
	 
	 
}