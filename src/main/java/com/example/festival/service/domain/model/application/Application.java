package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.PointAmount;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 参加申込 Entity.
 */
public class Application {

  FestivalId festivalId;

  MemberId memberId;

  EntryId entryId;

  LocalDate applicationDate;

  LocalDate paymentDate;

  PointAmount usePoints;

  private Application() {
  }

  /**
   * エントリ申込のためのオブジェクトを生成して返す.
   */
  public static Application createEntityForEntry(
      FestivalId festivalId,
      MemberId memberId,
      EntryId entryId,
      LocalDate applicationDate) {

    Application result = new Application();
    result.festivalId = festivalId;
    result.memberId = memberId;
    result.entryId = entryId;
    result.applicationDate = applicationDate;
    result.paymentDate = null;
    result.usePoints = new PointAmount(BigDecimal.ZERO);

    return result;
  }

  public FestivalId festivalId() {
    return festivalId;
  }

  public MemberId memberId() {
    return memberId;
  }

  public EntryId entryId() {
    return entryId;
  }

  public LocalDate applicationDate() {
    return applicationDate;
  }

  public LocalDate paymentDate() {
    return paymentDate;
  }

  public PointAmount usePoints() {
    return usePoints;
  }
}
