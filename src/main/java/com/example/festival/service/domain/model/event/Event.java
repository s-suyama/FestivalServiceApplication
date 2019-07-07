package com.example.festival.service.domain.model.event;

/**
 * 種目 Entity.
 */
public class Event {

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
