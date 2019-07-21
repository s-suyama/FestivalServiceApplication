package com.example.festival.service.domain.model.member;

import com.example.festival.service.domain.ValueObject;

/**
 * 会員番号.
 */
public class MemberId implements ValueObject {

  private Integer value;

  private MemberId() {
  }

  public MemberId(Integer value) {
    this.value = value;
  }

  public Integer value() {
    return value;
  }
}
