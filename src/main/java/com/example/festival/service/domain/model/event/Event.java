package com.example.festival.service.domain.model.event;

import com.example.festival.service.domain.Entity;

/**
 * 種目.
 */
public class Event implements Entity {

  EventCode eventCode;

  String eventName;

  EventType eventType;

  /**
   * All argument constructor.
   *
   */
  public Event(
      EventCode eventCode,
      String eventName,
      EventType eventType) {

    this.eventCode = eventCode;
    this.eventName = eventName;
    this.eventType = eventType;
  }

  private Event() {
  }
}
