package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.Entity;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.PointAmount;
import java.time.LocalDate;

/**
 * 参加申込.
 */
public class Application implements Entity {

  FestivalId festivalId;

  MemberId memberId;

  EntryId entryId;

  LocalDate applicationDate;

  Payment payment;

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
    result.payment = Payment.createUnpaid();

    return result;
  }

  /**
   * 入金する.
   */
  public void pay(LocalDate paymentDate, PointAmount usePoints) {
    this.payment = new Payment(paymentDate, usePoints);
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

  public Payment payment() {
    return payment;
  }
}
