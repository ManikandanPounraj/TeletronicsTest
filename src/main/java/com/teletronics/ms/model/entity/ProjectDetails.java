package com.teletronics.ms.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetails {

	private String projectId;
	private String projectTitle;
	private String projectURL;
	private String projectDescription;
	private String totalCommits;
}
