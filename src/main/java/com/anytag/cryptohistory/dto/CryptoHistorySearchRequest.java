package com.anytag.cryptohistory.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CryptoHistorySearchRequest {
    @NonNull
    private String startTime;
    @NonNull
    private String endTime;
}
