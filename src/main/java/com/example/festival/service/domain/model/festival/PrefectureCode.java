package com.example.festival.service.domain.model.festival;

import com.example.festival.service.domain.ValueObject;

/**
 * 都道府県コード.
 */
public class PrefectureCode implements ValueObject {

  int value;

  public PrefectureCode(int value) {
    this.value = value;
  }

  private PrefectureCode() {
  }

  public int value() {
    return value;
  }
}
