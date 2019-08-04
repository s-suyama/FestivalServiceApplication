package com.example.festival.service.integration.application;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.assertj.db.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.festival.service.application.payment.PaymentRequest;
import com.example.festival.service.integration.TestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.operation.Operation;
import java.math.BigDecimal;
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
class PayToApplicationTest {

  private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void prepare() {

    final Operation insertFestivals =
        insertInto("festivals")
            .columns("festival_id", "festival_name", "sponsor_id",
                "held_date", "held_place_prefecture_code")
            .values(1, "スポーツ大会2017春", 1, LocalDate.of(2017, 5, 1), 13)
            .values(2, "スポーツ大会2017秋", 1, LocalDate.of(2017, 10, 1), 13)
            .values(3, "スポーツ大会2018春", 1, LocalDate.of(2018, 5, 1), 13)
            .values(4, "スポーツ大会2018秋", 1, LocalDate.of(2018, 10, 1), 13)
            .values(5, "スポーツ大会2019春", 1, LocalDate.of(2019, 5, 1), 13)
            .values(6, "スポーツ大会2019秋", 1, LocalDate.of(2019, 10, 1), 13)
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
            .column("application_numbers", 5)
            .column("first_arrival_lottery_type", "firstArrival")
            .column("application_start_date", LocalDate.of(2017, 4, 11))
            .column("application_end_date", LocalDate.of(2017, 4, 20))
            .column("entry_status", "participantConfirmation")
            .end()
            .row()
            .column("festival_id", 2)
            .column("entry_id", 2)
            .column("entry_name", "先着枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 1000)
            .column("application_numbers", 5)
            .column("first_arrival_lottery_type", "firstArrival")
            .column("application_start_date", LocalDate.of(2017, 9, 11))
            .column("application_end_date", LocalDate.of(2017, 9, 20))
            .column("entry_status", "participantConfirmation")
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
            .column("application_start_date", LocalDate.of(2018, 4, 11))
            .column("application_end_date", LocalDate.of(2018, 4, 20))
            .column("entry_status", "participantConfirmation")
            .end()
            .row()
            .column("festival_id", 4)
            .column("entry_id", 4)
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
            .column("festival_id", 5)
            .column("entry_id", 5)
            .column("entry_name", "先着枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 1000)
            .column("application_numbers", 5)
            .column("first_arrival_lottery_type", "firstArrival")
            .column("application_start_date", LocalDate.of(2019, 4, 11))
            .column("application_end_date", LocalDate.of(2019, 4, 20))
            .column("entry_status", "participantConfirmation")
            .end()
            .row()
            .column("festival_id", 6)
            .column("entry_id", 6)
            .column("entry_name", "先着枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 1000)
            .column("application_numbers", 5)
            .column("first_arrival_lottery_type", "firstArrival")
            .column("application_start_date", LocalDate.of(2019, 9, 11))
            .column("application_end_date", LocalDate.of(2019, 9, 20))
            .column("entry_status", "participantConfirmation")
            .end()
            .row()
            .column("festival_id", 6)
            .column("entry_id", 7)
            .column("entry_name", "抽選枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 1000)
            .column("application_numbers", 10)
            .column("first_arrival_lottery_type", "lottery")
            .column("application_start_date", LocalDate.of(2019, 9, 11))
            .column("application_end_date", LocalDate.of(2019, 9, 15))
            .column("entry_status", "participantConfirmation")
            .end()
            .row()
            .column("festival_id", 6)
            .column("entry_id", 8)
            .column("entry_name", "抽選枠")
            .column("entry_description", "先着枠です")
            .column("event_code", 1)
            .column("capacity", 5)
            .column("participation_fees", 1000)
            .column("application_numbers", 10)
            .column("first_arrival_lottery_type", "lottery")
            .column("application_start_date", LocalDate.of(2019, 9, 16))
            .column("application_end_date", LocalDate.of(2019, 9, 20))
            .column("entry_status", "participantConfirmation")
            .end()
            .build();

    final Operation insertLotteryEntries =
        insertInto("lottery_entries")
            .columns("festival_id", "entry_id", "lottery_date", "following_entry_id")
            .values(6, 7, LocalDate.of(2019, 9, 16), 8)
            .values(6, 8, LocalDate.of(2018, 9, 26), null)
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

    Operation operation = sequenceOf(
        TestHelper.deleteAll,
        TestHelper.resetSequences,
        TestHelper.insertSponsors,
        TestHelper.insertEventTypes,
        TestHelper.insertEvents,
        insertFestivals,
        insertEntries,
        insertLotteryEntries,
        insertMembers);

    Destination dest = new DataSourceDestination(dataSource);
    DbSetup dbSetup = new DbSetup(dest, operation);
    dbSetupTracker.launchIfNecessary(dbSetup);
  }

  @DisplayName("ポイント利用なしで先着枠の申込への支払いができること")
  @Test
  void testPayToFirstArrivalEntry() throws Exception {

    final Operation insertApplications =
        insertInto("applications")
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 6)
            .column("application_date", LocalDate.of(2019, 9, 11))
            .column("payment_date", null)
            .column("use_points", 0)
            .end()
            .build();

    Destination dest = new DataSourceDestination(dataSource);
    DbSetup dbSetup = new DbSetup(dest, insertApplications);
    dbSetupTracker.launchIfNecessary(dbSetup);

    PaymentRequest request = new PaymentRequest();
    request.setFestivalId(6);
    request.setMemberId(1);
    request.setPaymentDate(LocalDate.of(2019, 9, 21));
    request.setUsePoints(BigDecimal.ZERO);

    final String requestJson = objectMapper.writeValueAsString(request);

    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/payment")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());

    changes.setEndPointNow();

    assertThat(changes)
        .hasNumberOfChanges(1)
        .changeOnTable("applications")
        .isModification()
        .rowAtStartPoint()
        .value("payment_date").isNull()
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(6)
        .value("member_id").isEqualTo(1)
        .value("entry_id").isEqualTo(6)
        .value("application_date").isEqualTo(DateValue.of(2019, 9, 11))
        .value("payment_date").isEqualTo(DateValue.of(2019, 9, 21))
        .value("use_points").isEqualTo(0);
  }

  @DisplayName("ポイント利用ありで抽選枠の申込への支払いができること")
  @Test
  void testPayToLotteryEntryUsePoint() throws Exception {

    final Operation insertApplications =
        insertInto("applications")
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 7)
            .column("application_date", LocalDate.of(2019, 9, 11))
            .column("payment_date", null)
            .column("use_points", 0)
            .end()
            .build();

    final Operation insertLotteryEntryResults =
        insertInto("lottery_entry_results")
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 7)
            .column("lottery_result", "winning")
            .end()
            .build();

    final Operation insertMemberPoints =
        insertInto("member_points")
            .row()
            .column("member_id", 1)
            .column("given_point_date", LocalDate.of(2019, 4, 25))
            .column("given_point", 100)
            .column("used_point", 0)
            .end()
            .build();

    Operation operation = sequenceOf(
        insertApplications,
        insertLotteryEntryResults,
        insertMemberPoints);

    Destination dest = new DataSourceDestination(dataSource);
    DbSetup dbSetup = new DbSetup(dest, operation);
    dbSetupTracker.launchIfNecessary(dbSetup);

    PaymentRequest request = new PaymentRequest();
    request.setFestivalId(6);
    request.setMemberId(1);
    request.setPaymentDate(LocalDate.of(2019, 9, 21));
    request.setUsePoints(BigDecimal.valueOf(50));

    final String requestJson = objectMapper.writeValueAsString(request);

    Changes changes = new Changes(dataSource);
    changes.setStartPointNow();

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/payment")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());

    changes.setEndPointNow();

    assertThat(changes)
        .hasNumberOfChanges(2)
        .changeOnTable("applications")
        .isModification()
        .rowAtStartPoint()
        .value("payment_date").isNull()
        .value("use_points").isEqualTo(0)
        .rowAtEndPoint()
        .value("festival_id").isEqualTo(6)
        .value("member_id").isEqualTo(1)
        .value("entry_id").isEqualTo(7)
        .value("application_date").isEqualTo(DateValue.of(2019, 9, 11))
        .value("payment_date").isEqualTo(DateValue.of(2019, 9, 21))
        .value("use_points").isEqualTo(50)
        .changeOnTable("member_points")
        .isModification()
        .rowAtStartPoint()
        .value("used_point").isEqualTo(BigDecimal.ZERO)
        .rowAtEndPoint()
        .value("member_id").isEqualTo(1)
        .value("given_point_date").isEqualTo(DateValue.of(2019, 4, 25))
        .value("given_point").isEqualTo(100)
        .value("used_point").isEqualTo(50);
  }

  @DisplayName("申込を行っていない大会への入金がエラーになること")
  @Test
  void testErrorByNoApplication() throws Exception {

    dbSetupTracker.skipNextLaunch();

    PaymentRequest request = new PaymentRequest();
    request.setFestivalId(6);
    request.setMemberId(1);
    request.setPaymentDate(LocalDate.of(2019, 9, 21));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/payment")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("当選していない大会への入金がエラーになること")
  @Test
  void testErrorByNoWinning() throws Exception {

    final Operation insertApplications =
        insertInto("applications")
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 8)
            .column("application_date", LocalDate.of(2019, 9, 11))
            .column("payment_date", null)
            .column("use_points", 0)
            .end()
            .build();

    final Operation insertLotteryEntryResults =
        insertInto("lottery_entry_results")
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 8)
            .column("lottery_result", "failed")
            .end()
            .build();

    Operation operation = sequenceOf(
        insertApplications,
        insertLotteryEntryResults);

    Destination dest = new DataSourceDestination(dataSource);
    DbSetup dbSetup = new DbSetup(dest, operation);
    dbSetupTracker.launchIfNecessary(dbSetup);

    PaymentRequest request = new PaymentRequest();
    request.setFestivalId(6);
    request.setMemberId(1);
    request.setPaymentDate(LocalDate.of(2019, 9, 21));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/payment")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest());
  }

