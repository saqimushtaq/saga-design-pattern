package com.saqib.commonservice.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ShipmentCancelCommand(
  @TargetAggregateIdentifier
  String shipmentId,
  String orderId
) {
}
