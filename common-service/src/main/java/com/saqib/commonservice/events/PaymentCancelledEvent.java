package com.saqib.commonservice.events;

public record PaymentCancelledEvent(
  String paymentId,
  String orderId,
  String paymentStatus
) {
}
