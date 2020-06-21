package com.teletronics.ms.model.entity;



import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Document(collection = "projects")
@Data
@ToString
@EqualsAndHashCode
public class TestEntity {

	@Id
	private String _id;
	private String userName;
    private List<ProjectDetails> projectDetails;	
}
