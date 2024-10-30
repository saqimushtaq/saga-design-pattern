package com.saqib.payment.service.commands.api.events;

import com.saqib.commonservice.events.PaymentCancelledEvent;
import com.saqib.commonservice.events.PaymentProcessedEvent;
import com.saqib.payment.service.commands.api.data.Payment;
import com.saqib.payment.service.commands.api.data.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

  private final PaymentRepository paymentRepository;

  @EventHandler
  public void on(PaymentProcessedEvent event) {
    var payment = new Payment(event.paymentId(), event.orderId(), new Date(), "COMPLETED");
    paymentRepository.save(payment);
  }

  @EventHandler
  public void on(PaymentCancelledEvent event) {
    paymentRepository.findById(event.paymentId()).ifPresent(payment -> {
      payment.setPaymentStatus(event.paymentStatus());
      paymentRepository.save(payment);
    });
  }
}
