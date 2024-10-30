package com.saqib.commonservice.models;

public record User(
  String userId,
  String firstName,
  String lastName,
  CardDetail cardDetail
) {
}
