package com.anytag.cryptohistory.service;

import com.anytag.cryptohistory.dto.CryptoHistoryRequest;
import com.anytag.cryptohistory.entity.CryptoHistory;
import com.anytag.cryptohistory.repository.CryptoHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class CryptoHistoryService {
    @Autowired
    private CryptoHistoryRepository cryptoHistoryRepository;

    public List<CryptoHistory> filterCryptoHistoryByDatetime(String startTime, String endTime) throws DateTimeParseException {
        Instant startTimeInstant = Instant.parse(startTime);
        Instant endTimeInstant = Instant.parse(endTime);
        return cryptoHistoryRepository.findAllByDatetimeBetween(startTimeInstant, endTimeInstant);
    }

    public CryptoHistory createCryptoHistoryPrice(CryptoHistoryRequest request) throws DateTimeParseException {
        CryptoHistory cryptoHistory = new CryptoHistory();
        cryptoHistory.setAmount(request.getAmount().setScale(6));
        cryptoHistory.setDatetime(Instant.parse(request.getDateTime()));
        return cryptoHistoryRepository.save(cryptoHistory);
    }
}
