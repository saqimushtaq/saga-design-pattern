package com.saqib.orderservice.events;

import com.saqib.commonservice.events.CancelOrderEvent;
import com.saqib.commonservice.events.OrderCompletedEvent;
import com.saqib.orderservice.command.data.Order;
import com.saqib.orderservice.command.data.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventHandler {
  private final OrderRepository orderRepository;

  @EventHandler
  public void on(OrderCreatedEvent event) {
    var order = new Order(event.orderId(), event.productId(), event.addressId(), event.orderStatus(), event.quantity());
    orderRepository.save(order);
  }

  @EventHandler
  public void on(OrderCompletedEvent event) {
    orderRepository.findByOrderId(event.orderId()).ifPresent(order -> {
      order.setOrderStatus(event.orderStatus());
      orderRepository.save(order);
    });
  }

  @EventHandler
  public void on(CancelOrderEvent event){
    orderRepository.findByOrderId(event.orderId()).ifPresent(order -> {
      order.setOrderStatus(event.orderStatus());
      orderRepository.save(order);
    });
  }
}
