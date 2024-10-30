package com.saqib.orderservice.command.api.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateOrderCommand(
  @TargetAggregateIdentifier
  String orderId,
  String productId,
  String userId,
  String addressId,
  int quantity,
  String orderStatus
) {
}
