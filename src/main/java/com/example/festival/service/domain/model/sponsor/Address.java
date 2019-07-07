package com.example.festival.service.domain.model.sponsor;

import com.example.festival.service.domain.ValueObject;

/**
 * 住所.
 */
public class Address implements ValueObject {

  String value;

  public Address(String value) {
    this.value = value;
  }

  private Address() {
  }

  public String value() {
    return value;
  }
}
