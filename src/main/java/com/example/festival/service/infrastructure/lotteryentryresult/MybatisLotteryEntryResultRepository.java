package com.example.festival.service.infrastructure.lotteryentryresult;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResult;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResultRepository;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResults;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.infrastructure.entry.EntryDto;
import com.example.festival.service.infrastructure.entry.EntryMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisLotteryEntryResultRepository implements LotteryEntryResultRepository {

  private final LotteryEntryResultMapper lotteryEntryResultMapper;

  private final EntryMapper entryMapper;

  /**
   * コンストラクタ.
   */
  public MybatisLotteryEntryResultRepository(
      LotteryEntryResultMapper lotteryEntryResultMapper,
      EntryMapper entryMapper) {

    this.lotteryEntryResultMapper = lotteryEntryResultMapper;
    this.entryMapper = entryMapper;
  }

  @Override
  public LotteryEntryResult findLotteryEntryResult(
      FestivalId festivalId,
      MemberId memberId,
      EntryId entryId) {

    return lotteryEntryResultMapper.selectLotteryEntryResult(festivalId, memberId, entryId);
  }

  @Override
  public LotteryEntryResults findLotteryEntryResults(
      FestivalId festivalId,
      MemberId memberId,
      EntryId entryId) {

    List<LotteryEntryResult> resultList = new ArrayList<>();

    EntryId targetEntryId = entryId;
    while (true) {
      LotteryEntryResult lotteryEntryResult = lotteryEntryResultMapper.selectLotteryEntryResult(
          festivalId, memberId, targetEntryId);
      if (lotteryEntryResult == null) {
        break;
      }

      resultList.add(lotteryEntryResult);

      EntryDto entryDto = entryMapper.selectEntry(festivalId, targetEntryId);
      EntryId followingEntryId = entryDto.followingEntryId();
      if (followingEntryId == null) {
        break;
      }
      targetEntryId = followingEntryId;
    }

    return new LotteryEntryResults(resultList);
  }

  @Override
  public void saveLotteryEntryResult(LotteryEntryResult lotteryEntryResult) {

    int cnt = lotteryEntryResultMapper.insertLotteryEntryResult(lotteryEntryResult);
    if (cnt != 1) {
      throw new IllegalStateException("lottery_entry_results への insert が失敗しました");
    }
  }
}
