package com.example.festival.service.integration.application;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.festival.service.application.application.ApplyForEntryRequest;
import com.example.festival.service.domain.model.entry.EntryStatus;
import com.example.festival.service.integration.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.time.LocalDate;
import javax.sql.DataSource;
import org.assertj.db.type.Changes;
import org.assertj.db.type.DateValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplyForEntryTest {

  private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void prepare() {

    final Operation insertSponsors =
        insertInto("sponsors")
            .row()
            .column("sponsor_id", 1)
            .column("sponsor_name", "A食品")
            .column("representative_name", "田中一郎")
            .column("address", "東京都渋谷区恵比寿西１－２－３")
            .column("phone_number", "03-1234-5678")
            .column("email", "ichiro@example.com")
            .end()
            .build();

    final Operation insertEventTypes =
        insertInto("event_types")
            .columns("event_type_code", "event_type_name")
            .values("R01", "ランニング")
            .values("B01", "自転車レース")
            .build();

    final Operation insertEvents =
        insertInto("events")
            .columns("event_code", "event_name", "event_type_code")
            .values(1, "フルマラソン", "R01")
            .build();

    final Operation insertFestivals =
        insertInto("festivals")
            .columns("festival_id", "festival_name", "sponsor_id",
                "held_date", "held_place_prefecture_code")
            .values(1, "わくわくスポーツ大会", 1, LocalDate.of(2019, 10, 1), 13)
            .values(2, "どきどきスポーツ大会", 1, LocalDate.of(2019, 11, 1), 13)
            .values(3, "スポーツ大会2018", 1, LocalDate.of(2018, 10, 1), 13)
            .build();

    final Operation insertEntries =
        insertInto("entries")
            .row()
            .column("festival_id", 1)
            .column("entry_id", 1)
            .column("entry_name", "先着枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 1000)
            .column("application_numbers", 0)
            .column("first_arrival_lottery_type", "firstArrival")
            .column("application_start_date", LocalDate.of(2019, 9, 11))
            .column("application_end_date", LocalDate.of(2019, 9, 20))
            .column("entry_status", "recruiting")
            .end()
            .row()
            .column("festival_id", 1)
            .column("entry_id", 2)
            .column("entry_name", "抽選枠")
            .column("entry_description", "抽選枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 500)
            .column("application_numbers", 0)
            .column("first_arrival_lottery_type", "lottery")
            .column("application_start_date", LocalDate.of(2019, 9, 21))
            .column("application_end_date", LocalDate.of(2019, 9, 25))
            .column("entry_status", "recruiting")
            .end()
            .row()
            .column("festival_id", 3)
            .column("entry_id", 3)
            .column("entry_name", "先着枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 1000)
            .column("application_numbers", 5)
            .column("first_arrival_lottery_type", "firstArrival")
            .column("application_start_date", LocalDate.of(2018, 9, 11))
            .column("application_end_date", LocalDate.of(2018, 9, 20))
            .column("entry_status", "participantConfirmation")
            .end()
            .row()
            .column("festival_id", 3)
            .column("entry_id", 4)
            .column("entry_name", "抽選枠")
            .column("entry_description", "抽選枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 500)
            .column("application_numbers", 5)
            .column("first_arrival_lottery_type", "lottery")
            .column("application_start_date", LocalDate.of(2018, 9, 21))
            .column("application_end_date", LocalDate.of(2018, 9, 25))
            .column("entry_status", "participantConfirmation")
            .end()
            .row()
            .column("festival_id", 2)
            .column("entry_id", 5)
            .column("entry_name", "先着枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 2)
            .column("participation_fees", 1000)
            .column("application_numbers", 1)
            .column("first_arrival_lottery_type", "firstArrival")
            .column("application_start_date", LocalDate.of(2019, 10, 11))
            .column("application_end_date", LocalDate.of(2019, 10, 20))
            .column("entry_status", "recruiting")
            .end()
            .row()
            .column("festival_id", 2)
            .column("entry_id", 6)
            .column("entry_name", "抽選枠")
            .column("entry_description", "抽選枠です")
            .column("event_code", 1)
            .column("capacity", 2)
            .column("participation_fees", 1000)
            .column("application_numbers", 1)
            .column("first_arrival_lottery_type", "lottery")
            .column("application_start_date", LocalDate.of(2019, 10, 11))
            .column("application_end_date", LocalDate.of(2019, 10, 20))
            .column("entry_status", "recruiting")
            .end()
            .build();

    final Operation insertLotteryEntries =
        insertInto("lottery_entries")
            .columns("festival_id", "entry_id", "lottery_date")
            .values(1, 2, LocalDate.of(2019, 9, 26))
            .values(3, 4, LocalDate.of(2018, 9, 26))
            .build();

    final Operation insertMembers =
        insertInto("members")
            .columns("member_id", "member_name", "sex", "birthday",
                "address", "phone_number", "email")
            .values(1, "江崎 博幸", "man", LocalDate.of(1990, 4, 1),
                "東京都渋谷区神宮前", "090-1111-1111", "ezaki@example.com")
            .values(2, "津久井 聡志", "man", LocalDate.of(1990, 5, 1),
                "東京都渋谷区渋谷", "090-2222-2222", "tsukui@example.com")
            .values(3, "堀内 みどり", "woman", LocalDate.of(1990, 6, 1),
                "東京都渋谷区恵比寿", "090-3333-3333", "horiuchi@example.com")
            .build();

    final Operation insertApplications =
        insertInto("applications")
            .row()
            .column("festival_id", 1)
            .column("member_id", 2)
            .column("entry_id", 1)
            .column("application_date", LocalDate.of(2019, 9, 11))
            .column("lottery_result", null)
            .column("payment_date", null)
            .column("use_points", 0)
            .end()
            .build();

    Operation operation = sequenceOf(
        TestHelper.deleteAll,
        TestHelper.resetSequences,
        insertSponsors,
        insertEventTypes,
        insertEvents,
        insertFestivals,
        insertEntries,
        insertLotteryEntries,
        insertMembers,
        insertApplications);

    Destination dest = new DataSourceDestination(dataSource);
    DbSetup dbSetup = new DbSetup(dest, operation);
    dbSetupTracker.launchIfNecessary(dbSetup);
  }

  @DisplayName("先着枠に申込できること")
  @Test
  void testApplyForFirstArrivalEntry() throws Exception {

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(1L);
    request.setEntryId(1L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 9, 12));

    final String requestJson = objectMapper.writeValueAsString(request);

    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());

    changes.setEndPointNow();

    assertThat(changes)
        .hasNumberOfChanges(2)
        .changeOnTable("entries")
        .isModification()
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(1)
        .value("entry_id").isEqualTo(1)
        .value("application_numbers").isEqualTo(1)
        .changeOnTable("applications")
        .isCreation()
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(1)
        .value("member_id").isEqualTo(1)
        .value("entry_id").isEqualTo(1)
        .value("application_date").isEqualTo(DateValue.of(2019, 9, 12))
        .value("lottery_result").isNull()
        .value("payment_date").isNull()
        .value("use_points").isEqualTo(0);
  }

  @DisplayName("抽選枠に申込できること")
  @Test
  void testApplyForLotteryEntry() throws Exception {

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(1L);
    request.setEntryId(2L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 9, 21));

    final String requestJson = objectMapper.writeValueAsString(request);

    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());

    changes.setEndPointNow();

    assertThat(changes)
        .hasNumberOfChanges(2)
        .changeOnTable("entries")
        .isModification()
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(1)
        .value("entry_id").isEqualTo(2)
        .value("application_numbers").isEqualTo(1)
        .changeOnTable("applications")
        .isCreation()
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(1)
        .value("member_id").isEqualTo(1)
        .value("entry_id").isEqualTo(2)
        .value("application_date").isEqualTo(DateValue.of(2019, 9, 21))
        .value("lottery_result").isNull()
        .value("payment_date").isNull()
        .value("use_points").isEqualTo(0);
  }

  @DisplayName("募集開始日に申込を行えること")
  @Test
  void testApplyForEntryOnApplicationStartDate() throws Exception {

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(1L);
    request.setEntryId(1L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 9, 11));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());
  }

  @DisplayName("募集終了日に申込を行えること")
  @Test
  void testApplyForEntryOnApplicationEndDate() throws Exception {

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(1L);
    request.setEntryId(1L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 9, 20));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());
  }

  @DisplayName("先着順のエントリ枠に申込、申込人数が定員に達した場合、エントリ枠状態を参加者確定に変更すること")
  @Test
  void testChangeEntryStatusForArrivalFirst() throws Exception {

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(2L);
    request.setEntryId(5L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 10, 12));

    final String requestJson = objectMapper.writeValueAsString(request);

    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());

    changes.setEndPointNow();

    assertThat(changes)
        .hasNumberOfChanges(2)
        .changeOnTable("entries")
        .isModification()
        .rowAtStartPoint()
        .value("application_numbers").isEqualTo(1)
        .value("entry_status").isEqualTo(EntryStatus.recruiting.toString())
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(2)
        .value("entry_id").isEqualTo(5)
        .value("application_numbers").isEqualTo(2)
        .value("entry_status").isEqualTo(EntryStatus.participantConfirmation.toString())
        .changeOnTable("applications")
        .isCreation();
  }

  @DisplayName("抽選のエントリ枠に申込、申込人数が定員に達しても、エントリ枠状態を変更しないこと")
  @Test
  void testChangeEntryStatusForLottery() throws Exception {

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(2L);
    request.setEntryId(6L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 10, 12));

    final String requestJson = objectMapper.writeValueAsString(request);

    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());

    changes.setEndPointNow();

    assertThat(changes)
        .hasNumberOfChanges(2)
        .changeOnTable("entries")
        .isModification()
        .rowAtStartPoint()
        .value("application_numbers").isEqualTo(1)
        .value("entry_status").isEqualTo(EntryStatus.recruiting.toString())
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(2)
        .value("entry_id").isEqualTo(6)
        .value("application_numbers").isEqualTo(2)
        .value("entry_status").isEqualTo(EntryStatus.recruiting.toString())
        .changeOnTable("applications")
        .isCreation();
  }

  @DisplayName("バリデーションエラーになること")
  @Test
  void testValidationError() throws Exception {

    dbSetupTracker.skipNextLaunch();

    ApplyForEntryRequest request = new ApplyForEntryRequest();

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("入力内容にエラーがあります")))
        .andExpect(jsonPath("$.details").isArray())
        .andExpect(jsonPath("$.details", hasSize(4)));
  }

  @DisplayName("既に申し込み済みのエラーになること")
  @Test
  void testBusinessError1() throws Exception {

    dbSetupTracker.skipNextLaunch();

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(1L);
    request.setEntryId(2L);
    request.setMemberId(2L);
    request.setApplicationDate(LocalDate.of(2019, 9, 21));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("指定した大会には既に申し込み済みです")));
  }

  @DisplayName("募集を行っていないエントリ枠に申込んだときエラーになること")
  @Test
  void testBusinessError2() throws Exception {

    dbSetupTracker.skipNextLaunch();

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(3L);
    request.setEntryId(3L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2018, 9, 21));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("指定した大会は現在募集を行っておりません")));
  }

  @DisplayName("募集開始前の場合エラーになること")
  @Test
  void testBusinessError3() throws Exception {

    dbSetupTracker.skipNextLaunch();

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(1L);
    request.setEntryId(1L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 9, 10));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("指定した大会はまだ募集を開始していません")));
  }

  @DisplayName("募集終了日を過ぎている場合エラーになること")
  @Test
  void testBusinessError4() throws Exception {

    dbSetupTracker.skipNextLaunch();

    ApplyForEntryRequest request = new ApplyForEntryRequest();
    request.setFestivalId(1L);
    request.setEntryId(1L);
    request.setMemberId(1L);
    request.setApplicationDate(LocalDate.of(2019, 9, 21));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/entry")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("指定した大会の募集期間を過ぎています")));
  }
}
