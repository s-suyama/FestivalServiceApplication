package com.example.festival.service.domain.model.lotteryentryresult;

import java.util.List;

public class LotteryEntryResults {

  private List<LotteryEntryResult> list;

  public LotteryEntryResults(List<LotteryEntryResult> list) {
    this.list = list;
  }

  /**
   * 当選しているかどうかを返す.当選している場合、trueを返す.
   */
  public boolean winning() {

    for (LotteryEntryResult result : list) {
      if (result.lotteryResult == LotteryResult.winning) {
        return true;
      }
    }

    return false;
  }
}
