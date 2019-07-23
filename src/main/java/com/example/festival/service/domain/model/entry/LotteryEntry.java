package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.model.event.EventCode;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.type.Amount;
import com.example.festival.service.domain.type.NumberOfPeople;

import java.time.LocalDate;

/**
 * 抽選エントリ枠 Entity.
 */
public class LotteryEntry extends Entry {

  LocalDate lotteryDate;

  EntryId followingEntryId;

  private LotteryEntry() {
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
   * @param lotteryDate 抽選年月日
   * @param followingEntryId 後続エントリ枠番号
   */
  public LotteryEntry(
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
      EntryStatus entryStatus,
      LocalDate lotteryDate,
      EntryId followingEntryId) {

    super(
        festivalId,
        entryId,
        entryName,
        entryDescription,
        eventCode,
        capacity,
        participationFees,
        applicationNumbers,
        FirstArrivalLotteryType.lottery,
        applicationStartDate,
        applicationEndDate,
        entryStatus);

    this.lotteryDate = lotteryDate;
    this.followingEntryId = followingEntryId;
  }

  @Override
  public boolean isLotteryEntry() {
    return true;
  }

  @Override
  public void incrementApplicationNumbers() {
    applicationNumbers = applicationNumbers.increment();
  }

  public EntryId followingEntryId() {
    return followingEntryId;
  }
}
