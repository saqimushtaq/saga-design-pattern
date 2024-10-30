package com.saqib.orderservice.saga;

import com.saqib.commonservice.commands.*;
import com.saqib.commonservice.events.*;
import com.saqib.commonservice.models.User;
import com.saqib.commonservice.queries.GetUserPaymentDetailQuery;
import com.saqib.orderservice.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient QueryGateway queryGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(OrderCreatedEvent event) {
    log.info("saga OrderCreatedEvent: {}", event);
    try {
      var paymentDetailQuery = new GetUserPaymentDetailQuery(event.userId());
      var user = queryGateway.query(paymentDetailQuery, ResponseTypes.instanceOf(User.class)).join();
      var validatePayment = new ValidatePaymentCommand(UUID.randomUUID().toString(),
        event.orderId(), user.cardDetail());
      commandGateway.sendAndWait(validatePayment);
      log.info("saga OrderCreatedEvent processed: {}", event);
    } catch (Exception e) {
//      start compensation transaction
      log.error("Saga exception OrderCreatedEvent exception: {}", e.getMessage());
      cancelOrderCommand(event.orderId());
    }
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(PaymentProcessedEvent event) {
    log.info("saga PaymentProcessedEvent: {}", event);
    try {
      var shipmentCommand = new ShipOrderCommand(UUID.randomUUID().toString(), event.orderId());
      if(true){
        throw new Exception();
      }
      commandGateway.sendAndWait(shipmentCommand);
    } catch (Exception e) {
      log.error("Error when processing payment {}", e.getMessage());
//      start compensation transaction
      var cancelledEvent = new CancelPaymentCommand(event.paymentId(), event.orderId(), "CANCELLED");
      commandGateway.sendAndWait(cancelledEvent);
    }
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(PaymentCancelledEvent event) {
    log.info("saga PaymentCancelledEvent: {}", event);

    cancelOrderCommand(event.orderId());

  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(ShipmentCancelledEvent event) {
    log.info("saga ShipmentCancelledEvent: {}", event);
    cancelOrderCommand(event.orderId());
  }

  private void cancelOrderCommand(String orderId) {
    var command = new CancelOrderCommand(orderId, "CANCELLED");
    commandGateway.sendAndWait(command);
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(CancelOrderEvent event) {
    log.info("saga CancelOrderEvent: {}", event);
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(OrderShippedEvent event) {
    log.info("OrderShippedEvent: {}", event);
    try {
      var command = new OrderCompletedCommand(event.orderId(), "APPROVED");
      commandGateway.sendAndWait(command);
    } catch (Exception e) {
      log.error("Error when processing shipment", e);
      var cancelledEvent = new ShipmentCancelledEvent(event.shipmentId(), event.orderId(), "CANCELLED");
      commandGateway.sendAndWait(cancelledEvent);
    }
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void on(OrderCompletedEvent event) {
    log.info("OrderCompletedEvent: {}", event);
  }
}
