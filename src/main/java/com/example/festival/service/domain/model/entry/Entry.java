package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.event.EventCode;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.type.Amount;
import com.example.festival.service.domain.type.NumberOfPeople;
import com.example.festival.service.support.BusinessErrorException;

/**
 * エントリ枠 Entity.
 */
public abstract class Entry {

  FestivalId festivalId;

  EntryId entryId;

  String entryName;

  String entryDescription;

  EventCode eventCode;

  NumberOfPeople capacity;

  Amount participationFees;

  NumberOfPeople applicationNumbers;

  FirstArrivalLotteryType firstArrivalLotteryType;

  ApplicationStartDate applicationStartDate;

  ApplicationEndDate applicationEndDate;

  EntryStatus entryStatus;

  protected Entry() {
  }

  /**
   * All argument constructor.
   */
  public Entry(
      FestivalId festivalId,
      EntryId entryId, String entryName,
      String entryDescription,
      EventCode eventCode,
      NumberOfPeople capacity,
      Amount participationFees,
      NumberOfPeople applicationNumbers,
      FirstArrivalLotteryType firstArrivalLotteryType,
      ApplicationStartDate applicationStartDate,
      ApplicationEndDate applicationEndDate,
      EntryStatus entryStatus) {

    this.festivalId = festivalId;
    this.entryId = entryId;
    this.entryName = entryName;
    this.entryDescription = entryDescription;
    this.eventCode = eventCode;
    this.capacity = capacity;
    this.participationFees = participationFees;
    this.applicationNumbers = applicationNumbers;
    this.firstArrivalLotteryType = firstArrivalLotteryType;
    this.applicationStartDate = applicationStartDate;
    this.applicationEndDate = applicationEndDate;
    this.entryStatus = entryStatus;
  }

  /**
   * 抽選のエントリ枠かどうかを返す.
   * @return 抽選のエントリ枠の場合、 true を返す.
   */
  public abstract boolean isLotteryEntry();

  /**
   * 参加申込人数をインクリメントする.
   */
  public abstract void incrementApplicationNumbers();

  /**
   * 申込内容にエラーがないかを検証し、エラーがある場合、業務例外を throw する.
   */
  public void validateAndThrowBusinessErrorIfHasErrorForApplication(Application application) {

    if (entryStatus != EntryStatus.recruiting) {
      throw new BusinessErrorException("指定した大会は現在募集を行っておりません");
    }

    if (application.applicationDate().value().compareTo(applicationStartDate.value()) < 0) {
      throw new BusinessErrorException("指定した大会はまだ募集を開始していません");
    }

    if (application.applicationDate().value().compareTo(applicationEndDate.value()) > 0) {
      throw new BusinessErrorException("指定した大会の募集期間を過ぎています");
    }
  }

  public FestivalId festivalId() {
    return festivalId;
  }

  public EntryId entryId() {
    return entryId;
  }

  public String entryName() {
    return entryName;
  }

  public String entryDescription() {
    return entryDescription;
  }

  public EventCode eventCode() {
    return eventCode;
  }

  public NumberOfPeople capacity() {
    return capacity;
  }

  public Amount participationFees() {
    return participationFees;
  }

  public NumberOfPeople applicationNumbers() {
    return applicationNumbers;
  }

  public FirstArrivalLotteryType firstArrivalLotteryType() {
    return firstArrivalLotteryType;
  }

  public ApplicationStartDate applicationStartDate() {
    return applicationStartDate;
  }

  public ApplicationEndDate applicationEndDate() {
    return applicationEndDate;
  }

  public EntryStatus entryStatus() {
    return entryStatus;
  }
}
