package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryStatus;
import com.example.festival.service.domain.model.member.MemberId;
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
  public Application createApplication(MemberId memberId, LocalDate applicationDate)
      throws EntryStatusIsNotRecruitingException,
      HasAlreadyApplyForSameFestivalException {

    // エントリ枠が募集中の間だけ参加申込を受け付ける
    if (entry.entryStatus() != EntryStatus.recruiting) {
      throw new EntryStatusIsNotRecruitingException();
    }

    // 募集開始日と募集終了日での判定は、本来、エントリ枠状態が募集中であれば、
    // エラーは発生しえないはずなので、ここではあえて、IllegalStateException をスローしています。
    if (applicationDate.compareTo(entry.applicationStartDate()) < 0) {
      throw new IllegalStateException("指定した大会はまだ募集を開始していません");
    }

    if (applicationDate.compareTo(entry.applicationEndDate()) > 0) {
      throw new IllegalStateException("指定した大会の募集期間を過ぎています");
    }


    // 会員は一つの大会について一つのエントリ枠だけ参加申込できる
    if (festivalApplicationPolicy.hasAlreadyApplyForSameFestival(
        memberId, entry.festivalId())) {
      throw new HasAlreadyApplyForSameFestivalException();
    }

    return Application.createEntityForEntry(
        entry.festivalId(),
        memberId,
        entry.entryId(),
        applicationDate
    );
  }
}
