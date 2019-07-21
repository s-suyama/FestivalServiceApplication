package com.example.festival.service.domain.model.event;

import com.example.festival.service.domain.ValueObject;

/**
 * 種目コード.
 */
public class EventCode implements ValueObject {

  private Integer value;

  public EventCode(Integer value) {
    this.value = value;
  }

  private EventCode() {
  }

  public Integer value() {
    return value;
  }
}
