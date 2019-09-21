package com.example.festival.service.domain.model.entry;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.festival.service.domain.model.event.EventCode;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.type.Amount;
import com.example.festival.service.domain.type.NumberOfPeople;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntryTest {

  @DisplayName("先着順のエントリ枠で申込人数が定員に達した場合、参加者確定に変更すること")
  @Test
  void incrementApplicationNumbers1() {

    FestivalId festivalId = new FestivalId(1);
    EntryId entryId = new EntryId(1);

    Entry entry = new FirstArrivalEntry(
        festivalId, entryId, "先着枠", "説明", new EventCode(1), new NumberOfPeople(1),
        new Amount(BigDecimal.valueOf(1000)), new NumberOfPeople(0),
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
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
        LocalDate.of(2019, 4, 1),
        LocalDate.of(2019, 4, 2),
        EntryStatus.recruiting, LocalDate.of(2019, 4, 3), new EntryId(null));

    entry.incrementApplicationNumbers();

    assertAll("entry",
        () -> assertEquals(new NumberOfPeople(1), entry.applicationNumbers),
        () -> assertEquals(EntryStatus.recruiting, entry.entryStatus)
    );
  }
}
