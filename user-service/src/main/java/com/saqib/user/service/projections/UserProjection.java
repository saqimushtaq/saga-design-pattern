package com.saqib.user.service.projections;

import com.saqib.commonservice.models.CardDetail;
import com.saqib.commonservice.models.User;
import com.saqib.commonservice.queries.GetUserPaymentDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserProjection {

  @QueryHandler
  public User getUserPaymentDetail(GetUserPaymentDetailQuery query){
    return new User(UUID.randomUUID().toString(), "Saqib", "Mushtaq",
      new CardDetail("Saqib Mushtaq", "123123", 2026, 12, 123));
  }
}
