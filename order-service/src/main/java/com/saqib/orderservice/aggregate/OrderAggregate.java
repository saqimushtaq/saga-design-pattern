package com.saqib.orderservice.aggregate;

import com.saqib.commonservice.commands.CancelOrderCommand;
import com.saqib.commonservice.commands.OrderCompletedCommand;
import com.saqib.commonservice.events.CancelOrderEvent;
import com.saqib.commonservice.events.OrderCompletedEvent;
import com.saqib.orderservice.command.api.command.CreateOrderCommand;
import com.saqib.orderservice.events.OrderCreatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
@NoArgsConstructor
public class OrderAggregate {
  @AggregateIdentifier
  String orderId;
  String userId;
  String addressId;
  int quantity;
  String orderStatus;

  @CommandHandler
  public OrderAggregate(CreateOrderCommand command) {
    // all validations here
    var event = new OrderCreatedEvent(command.orderId(),
      command.productId(), command.userId(), command.addressId(), command.quantity(),
      command.orderStatus());
    AggregateLifecycle.apply(event);

  }

  @CommandHandler
  public void handle(CancelOrderCommand command) {
    var event = new CancelOrderEvent(command.orderId(), command.orderStatus());
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void handle(CancelOrderEvent event) {
    this.orderId = event.orderId();
    this.orderStatus = event.orderStatus();
  }

  @EventSourcingHandler
  public void on(OrderCreatedEvent event) {
    this.orderId = event.orderId();
    this.userId = event.userId();
    this.addressId = event.addressId();
    this.quantity = event.quantity();
    this.orderStatus = event.orderStatus();
  }

  @CommandHandler
  public void handle(OrderCompletedCommand command) {
    var event = new OrderCompletedEvent(command.orderId(), command.orderStatus());
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(OrderCompletedEvent event) {
    this.orderStatus = event.orderStatus();
  }
}
