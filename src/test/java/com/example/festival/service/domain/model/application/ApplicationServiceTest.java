package com.example.festival.service.domain.model.application;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.entry.EntryStatus;
import com.example.festival.service.domain.model.entry.FirstArrivalEntry;
import com.example.festival.service.domain.model.entry.LotteryEntry;
import com.example.festival.service.domain.model.event.EventCode;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.Amount;
import com.example.festival.service.domain.type.NumberOfPeople;
import com.example.festival.service.support.BusinessErrorException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationServiceTest {

  @DisplayName("参加申込オブジェクトを生成して返すこと")
  @Test
  void testSuccess() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(2);
    MemberId memberId = new MemberId(3);

    Entry entry = new FirstArrivalEntry(
        festivalId, entryId, "一般枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
        EntryStatus.recruiting);

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(new ArrayList<>());

    ApplicationService applicationService = new ApplicationService(
        entry, festivalApplicationPolicy);

    Application application = applicationService.applyEntry(memberId, LocalDate.of(2019, 4, 1));

    assertAll("application",
        () -> assertEquals(Integer.valueOf(1), application.festivalId.value(), "festivalId"),
        () -> assertEquals(Integer.valueOf(3), application.memberId.value(), "memberId"),
        () -> assertEquals(Integer.valueOf(2), application.entryId.value(), "entryId"),
        () -> assertEquals(LocalDate.of(2019, 4, 1), application.applicationDate, "applicationDate")
    );
  }

  @DisplayName("募集開始前の申込時に業務例外を throw すること")
  @Test
  void testBusinessError1() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Entry entry = new FirstArrivalEntry(
        festivalId, entryId, "一般枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
        EntryStatus.recruiting);

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(new ArrayList<>());

    ApplicationService applicationService = new ApplicationService(
        entry, festivalApplicationPolicy);

    assertThrows(BusinessErrorException.class, () ->
        applicationService.applyEntry(memberId, LocalDate.of(2019, 3, 30)));
  }

  @DisplayName("募集終了日より後の申込時に業務例外を throw すること")
  @Test
  void testBusinessError2() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Entry entry = new FirstArrivalEntry(
        festivalId, entryId, "一般枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
        EntryStatus.recruiting);

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(new ArrayList<>());

    ApplicationService applicationService = new ApplicationService(
        entry, festivalApplicationPolicy);

    assertThrows(BusinessErrorException.class, () ->
        applicationService.applyEntry(memberId, LocalDate.of(2019, 4, 3)));
  }

  @DisplayName("参加者確定の状態で申し込んだ場合、業務例外を throw すること")
  @Test
  void testBusinessError3() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Entry entry = new FirstArrivalEntry(
        festivalId, entryId, "一般枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
        EntryStatus.participantConfirmation);

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(new ArrayList<>());

    ApplicationService applicationService = new ApplicationService(
        entry, festivalApplicationPolicy);

    assertThrows(BusinessErrorException.class, () ->
        applicationService.applyEntry(memberId, LocalDate.of(2019, 4, 1)));
  }

  @DisplayName("抽選中の状態で申し込んだ場合、業務例外を throw すること")
  @Test
  void testBusinessError4() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Entry entry = new LotteryEntry(
        festivalId, entryId, "抽選枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
        EntryStatus.underLottery, LocalDate.of(2019, 4, 3), new EntryId(null));

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(new ArrayList<>());

    ApplicationService applicationService = new ApplicationService(
        entry, festivalApplicationPolicy);

    assertThrows(BusinessErrorException.class, () ->
        applicationService.applyEntry(memberId, LocalDate.of(2019, 4, 1)));
  }

  @DisplayName("既にその大会に申込済みの場合、業務例外を throw すること")
  @Test
  void testBusinessError5() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Entry entry = new LotteryEntry(
        festivalId, entryId, "抽選枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
        EntryStatus.underLottery, LocalDate.of(2019, 4, 3), new EntryId(null));

    Application application = Application.createEntityForEntry(
        festivalId,
        memberId,
        new EntryId(2),
        LocalDate.of(2019, 4, 1)
    );
    List<Application> applicationList = new ArrayList<>();
    applicationList.add(application);

    FestivalApplicationPolicy festivalApplicationPolicy =
        new FestivalApplicationPolicy(applicationList);

    ApplicationService applicationService = new ApplicationService(
        entry, festivalApplicationPolicy);

    assertThrows(BusinessErrorException.class, () ->
        applicationService.applyEntry(memberId, LocalDate.of(2019, 4, 1)));
  }
}
