package com.example.festival.service.infrastructure.lotteryentryresult;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResult;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResultRepository;
import com.example.festival.service.domain.model.member.MemberId;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisLotteryEntryResultRepository implements LotteryEntryResultRepository {

  private final LotteryEntryResultMapper lotteryEntryResultMapper;

  public MybatisLotteryEntryResultRepository(LotteryEntryResultMapper lotteryEntryResultMapper) {
    this.lotteryEntryResultMapper = lotteryEntryResultMapper;
  }

  @Override
  public LotteryEntryResult findLotteryEntryResult(
      FestivalId festivalId,
      MemberId memberId,
      EntryId entryId) {

    return lotteryEntryResultMapper.selectLotteryEntryResult(festivalId, memberId, entryId);
  }

  @Override
  public void saveLotteryEntryResult(LotteryEntryResult lotteryEntryResult) {

    int cnt = lotteryEntryResultMapper.insertLotteryEntryResult(lotteryEntryResult);
    if (cnt != 1) {
      throw new IllegalStateException("lottery_entry_results への insert が失敗しました");
    }
  }
}
