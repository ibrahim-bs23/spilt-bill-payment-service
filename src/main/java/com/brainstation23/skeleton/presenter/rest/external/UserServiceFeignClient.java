package com.brainstation23.skeleton.presenter.rest.external;

import com.brainstation23.skeleton.core.domain.model.ApiResponse;
import com.brainstation23.skeleton.core.domain.requests.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(name = "user-service", url = "${services.user.base-url}", path = "/api/v1/user", contextId = "user-service")
public interface UserServiceFeignClient {
    @PostMapping("/payment/initiate")
    ApiResponse<?> initiatePayment(@RequestBody PaymentRequest paymentRequest);
}

