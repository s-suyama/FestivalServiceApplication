package com.example.festival.service.application.application;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.application.ApplicationDate;
import com.example.festival.service.domain.model.application.ApplicationRepository;
import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.entry.EntryRepository;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.Member;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.model.member.MemberRepository;
import com.example.festival.service.support.BusinessErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ApplicationCommandService {

  private final EntryRepository entryRepository;

  private final ApplicationRepository applicationRepository;

  private final MemberRepository memberRepository;

  /**
   * Constructor.
   */
  public ApplicationCommandService(
      ApplicationRepository applicationRepository,
      EntryRepository entryRepository,
      MemberRepository memberRepository) {

    this.entryRepository = entryRepository;
    this.applicationRepository = applicationRepository;
    this.memberRepository = memberRepository;
  }

  /**
   * エントリー枠に申し込む.
   */
  public void applyForEntry(ApplyForEntryRequest request) {

    final FestivalId festivalId = request.festivalId();
    final MemberId memberId = request.memberId();
    final EntryId entryId = request.entryId();
    final ApplicationDate applicationDate = request.applicationDate();

    final Application alreadyApplication =
        applicationRepository.findApplication(festivalId, memberId);

    if (alreadyApplication != null) {
      throw new BusinessErrorException("指定した大会には既に申し込み済みです");
    }

    final Entry entry = entryRepository.findEntry(festivalId, entryId);

    if (entry == null) {
      throw new BusinessErrorException("存在しないエントリ枠です");
    }

    final Member member = memberRepository.findMember(request.memberId());
    if (member == null) {
      throw new BusinessErrorException("存在しない会員です");
    }

    final Application application = Application.createEntityForEntry(
        festivalId,
        memberId,
        entryId,
        applicationDate
    );

    entry.validateAndThrowBusinessErrorIfHasErrorForApplication(application);

    entry.incrementApplicationNumbers();
    entryRepository.saveEntry(entry);

    applicationRepository.addApplication(application);
  }
}
