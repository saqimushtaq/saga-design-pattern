package com.saqib.commonservice.events;

public record ShipmentCancelledEvent(
  String shipmentId,
  String orderId,
  String shipmentStatus
) {
}
