package com.saqib.orderservice.command.api.models;

public record OrderRestModel(
  String productId,
  String userId,
  String addressId,
  int quantity
) {
}
