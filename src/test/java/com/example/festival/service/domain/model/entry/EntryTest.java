package com.example.festival.service.domain.model.entry;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.application.ApplicationDate;
import com.example.festival.service.domain.model.event.EventCode;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.Amount;
import com.example.festival.service.domain.type.NumberOfPeople;
import com.example.festival.service.support.BusinessErrorException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntryTest {

  @DisplayName("募集開始前の申込時に業務例外を throw すること")
  @Test
  void testBusinessError1() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);
    MemberId memberId = new MemberId(1);

    Entry entry = new FirstArrivalEntry(
        festivalId, entryId, "一般枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        new ApplicationStartDate(LocalDate.of(2019, 4, 1)),
        new ApplicationEndDate(LocalDate.of(2019, 4, 2)),
        EntryStatus.recruiting);

    Application application =
        Application.createEntityForEntry(
            festivalId, memberId, entryId, new ApplicationDate(LocalDate.of(2019, 3, 30)));

    assertThrows(BusinessErrorException.class, () ->
        entry.validateAndThrowBusinessErrorIfHasErrorForApplication(application));
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
        new ApplicationStartDate(LocalDate.of(2019, 4, 1)),
        new ApplicationEndDate(LocalDate.of(2019, 4, 2)),
        EntryStatus.recruiting);

    Application application =
        Application.createEntityForEntry(
            festivalId, memberId, entryId, new ApplicationDate(LocalDate.of(2019, 4, 3)));

    assertThrows(BusinessErrorException.class, () ->
        entry.validateAndThrowBusinessErrorIfHasErrorForApplication(application));
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
        new ApplicationStartDate(LocalDate.of(2019, 4, 1)),
        new ApplicationEndDate(LocalDate.of(2019, 4, 2)),
        EntryStatus.participantConfirmation);

    Application application =
        Application.createEntityForEntry(
            festivalId, memberId, entryId, new ApplicationDate(LocalDate.of(2019, 4, 1)));

    assertThrows(BusinessErrorException.class, () ->
        entry.validateAndThrowBusinessErrorIfHasErrorForApplication(application));
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
        new ApplicationStartDate(LocalDate.of(2019, 4, 1)),
        new ApplicationEndDate(LocalDate.of(2019, 4, 2)),
        EntryStatus.underLottery, new LotteryDate(LocalDate.of(2019, 4, 3)), new EntryId(null));

    Application application =
        Application.createEntityForEntry(
            festivalId, memberId, entryId, new ApplicationDate(LocalDate.of(2019, 4, 1)));

    assertThrows(BusinessErrorException.class, () ->
        entry.validateAndThrowBusinessErrorIfHasErrorForApplication(application));
  }

  @DisplayName("先着順のエントリ枠で申込人数が定員に達した場合、参加者確定に変更すること")
  @Test
  void incrementApplicationNumbers1() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);

    Entry entry = new FirstArrivalEntry(
        festivalId, entryId, "先着枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        new ApplicationStartDate(LocalDate.of(2019, 4, 1)),
        new ApplicationEndDate(LocalDate.of(2019, 4, 2)),
        EntryStatus.recruiting);

    entry.incrementApplicationNumbers();

    assertAll("entry",
        () -> assertEquals(new NumberOfPeople(1), entry.applicationNumbers),
        () -> assertEquals(EntryStatus.participantConfirmation, entry.entryStatus)
    );
  }

  @DisplayName("抽選のエントリ枠で申込人数が定員に達した場合、エントリ枠状態を変更しないこと")
  @Test
  void incrementApplicationNumbers2() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);

    Entry entry = new LotteryEntry(
        festivalId, entryId, "抽選枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        new ApplicationStartDate(LocalDate.of(2019, 4, 1)),
        new ApplicationEndDate(LocalDate.of(2019, 4, 2)),
        EntryStatus.recruiting, new LotteryDate(LocalDate.of(2019, 4, 3)), new EntryId(null));

    entry.incrementApplicationNumbers();

    assertAll("entry",
        () -> assertEquals(new NumberOfPeople(1), entry.applicationNumbers),
        () -> assertEquals(EntryStatus.recruiting, entry.entryStatus)
    );
  }
}
