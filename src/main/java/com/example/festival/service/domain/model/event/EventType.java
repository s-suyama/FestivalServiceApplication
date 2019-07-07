package com.example.festival.service.domain.model.event;

/**
 * 種目分類.
 */
public class EventType {

  private EventTypeCode eventTypeCode;

  private String eventTypeName;

  /**
   * All argument constructor.
   */
  public EventType(
      EventTypeCode eventTypeCode,
      String eventTypeName) {

    this.eventTypeCode = eventTypeCode;
    this.eventTypeName = eventTypeName;
  }

  private EventType() {
  }

  public EventTypeCode eventTypeCode() {
    return eventTypeCode;
  }

  public String eventTypeName() {
    return eventTypeName;
  }
}
