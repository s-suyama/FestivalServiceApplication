package com.example.festival.service.domain.model.event;

import com.example.festival.service.domain.ValueObject;

/**
 * 種目分類コード.
 */
public class EventTypeCode implements ValueObject {

  private String value;

  public EventTypeCode(String value) {
    this.value = value;
  }

  private EventTypeCode() {
  }

  public String value() {
    return value;
  }
}
