package com.example.festival.service.application.lotteryentryresult;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.application.ApplicationRepository;
import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryRepository;
import com.example.festival.service.domain.model.entry.EntryStatus;
import com.example.festival.service.domain.model.entry.LotteryEntry;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResult;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResultRepository;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryResult;
import com.example.festival.service.domain.model.member.Member;
import com.example.festival.service.domain.model.member.MemberRepository;
import com.example.festival.service.support.BusinessErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class LotteryEntryResultCommandService {

  private final LotteryEntryResultRepository lotteryEntryResultRepository;

  private final EntryRepository entryRepository;

  private final ApplicationRepository applicationRepository;

  private final MemberRepository memberRepository;

  /**
   * Constructor.
   */
  public LotteryEntryResultCommandService(
      LotteryEntryResultRepository lotteryEntryResultRepository,
      EntryRepository entryRepository,
      ApplicationRepository applicationRepository,
      MemberRepository memberRepository) {

    this.lotteryEntryResultRepository = lotteryEntryResultRepository;
    this.entryRepository = entryRepository;
    this.applicationRepository = applicationRepository;
    this.memberRepository = memberRepository;
  }

  /**
   * 抽選結果を登録する.
   */
  public void registerLotteryEntryResult(RegisterLotteryEntryResultRequest request) {

    final Member member = memberRepository.findMember(request.memberId());
    if (member == null) {
      throw new BusinessErrorException("存在しない会員です");
    }

    final Entry entry = entryRepository.findEntry(request.festivalId(), request.entryId());

    if (entry == null) {
      throw new BusinessErrorException("存在しないエントリ枠です");
    }

    if (!entry.isLotteryEntry()) {
      throw new BusinessErrorException("抽選のエントリ枠ではありません");
    }

    if (entry.entryStatus() != EntryStatus.underLottery) {
      throw new BusinessErrorException("まだ抽選を開始していません");
    }

    LotteryEntryResult already = lotteryEntryResultRepository.findLotteryEntryResult(
        request.festivalId(),
        request.memberId(),
        request.entryId()
    );
    if (already != null) {
      throw new BusinessErrorException("既に抽選結果を登録済みです");
    }

    Application application = applicationRepository.findApplication(
        request.festivalId(),
        request.memberId());
    if (application == null) {
      throw new BusinessErrorException("対象の大会には申込を行っていません");
    }

    // 申込を行ったエントリ枠ではない場合、申し込んだエントリ枠の多段階抽選枠か？
    if (!(application.entryId().equals(request.entryId()))) {
      Entry firstEntry = entryRepository.findEntry(request.festivalId(), application.entryId());
      if (!(((LotteryEntry)firstEntry).followingEntryId().equals(request.entryId()))) {
        throw new BusinessErrorException("抽選対象外のエントリ枠です");
      }

      LotteryEntryResult firstEntryResult =
          lotteryEntryResultRepository.findLotteryEntryResult(
              application.festivalId(),
              application.memberId(),
              application.entryId());
      if (firstEntryResult == null) {
        throw new BusinessErrorException("まだ申し込んだエントリ枠の抽選結果が登録されていません");
      }

      if (firstEntryResult.lotteryResult() == LotteryResult.winning) {
        throw new BusinessErrorException("既に当選済みのため、後続エントリ枠の抽選結果を登録できません");
      }
    }

    LotteryEntryResult lotteryEntryResult = new LotteryEntryResult(
        request.festivalId(),
        request.memberId(),
        request.entryId(),
        request.getLotteryResult()
    );

    lotteryEntryResultRepository.saveLotteryEntryResult(lotteryEntryResult);
  }
}
