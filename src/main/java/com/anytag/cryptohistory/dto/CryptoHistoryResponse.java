package com.anytag.cryptohistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoHistoryResponse {
    private BigDecimal amount;
    private Instant dateTime;
}
