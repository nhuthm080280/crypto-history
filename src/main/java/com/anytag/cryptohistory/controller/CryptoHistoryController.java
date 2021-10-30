package com.anytag.cryptohistory.controller;

import com.anytag.cryptohistory.dto.CryptoHistoryRequest;
import com.anytag.cryptohistory.entity.CryptoHistory;
import com.anytag.cryptohistory.dto.CryptoHistorySearchRequest;
import com.anytag.cryptohistory.dto.CryptoHistoryResponse;
import com.anytag.cryptohistory.service.CryptoHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CryptoHistoryController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CryptoHistoryService cryptoHistoryService;

    @PostMapping("/search-crypto-history")
    public ResponseEntity<List<CryptoHistoryResponse>> getAllTutorials(@RequestBody CryptoHistorySearchRequest request) {
        logger.info("Start getting crypto history info");
        List<CryptoHistory> cryptoHistories = cryptoHistoryService.filterCryptoHistoryByDatetime(
            request.getStartTime() , request.getEndTime()
        );
        if (cryptoHistories.size() == 0 ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CryptoHistoryResponse> responses = new ArrayList<>();
        for (int i = 0; i < cryptoHistories.size(); i++) {
            CryptoHistoryResponse response = new CryptoHistoryResponse(
                cryptoHistories.get(i).getAmount(),
                cryptoHistories.get(i).getDatetime()
            );
            responses.add(response);
        }

        logger.info("The number crypto history info: {}", responses.size());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/crypto-history")
    public ResponseEntity<CryptoHistoryResponse> createCryptoHistory(@RequestBody CryptoHistoryRequest request) {
        CryptoHistory cryptoHistory = cryptoHistoryService.createCryptoHistoryPrice(request);
        CryptoHistoryResponse response = new CryptoHistoryResponse(
            cryptoHistory.getAmount().setScale(6), cryptoHistory.getDatetime()
        );
        logger.info("Stored new crypto history successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
