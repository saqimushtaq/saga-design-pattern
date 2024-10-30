package com.saqib.orderservice.events;

public record OrderCreatedEvent(
  String orderId,
  String productId,
  String userId,
  String addressId,
  int quantity,
  String orderStatus) {
}
