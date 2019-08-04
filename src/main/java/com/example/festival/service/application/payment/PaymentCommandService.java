package com.example.festival.service.application.payment;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.application.ApplicationRepository;
import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.entry.EntryRepository;
import com.example.festival.service.domain.model.entry.LotteryEntry;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResult;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryEntryResultRepository;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryResult;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.model.memberpoint.MemberPointRepository;
import com.example.festival.service.domain.model.memberpoint.MemberPoints;
import com.example.festival.service.domain.type.PointAmount;
import com.example.festival.service.support.BusinessErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PaymentCommandService {

  private final ApplicationRepository applicationRepository;

  private final EntryRepository entryRepository;

  private final LotteryEntryResultRepository lotteryEntryResultRepository;

  private final MemberPointRepository memberPointRepository;

  /**
   * Constructor.
   */
  public PaymentCommandService(
      ApplicationRepository applicationRepository,
      EntryRepository entryRepository,
      LotteryEntryResultRepository lotteryEntryResultRepository,
      MemberPointRepository memberPointRepository) {

    this.applicationRepository = applicationRepository;
    this.entryRepository = entryRepository;
    this.lotteryEntryResultRepository = lotteryEntryResultRepository;
    this.memberPointRepository = memberPointRepository;
  }

  /**
   * 入金する.
   */
  public void payToApplication(PaymentRequest request) {

    final FestivalId festivalId = request.festivalId();
    final MemberId memberId = request.memberId();

    Application application = applicationRepository.findApplication(festivalId, memberId);

    if (application == null) {
      throw new BusinessErrorException("対象の大会には申込を行っていません");
    }

    Entry entry = entryRepository.findEntry(festivalId, application.entryId());
    if (entry.isLotteryEntry()) {
      // 対象のエントリが抽選なら当選しているかを確認する
      LotteryEntryResult entryResult = lotteryEntryResultRepository.findLotteryEntryResult(
          festivalId, memberId, entry.entryId());

      if (entryResult.lotteryResult() == LotteryResult.failed) {
        EntryId followingEntryId = ((LotteryEntry)entry).followingEntryId();
        if (followingEntryId == null) {
          throw new BusinessErrorException("対象の大会には当選していませｎ");
        } else {
          LotteryEntryResult followingEntryResult =
              lotteryEntryResultRepository.findLotteryEntryResult(
                  festivalId, memberId, followingEntryId);

          if (followingEntryResult.lotteryResult() == LotteryResult.failed) {
            throw new BusinessErrorException("対象の大会には当選していませｎ");
          }
        }
      }
    }

    PointAmount usePoints = new PointAmount(request.getUsePoints());
    if (usePoints.isPositive()) {
      MemberPoints beforeMemberPoints = memberPointRepository.findMemberPoints(memberId);
      MemberPoints afterMemberPoints =
          beforeMemberPoints.usePoints(request.getPaymentDate(), usePoints);

      memberPointRepository.saveMemberPoints(afterMemberPoints);
    }

    application.pay(request.getPaymentDate(), usePoints);
    applicationRepository.saveApplication(application);
  }
}
