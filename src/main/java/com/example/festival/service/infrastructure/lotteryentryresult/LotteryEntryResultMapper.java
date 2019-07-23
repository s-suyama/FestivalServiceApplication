package com.example.festival.service.infrastructure.lotteryentryresult;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResult;
import com.example.festival.service.domain.model.member.MemberId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LotteryEntryResultMapper {

  LotteryEntryResult selectLotteryEntryResult(
      @Param("festivalId") FestivalId festivalId,
      @Param("memberId") MemberId memberId,
      @Param("entryId") EntryId entryId);

  int insertLotteryEntryResult(LotteryEntryResult lotteryEntryResult);
}
