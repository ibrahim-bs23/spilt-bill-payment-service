package com.brainstation23.skeleton.core.service.impl;

import com.brainstation23.skeleton.core.domain.enums.ResponseMessage;
import com.brainstation23.skeleton.core.domain.exceptions.InvalidRequestDataException;
import com.brainstation23.skeleton.core.domain.exceptions.RecordNotFoundException;
import com.brainstation23.skeleton.core.domain.requests.PaymentRequest;
import com.brainstation23.skeleton.core.service.BaseService;
import com.brainstation23.skeleton.data.entity.TransactionHistory;
import com.brainstation23.skeleton.data.repository.TransactionHistoryRepository;
import com.brainstation23.skeleton.presenter.service.nonTransactional.NonTransactionalIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class TransactionHistoryService extends BaseService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final NonTransactionalIntegrationService nonTransactionalIntegrationService;


    public void initiateTransaction(PaymentRequest paymentRequest) {

        validatePaymentRequest(paymentRequest);
        TransactionHistory transactionHistory = createTransactionHistory(paymentRequest);
        paymentRequest.setTransactionId(transactionHistory.getTransactionId());
        transactionHistoryRepository.save(transactionHistory);
        processTransaction(paymentRequest);
    }

    public void processTransaction(PaymentRequest paymentRequest) {
        nonTransactionalIntegrationService.initiatePaymentProcess(paymentRequest);
    }

    private TransactionHistory createTransactionHistory(PaymentRequest paymentRequest) {
        return TransactionHistory.builder()
                .senderUsername(paymentRequest.getSenderUsername())
                .receiverUsername(paymentRequest.getReceiverUsername())
                .transactionId(generateTransactionId())
                .createdAt(new Date())
                .amount(paymentRequest.getAmount())
                .status("INITIATED")
                .transferType(paymentRequest.getTransactionType())
                .eventId(paymentRequest.getEventId())
                .build();
    }

    private void validatePaymentRequest(PaymentRequest paymentRequest) {
        if(Objects.isNull(paymentRequest)) {
            throw new InvalidRequestDataException(ResponseMessage.INVALID_REQUEST_DATA);
        }
    }
    public static String generateTransactionId() {
        // Generate a random UUID
        String uuid = UUID.randomUUID().toString();

        // Get the current timestamp in milliseconds
        long timestamp = System.currentTimeMillis();

        // Combine the timestamp and UUID to form a unique transaction ID
        return timestamp + "-" + uuid;
    }

    public void updateTransactionStatus(String transactionId) {
        TransactionHistory transactionHistory = transactionHistoryRepository.findByTransactionId(transactionId).orElseThrow(
                () -> new RecordNotFoundException(ResponseMessage.RECORD_NOT_FOUND)
        );
        transactionHistory.setStatus("COMPLETED");
        transactionHistory.setUpdatedAt(new Date());
        transactionHistoryRepository.save(transactionHistory);
    }
}
