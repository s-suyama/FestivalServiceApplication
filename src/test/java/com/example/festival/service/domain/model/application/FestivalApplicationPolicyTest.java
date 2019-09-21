package com.example.festival.service.domain.model.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FestivalApplicationPolicyTest {

  @DisplayName("既にその大会に参加申込している場合 true を返すこと")
  @Test
  void hasAlreadyApplyForSameFestival1() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Application application = Application.createEntityForEntry(
        festivalId,
        memberId,
        entryId,
        LocalDate.of(2019, 4, 1)
    );
    List<Application> applicationList = new ArrayList<>();
    applicationList.add(application);

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(applicationList);

    boolean result = festivalApplicationPolicy.hasAlreadyApplyForSameFestival(
        memberId, festivalId);

    assertThat(result, is(true));
  }

  @DisplayName("まだその大会に参加申込していない場合 false を返すこと")
  @Test
  void hasAlreadyApplyForSameFestival2() {

    FestivalId festivalId = new FestivalId(1);
    FestivalId otherFestivalId = new FestivalId(2);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Application application = Application.createEntityForEntry(
        festivalId,
        memberId,
        entryId,
        LocalDate.of(2019, 4, 1)
    );
    List<Application> applicationList = new ArrayList<>();
    applicationList.add(application);

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(applicationList);

    boolean result = festivalApplicationPolicy.hasAlreadyApplyForSameFestival(
        memberId, otherFestivalId);

    assertThat(result, is(false));
  }

}
