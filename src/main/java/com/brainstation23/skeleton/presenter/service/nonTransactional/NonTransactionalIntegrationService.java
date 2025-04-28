package com.brainstation23.skeleton.presenter.service.nonTransactional;

import com.brainstation23.skeleton.core.domain.enums.ApiResponseCode;
import com.brainstation23.skeleton.core.domain.exceptions.InterServiceCommunicationException;
import com.brainstation23.skeleton.core.domain.model.ApiResponse;
import com.brainstation23.skeleton.core.domain.requests.PaymentRequest;
import com.brainstation23.skeleton.core.service.BaseService;
import com.brainstation23.skeleton.presenter.rest.external.UserServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NonTransactionalIntegrationService extends BaseService {

  private final UserServiceFeignClient userServiceFeignClient;

  public void initiatePaymentProcess(PaymentRequest paymentRequest) {

      ApiResponse<?> response = userServiceFeignClient.initiatePayment(paymentRequest);
      if (ApiResponseCode.isNotOperationSuccessful(response)
              || Objects.isNull(response.getData())) {
          logger.trace("Error processing payment: " + writeJsonString(request));
          throw new InterServiceCommunicationException(response.getResponseMessage());
      }
  }
}
