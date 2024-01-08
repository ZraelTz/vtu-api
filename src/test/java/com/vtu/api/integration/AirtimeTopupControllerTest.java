package com.vtu.api.integration;

import com.github.javafaker.Faker;
import com.vtu.api.dto.request.AirtimeTopupRequest;
import com.vtu.api.model.entity.enums.NetworkProvider;
import com.vtu.api.service.AirtimeTopupService;
import com.vtu.api.util.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.vtu.api.model.constants.ConstraintConstant.MAXIMUM_AIRTIME_TOPUP;
import static com.vtu.api.model.constants.ConstraintConstant.MINIMUM_AIRTIME_TOPUP;
import static com.vtu.api.model.constants.TestConstant.MTN_PHONE_NO;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AirtimeTopupControllerTest {

    @Autowired
    AirtimeTopupService airtimeTopupService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("zraelwalker@gmail.com")
    public void testAirtimeTopup() throws Exception {
        AirtimeTopupRequest airtimeTopupRequest = AirtimeTopupRequest.builder()
                .amount(Faker.instance().number().numberBetween(MINIMUM_AIRTIME_TOPUP, MAXIMUM_AIRTIME_TOPUP))
                .phoneNumber(MTN_PHONE_NO)
                .networkProvider(NetworkProvider.MTN.name())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/airtime")
                        .contentType("application/json")
                        .content(TestUtils.convertToJsonString(airtimeTopupRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
