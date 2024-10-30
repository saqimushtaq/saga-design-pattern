package com.saqib.commonservice.events;

public record OrderCompletedEvent(
  String orderId,
  String orderStatus
) {
}
