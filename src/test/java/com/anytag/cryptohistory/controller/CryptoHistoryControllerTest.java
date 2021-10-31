package com.anytag.cryptohistory.controller;

import com.anytag.cryptohistory.dto.CryptoHistoryRequest;
import com.anytag.cryptohistory.dto.CryptoHistoryResponse;
import com.anytag.cryptohistory.dto.CryptoHistorySearchRequest;
import com.anytag.cryptohistory.entity.CryptoHistory;
import com.anytag.cryptohistory.repository.CryptoHistoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CryptoHistoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CryptoHistoryRepository cryptoHistoryRepository;

    private String baseUrl = "/api";
    private String createCryptoHistoryUrl = baseUrl + "/crypto-history";
    private String searchCryptoHistoryUrl = baseUrl + "/search-crypto-history";

    @BeforeEach
    void setup() {
        cryptoHistoryRepository.deleteAll();
    }

    @Test
    public void createCrypoHistorySuccess() throws Exception {
        // Given
        String testDatetime = "2021-10-05T15:00:00+00:00";
        BigDecimal testAmount = BigDecimal.valueOf(9999);
        CryptoHistoryRequest request = new CryptoHistoryRequest(testAmount, testDatetime);

        // When
        mockMvc.perform(
            MockMvcRequestBuilders.post(createCryptoHistoryUrl)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        CryptoHistory cryptoHistory = cryptoHistoryRepository.findByAmountAndDatetime(
            BigDecimal.valueOf(9999),
            Instant.parse(testDatetime)
        );
        System.out.println("CryptoHistory result: " + cryptoHistory.toString());
        assertThat(cryptoHistory.getAmount().equals(testAmount));
        assertThat(cryptoHistory.getDatetime().equals(Instant.parse(testDatetime)));
    }

    @Test
    public void createCrypoHistoryFailed() throws Exception {
        // Given
        String testDatetime = "2021-10-05T15:00:00+00:00";
        BigDecimal testAmount = BigDecimal.valueOf(-1);
        CryptoHistoryRequest request = new CryptoHistoryRequest(testAmount, testDatetime);

        // When and Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(createCryptoHistoryUrl)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void filterCrypoHistorySuccess() throws Exception {
        // Given
        List<CryptoHistory> cryptoHistories = new ArrayList<>();
        CryptoHistory cryptoHistoryFirst = new CryptoHistory();
        cryptoHistoryFirst.setAmount(BigDecimal.valueOf(1000));
        cryptoHistoryFirst.setDatetime(Instant.parse("2019-10-05T13:00:00+00:00"));
        cryptoHistories.add(cryptoHistoryFirst);
        CryptoHistory cryptoHistorySecond = new CryptoHistory();
        cryptoHistorySecond.setAmount(BigDecimal.valueOf(2000));
        cryptoHistorySecond.setDatetime(Instant.parse("2019-10-05T15:00:00+00:00"));
        cryptoHistories.add(cryptoHistorySecond);
        cryptoHistoryRepository.saveAll(cryptoHistories);
        String testStartTime = "2019-10-05T13:00:00+00:00";
        String testEndTime = "2019-10-05T14:00:00+00:00";
        CryptoHistorySearchRequest request = new CryptoHistorySearchRequest(
            testStartTime,
            testEndTime
        );

        // When
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(searchCryptoHistoryUrl)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk()
            ).andReturn();

        // Then
        List<CryptoHistoryResponse> cryptoHistoryResponses = objectMapper.readValue(
            result.getResponse().getContentAsString(), new TypeReference<List<CryptoHistoryResponse>>() {
            }
        );
        assertThat(cryptoHistoryResponses.get(0).getAmount().equals(BigDecimal.valueOf(1000)));
        assertThat(cryptoHistoryResponses.get(0).getDateTime().equals("2019-10-05T13:00:00+00:00"));
    }
}
