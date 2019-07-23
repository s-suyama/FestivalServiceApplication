package com.example.festival.service.domain.type;

import com.example.festival.service.domain.ValueObject;

/**
 * 電話番号.
 */
public class PhoneNumber implements ValueObject {

  String value;

  public PhoneNumber(String value) {
    this.value = value;
  }

  private PhoneNumber() {
  }

  public String value() {
    return value;
  }
}
