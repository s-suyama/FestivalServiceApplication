package com.example.festival.service.domain.model.lotteryentryresult;

import com.example.festival.service.domain.model.entry.LotteryEntry;
import com.example.festival.service.domain.model.member.MemberId;

public class LotteryEntryResult {

  LotteryEntry lotteryEntry;

  MemberId memberId;

  LotteryResult lotteryResult;

  private LotteryEntryResult() {
  }

  /**
   * All argument constructor.
   */
  public LotteryEntryResult(
      LotteryEntry lotteryEntry,
      MemberId memberId,
      LotteryResult lotteryResult) {

    this.lotteryEntry = lotteryEntry;
    this.memberId = memberId;
    this.lotteryResult = lotteryResult;
  }
}