  @DisplayName("申し込んだ抽選にも、多段階抽選にも当選していない大会への入金がえらーになること")
  @Test
  void testErrorByNoWinning2() throws Exception {

    final Operation insertApplications =
        insertInto("applications")
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 7)
            .column("application_date", LocalDate.of(2019, 9, 11))
            .column("payment_date", null)
            .column("use_points", 0)
            .end()
            .build();

    final Operation insertLotteryEntryResults =
        insertInto("lottery_entry_results")
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 7)
            .column("lottery_result", "failed")
            .end()
            .row()
            .column("festival_id", 6)
            .column("member_id", 1)
            .column("entry_id", 8)
            .column("lottery_result", "failed")
            .end()
            .build();

    Operation operation = sequenceOf(
        insertApplications,
        insertLotteryEntryResults);

    Destination dest = new DataSourceDestination(dataSource);
    DbSetup dbSetup = new DbSetup(dest, operation);
    dbSetupTracker.launchIfNecessary(dbSetup);

    PaymentRequest request = new PaymentRequest();
    request.setFestivalId(6);
    request.setMemberId(1);
    request.setPaymentDate(LocalDate.of(2019, 9, 21));

    final String requestJson = objectMapper.writeValueAsString(request);

    mockMvc.perform(MockMvcRequestBuilders.post("/applications/payment")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isBadRequest());
  }
}
