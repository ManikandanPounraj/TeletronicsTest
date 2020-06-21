package com.teltronics.ms;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.teletronics.ms.BootstrapApplication;
import com.teletronics.ms.controller.rest.TestController;
import com.teletronics.ms.model.entity.ProjectDetails;
import com.teletronics.ms.model.entity.TestEntity;
import com.teletronics.ms.repository.TestRepository;
import com.teletronics.ms.service.MemcachedHealthIndicator;
import com.teletronics.ms.service.TestSearchService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
@ActiveProfiles("dev")
@ComponentScan(basePackages = "com.*")
@ContextConfiguration(classes = BootstrapApplication.class)
@Slf4j
public class ControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemcachedHealthIndicator memcachedHealthIndicator;

	@MockBean
	private TestSearchService testSearchService;
	
	@MockBean
	private TestRepository testRepository;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetDetailsUserName() throws Exception {

		TestEntity resp = new TestEntity();
		ProjectDetails details1 = new ProjectDetails("123", "Test1", "http://test1.com", "Test1 Project", "5");
		ProjectDetails details2 = new ProjectDetails("456", "Test2", "http://test2.com", "Test2 Project", "10");
		List<ProjectDetails> detailsList = new ArrayList<ProjectDetails>();
		detailsList.add(details1);
		detailsList.add(details2);
		resp.set_id("123456");
		resp.setProjectDetails(detailsList);
		resp.setUserName("Manikandan");

		when(testSearchService.searchByUserName("Manikandan")).thenReturn(resp);

		MvcResult result = mockMvc
				.perform(
						MockMvcRequestBuilders.get("/projects/{userName}", "Manikandan").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();

	}

	
	@Test
	public void testGetProjectDetails() throws Exception {

		ProjectDetails details1 = new ProjectDetails("123", "Test1", "http://test1.com", "Test1 Project", "5");

		when(testSearchService.searchByProjectId("123", "Manikandan")).thenReturn(details1);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/{userName}/project-id", "Manikandan", "123")
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn();

	}

	
}