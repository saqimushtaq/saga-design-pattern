package com.saqib.commonservice.commands;

import com.saqib.commonservice.models.CardDetail;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ValidatePaymentCommand(
  @TargetAggregateIdentifier
  String paymentId,
  String orderId,
  CardDetail cardDetail
) {
}
