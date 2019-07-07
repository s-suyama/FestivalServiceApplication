package com.example.festival.service.domain.model.event;

import com.example.festival.service.domain.ValueObject;

/**
 * 種目コード.
 */
public class EventCode implements ValueObject {

  private long value;

  public EventCode(long value) {
    this.value = value;
  }

  private EventCode() {
  }

  public long value() {
    return value;
  }
}
