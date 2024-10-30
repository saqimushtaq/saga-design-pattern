package com.saqib.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ShipOrderCommand(
  @TargetAggregateIdentifier
  String shipmentId,
  String orderId
) {
}
