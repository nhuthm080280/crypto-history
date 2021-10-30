package com.anytag.cryptohistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoHistoryRequest {
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String dateTime;
}
