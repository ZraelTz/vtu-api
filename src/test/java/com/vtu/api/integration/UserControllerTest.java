package com.vtu.api.integration;

import com.github.javafaker.Faker;
import com.vtu.api.dto.request.UserRegistrationRequest;
import com.vtu.api.service.AirtimeTopupService;
import com.vtu.api.util.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    AirtimeTopupService airtimeTopupService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterUser() throws Exception {
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setEmail(Faker.instance().internet().safeEmailAddress());
        registrationRequest.setConfirmPassword("$Test123");
        registrationRequest.setPassword("$Test123");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType("application/json")
                        .content(TestUtils.convertToJsonString(registrationRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
