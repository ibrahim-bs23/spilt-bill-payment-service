package com.brainstation23.skeleton.presenter.rest.api;

import com.brainstation23.skeleton.common.utils.AppUtils;
import com.brainstation23.skeleton.common.utils.ResponseUtils;
import com.brainstation23.skeleton.core.domain.enums.ResponseMessage;
import com.brainstation23.skeleton.core.domain.model.ApiResponse;
import com.brainstation23.skeleton.core.domain.requests.PaymentRequest;
import com.brainstation23.skeleton.core.service.BaseService;
import com.brainstation23.skeleton.core.service.impl.TransactionHistoryService;
import com.brainstation23.skeleton.data.entity.TransactionHistory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppUtils.BASE_URL+"/process")
public class TransactionHistoryController extends BaseService {

    private final TransactionHistoryService transactionHistoryService;

    @PostMapping("/initiate")
    public ApiResponse<?> initiateTransaction(@Valid @RequestBody PaymentRequest paymentRequest) {
        transactionHistoryService.initiateTransaction(paymentRequest);
        return ResponseUtils.createSuccessResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL));
    }

    @PutMapping("/update-status/{transactionId}")
    public ApiResponse<?> updateTransactionStatus(@PathVariable String transactionId) {
        transactionHistoryService.updateTransactionStatus(transactionId);
        return ResponseUtils.createSuccessResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL));
    }

    @GetMapping("/transaction-history")
    public ApiResponse<Page<TransactionHistory>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseUtils.createSuccessResponseObject(getMessage(ResponseMessage.OPERATION_SUCCESSFUL),
                transactionHistoryService.getTransactionHistories(page, size));
    }



}
