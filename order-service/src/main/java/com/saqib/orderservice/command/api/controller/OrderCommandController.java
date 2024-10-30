package com.saqib.orderservice.command.api.controller;

import com.saqib.orderservice.command.api.command.CreateOrderCommand;
import com.saqib.orderservice.command.api.models.OrderRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderCommandController {
  private final transient CommandGateway commandGateway;
  @PostMapping
  public String createOrder(@RequestBody OrderRestModel request){
    var orderId = UUID.randomUUID().toString();
    var command = new CreateOrderCommand(orderId, request.productId(), request.userId(), request.addressId(), request.quantity(), "CREATED");
    commandGateway.sendAndWait(command);
    return "Order created";
  }
}
