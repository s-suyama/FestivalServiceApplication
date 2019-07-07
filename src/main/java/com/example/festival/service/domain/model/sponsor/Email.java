package com.example.festival.service.domain.model.sponsor;

import com.example.festival.service.domain.ValueObject;

/**
 * メールアドレス.
 */
public class Email implements ValueObject {

  String value;

  public Email(String value) {
    this.value = value;
  }

  private Email() {
  }

  public String value() {
    return value;
  }
}
