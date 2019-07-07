package com.example.festival.service.domain.model.member;

import com.example.festival.service.domain.ValueObject;

/**
 * 会員番号.
 */
public class MemberId implements ValueObject {

  private long value;

  private MemberId() {
  }

  public MemberId(long value) {
    this.value = value;
  }

  public long value() {
    return value;
  }
}
