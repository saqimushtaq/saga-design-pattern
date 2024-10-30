package com.saqib.commonservice.models;

public record CardDetail(
  String name,
  String cardNumber,
  int validUntilYear,
  int validUntilMonth,
  int cvv
) {
}
