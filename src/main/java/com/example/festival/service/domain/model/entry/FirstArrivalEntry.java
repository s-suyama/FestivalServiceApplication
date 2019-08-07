package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.Entity;
import com.example.festival.service.domain.model.event.EventCode;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.type.Amount;
import com.example.festival.service.domain.type.NumberOfPeople;

import java.time.LocalDate;

/**
 * 先着順エントリ枠.
 */
public class FirstArrivalEntry extends Entry implements Entity {

  private FirstArrivalEntry() {
    super();
  }

  /**
   * Constructor.
   * @param festivalId 大会番号
   * @param entryId エントリ枠番号
   * @param entryName エントリ枠名
   * @param entryDescription エントリ枠説明
   * @param eventCode 種目コード
   * @param capacity 定員
   * @param participationFees 参加費用
   * @param applicationNumbers 参加申込数
   * @param applicationStartDate 募集開始年月日
   * @param applicationEndDate 募集終了年月日
   * @param entryStatus エントリ枠状態
   */
  public FirstArrivalEntry(
      FestivalId festivalId,
      EntryId entryId,
      String entryName,
      String entryDescription,
      EventCode eventCode,
      NumberOfPeople capacity,
      Amount participationFees,
      NumberOfPeople applicationNumbers,
      LocalDate applicationStartDate,
      LocalDate applicationEndDate,
      EntryStatus entryStatus) {

    super(
        festivalId,
        entryId,
        entryName,
        entryDescription,
        eventCode,
        capacity,
        participationFees,
        applicationNumbers,
        FirstArrivalLotteryType.firstArrival,
        applicationStartDate,
        applicationEndDate,
        entryStatus
    );
  }

  @Override
  public boolean isLotteryEntry() {
    return false;
  }

  /**
   * 参加申込人数をインクリメントする. 参加申込人数が定員に達した場合は、参加者確定に変更する.
   */
  @Override
  public void incrementApplicationNumbers() {
    applicationNumbers = applicationNumbers.increment();

    if (capacity.same(applicationNumbers)) {
      entryStatus = EntryStatus.participantConfirmation;
    }
  }
}
