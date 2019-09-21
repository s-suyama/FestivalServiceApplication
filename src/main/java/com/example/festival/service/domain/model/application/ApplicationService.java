package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryStatus;
import com.example.festival.service.domain.model.member.Member;
import com.example.festival.service.support.BusinessErrorException;
import java.time.LocalDate;

public class ApplicationService {

  Member member;

  Entry entry;

  public ApplicationService(Member member, Entry entry) {
    this.member = member;
    this.entry = entry;
  }

  public Application applyEntry() {

    LocalDate today = LocalDate.now();

    if (entry.entryStatus() != EntryStatus.recruiting) {
      throw new BusinessErrorException("指定した大会は現在募集を行っておりません");
    }

    if (today.compareTo(entry.applicationStartDate()) < 0) {
      throw new BusinessErrorException("指定した大会はまだ募集を開始していません");
    }

    if (today.compareTo(entry.applicationEndDate()) > 0) {
      throw new BusinessErrorException("指定した大会の募集期間を過ぎています");
    }

    return Application.createEntityForEntry(
        entry.festivalId(),
        member.memberId(),
        entry.entryId(),
        today
    );
  }
}
