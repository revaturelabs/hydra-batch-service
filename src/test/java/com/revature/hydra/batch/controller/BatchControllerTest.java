package com.revature.hydra.batch.controller;

import com.revature.hydra.batch.BatchRepositoryServiceApplication;
import com.revature.hydra.batch.model.Batch;
import com.revature.hydra.batch.service.BatchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BatchRepositoryServiceApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class BatchControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BatchService batchService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void setTrainingService() {
    }

    @Test
    @WithMockUser
    public void findAllBatchesByTrainer() throws Exception {
        List<Batch> batches = Collections.singletonList(new Batch());
        Mockito.when(batchService.findAllBatches(anyInt())).thenReturn(batches);
        mockMvc.perform(MockMvcRequestBuilders.get("/trainer/batch/all")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
    }

    @Test
    public void createBatch() {
    }

    @Test
    public void updateBatch() {
    }

    @Test
    public void deleteBatch() {
    }

    @Test
    public void getAllCurrentBatches() {
    }

    @Test
    public void getAllBatches() {
    }

    @Test
    public void createWeek() {
    }

    @Test
    public void findCommonLocations() {
    }
}