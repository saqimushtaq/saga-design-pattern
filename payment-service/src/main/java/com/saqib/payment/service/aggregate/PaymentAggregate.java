package com.saqib.payment.service.aggregate;

import com.saqib.commonservice.commands.CancelPaymentCommand;
import com.saqib.commonservice.commands.ValidatePaymentCommand;
import com.saqib.commonservice.events.PaymentCancelledEvent;
import com.saqib.commonservice.events.PaymentProcessedEvent;
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
public class PaymentAggregate {
  @AggregateIdentifier
  private String paymentId;
  private String orderId;
  private String paymentStatus;

  @CommandHandler
  public PaymentAggregate(ValidatePaymentCommand command) {
    log.info("[PaymentAggregate] payment command: {}", command);
    // validate payment
    // publish payment process event
    var paymentProcessedEvent = new PaymentProcessedEvent(command.paymentId(), command.orderId());

    AggregateLifecycle.apply(paymentProcessedEvent);
    log.info("PaymentProcessedEvent Applied");
  }

  @CommandHandler
  public void handle(CancelPaymentCommand command) {
    var event = new PaymentCancelledEvent(command.paymentId(), command.orderId(), "CANCELLED");
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(PaymentProcessedEvent event) {
    log.info("[PaymentAggregate] payment processed event: {}", event);
    this.paymentId = event.paymentId();
    this.orderId = event.orderId();
    this.paymentStatus = "COMPLETED";
  }

  @EventSourcingHandler
  public void on(PaymentCancelledEvent event) {
    log.info("[PaymentAggregate] payment cancelled event: {}", event);
    this.paymentId = event.paymentId();
    this.orderId = event.orderId();
    this.paymentStatus = event.paymentStatus();
  }
}
