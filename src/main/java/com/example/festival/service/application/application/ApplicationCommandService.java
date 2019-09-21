package com.example.festival.service.application.application;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.application.ApplicationRepository;
import com.example.festival.service.domain.model.application.ApplicationService;
import com.example.festival.service.domain.model.application.FestivalApplicationPolicy;
import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.entry.EntryRepository;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.Member;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.model.member.MemberRepository;
import com.example.festival.service.support.BusinessErrorException;
import java.time.LocalDate;
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
    final EntryId entryId = request.entryId();
    final MemberId memberId = request.memberId();
    final LocalDate applicationDate = request.getApplicationDate();

    final Member member = memberRepository.findMember(memberId);
    if (member == null) {
      throw new BusinessErrorException("存在しない会員です");
    }

    final Entry entry = entryRepository.findEntry(festivalId, entryId);

    if (entry == null) {
      throw new BusinessErrorException("存在しないエントリ枠です");
    }

    final FestivalApplicationPolicy festivalApplicationPolicy =
        applicationRepository.createFestivalApplicationPolicy(festivalId, memberId);

    final ApplicationService applicationService =
        new ApplicationService(entry, festivalApplicationPolicy);

    final Application application = applicationService.createApplication(memberId, applicationDate);

    entry.incrementApplicationNumbers();
    entryRepository.saveEntry(entry);

    applicationRepository.addApplication(application);
  }
}
