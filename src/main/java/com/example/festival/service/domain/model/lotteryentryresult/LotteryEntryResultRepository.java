package com.example.festival.service.domain.model.lotteryentryresult;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;

public interface LotteryEntryResultRepository {

  LotteryEntryResult findLotteryEntryResult(
      FestivalId festivalId,
      MemberId memberId,
      EntryId entryId);

  void saveLotteryEntryResult(LotteryEntryResult lotteryEntryResult);
}
