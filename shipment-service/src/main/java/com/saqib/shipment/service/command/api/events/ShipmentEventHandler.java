package com.saqib.shipment.service.command.api.events;

import com.saqib.commonservice.events.OrderShippedEvent;
import com.saqib.commonservice.events.ShipmentCancelledEvent;
import com.saqib.shipment.service.command.api.data.Shipment;
import com.saqib.shipment.service.command.api.data.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentEventHandler {
  private final ShipmentRepository shipmentRepository;

  @EventHandler
  public void on(OrderShippedEvent event) {
    var shipment = new Shipment(event.shipmentId(), event.shipmentStatus(), event.orderId());
    shipmentRepository.save(shipment);
  }

  @EventHandler
  public void on(ShipmentCancelledEvent event) {
    shipmentRepository.findById(event.shipmentId()).ifPresent(shipment -> {
      shipment.setShipmentStatus(event.shipmentStatus());
      shipmentRepository.save(shipment);
    });
  }
}
