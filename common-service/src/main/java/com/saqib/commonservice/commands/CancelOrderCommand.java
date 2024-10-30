package com.saqib.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CancelOrderCommand(
  @TargetAggregateIdentifier
  String orderId,
  String orderStatus
) {
}
