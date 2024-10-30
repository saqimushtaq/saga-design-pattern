package com.saqib.commonservice.events;

public record CancelOrderEvent(
  String orderId,
  String orderStatus
) {
}
