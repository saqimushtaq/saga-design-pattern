package com.saqib.commonservice.events;

public record PaymentProcessedEvent(
  String paymentId,
  String orderId
) {
}
