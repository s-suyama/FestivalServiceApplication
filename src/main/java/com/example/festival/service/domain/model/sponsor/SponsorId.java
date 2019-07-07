package com.example.festival.service.domain.model.sponsor;

import com.example.festival.service.domain.ValueObject;

/**
 * 主催者番号.
 */
public class SponsorId implements ValueObject {

  long value;

  public SponsorId(long value) {
    this.value = value;
  }

  private SponsorId() {
  }

  public long value() {
    return value;
  }
}
