package com.anytag.cryptohistory.repository;

import com.anytag.cryptohistory.entity.CryptoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Repository
public interface CryptoHistoryRepository extends JpaRepository<CryptoHistory, Long> {
    List<CryptoHistory> findAllByDatetimeBetween(Instant startTime, Instant endTime);

    CryptoHistory findByAmountAndDatetime(BigDecimal amount, Instant dateTime);
}
