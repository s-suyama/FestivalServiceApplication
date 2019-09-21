package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryStatus;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.support.BusinessErrorException;
import java.time.LocalDate;

public class ApplicationService {

  private Entry entry;

  private FestivalApplicationPolicy festivalApplicationPolicy;

  /**
   * コンストラクタ.
   */
  public ApplicationService(
      Entry entry, FestivalApplicationPolicy festivalApplicationPolicy) {

    this.entry = entry;
    this.festivalApplicationPolicy = festivalApplicationPolicy;
  }

  /**
   * 参加申込オブジェクトを生成して返す.
   * @return 参加申込オブジェクト
   */
  public Application createApplication(MemberId memberId, LocalDate applicationDate) {

    if (entry.entryStatus() != EntryStatus.recruiting) {
      throw new BusinessErrorException("指定した大会は現在募集を行っておりません");
    }

    if (applicationDate.compareTo(entry.applicationStartDate()) < 0) {
      throw new BusinessErrorException("指定した大会はまだ募集を開始していません");
    }

    if (applicationDate.compareTo(entry.applicationEndDate()) > 0) {
      throw new BusinessErrorException("指定した大会の募集期間を過ぎています");
    }

    if (festivalApplicationPolicy.hasAlreadyApplyForSameFestival(
        memberId, entry.festivalId())) {
      throw new BusinessErrorException("指定した大会には既に申し込み済みです");
    }

    return Application.createEntityForEntry(
        entry.festivalId(),
        memberId,
        entry.entryId(),
        applicationDate
    );
  }
}
