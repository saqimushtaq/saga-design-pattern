package com.saqib.shipment.service.command.api.aggregate;

import com.saqib.commonservice.commands.ShipOrderCommand;
import com.saqib.commonservice.commands.ShipmentCancelCommand;
import com.saqib.commonservice.events.OrderShippedEvent;
import com.saqib.commonservice.events.ShipmentCancelledEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@Slf4j
public class ShipmentAggregate {
  @AggregateIdentifier
  private String shipmentId;
  private String orderId;
  private String shipmentStatus;

  @CommandHandler
  public ShipmentAggregate(ShipOrderCommand command) {
    // validate command
    //publish order ship event
    log.info("Handling shipment command: {}", command);

    var event = new OrderShippedEvent(command.shipmentId(), command.orderId(), "COMPLETED");
    AggregateLifecycle.apply(event);
  }

  @CommandHandler
  public void handle(ShipmentCancelCommand command) {
    // validate command
    log.info("Handling shipment cancel command: {}", command);
    var event = new ShipmentCancelledEvent(command.shipmentId(), command.orderId(), "CANCELLED");
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(OrderShippedEvent event) {
    this.shipmentId = event.shipmentId();
    this.orderId = event.orderId();
    this.shipmentStatus = event.shipmentStatus();
  }

  @EventSourcingHandler
  public void on(ShipmentCancelledEvent event) {
    this.shipmentStatus = event.shipmentStatus();
  }
}
