package com.example.festival.service.domain.model.festival;

import com.example.festival.service.domain.Entity;
import com.example.festival.service.domain.model.sponsor.Sponsor;

import java.time.LocalDate;

/**
 * 大会.
 */
public class Festival implements Entity {

  FestivalId festivalId;

  String festivalName;

  Sponsor sponsor;

  LocalDate heldDate;

  PrefectureCode heldPlacePrefectureCode;

  /**
   * Constructor.
   */
  public Festival(
      FestivalId festivalId,
      String festivalName,
      Sponsor sponsor,
      LocalDate heldDate,
      PrefectureCode heldPlacePrefectureCode) {

    this.festivalId = festivalId;
    this.festivalName = festivalName;
    this.sponsor = sponsor;
    this.heldDate = heldDate;
    this.heldPlacePrefectureCode = heldPlacePrefectureCode;
  }

  private Festival() {
  }
}
