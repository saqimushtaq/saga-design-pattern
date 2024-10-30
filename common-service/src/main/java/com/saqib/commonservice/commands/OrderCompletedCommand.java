package com.saqib.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record OrderCompletedCommand(
  @TargetAggregateIdentifier
  String orderId,
  String orderStatus
) {
}
