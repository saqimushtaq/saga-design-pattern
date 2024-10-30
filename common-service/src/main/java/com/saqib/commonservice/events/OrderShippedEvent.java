package com.saqib.commonservice.events;

public record OrderShippedEvent(
  String shipmentId,
  String orderId,
  String shipmentStatus
) {
}
