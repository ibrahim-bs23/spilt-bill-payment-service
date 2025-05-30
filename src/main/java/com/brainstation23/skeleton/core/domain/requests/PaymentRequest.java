package com.brainstation23.skeleton.core.domain.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PaymentRequest {
    @NotNull
    private String eventId;
    @NotNull
    private String senderUsername;
    @NotNull
    private String receiverUsername;
    @NotNull
    private Double amount;
    @NotNull
    private String transactionType;
    @NotNull
    private String currency;

    private String TransactionId;  //dont need to send this
}
