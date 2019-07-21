package com.example.festival.service.domain.model.sponsor;

import com.example.festival.service.domain.ValueObject;

/**
 * 主催者番号.
 */
public class SponsorId implements ValueObject {

  Integer value;

  public SponsorId(Integer value) {
    this.value = value;
  }

  private SponsorId() {
  }

  public Integer value() {
    return value;
  }
}
