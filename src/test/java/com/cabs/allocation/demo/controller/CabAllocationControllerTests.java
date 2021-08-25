package com.cabs.allocation.demo.controller;

import com.cabs.allocation.demo.CabAllocationApplication;
import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.Location;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.types.CabType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { CabAllocationApplication.class })
//@WebAppConfiguration
public class CabAllocationControllerTests {

//    @Autowired
    WebApplicationContext webApplicationContext;

    ObjectMapper objectMapper;
    MockMvc mockMvc;

//    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        placeCabs();
    }

    private void placeCabs() throws Exception {
        Location cab1 = new Location(19.160242, 72.998192);
        mockMvc.perform(MockMvcRequestBuilders.post("/allocation-service/v1.0/cabs")
                .content(objectMapper.writeValueAsString(cab1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Location cab2 = new Location(19.160769, 72.989302);
        mockMvc.perform(MockMvcRequestBuilders.post("/allocation-service/v1.0/cabs")
                    .content(objectMapper.writeValueAsString(cab1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Location cab3 = new Location(19.148290,72.992720);
        mockMvc.perform(MockMvcRequestBuilders.post("/allocation-service/v1.0/cabs")
                    .content(objectMapper.writeValueAsString(cab1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Location cab4 = new Location(18.991904, 73.120042);
        mockMvc.perform(MockMvcRequestBuilders.post("/allocation-service/v1.0/cabs")
                    .content(objectMapper.writeValueAsString(cab1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

//    @Test
    public void testOnlyNearByLocationsPicked() throws Exception {

        SearchOptions options = new SearchOptions();
        options.setLocation(new Location(19.160438, 72.994834));
        options.setCabTypes(Arrays.asList(CabType.NORMAL));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/allocation-service/v1.0/find-cabs")
                .content(objectMapper.writeValueAsString(options)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<CabView> participantJsonList = objectMapper.readValue(responseBody, new TypeReference<List<CabView>>(){});
        Assert.isTrue(participantJsonList.size() == 3, "Response not as expected");
    }
}
