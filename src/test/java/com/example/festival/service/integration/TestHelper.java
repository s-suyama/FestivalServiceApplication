package com.example.festival.service.integration;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sql;

import com.ninja_squad.dbsetup.operation.Operation;

public class TestHelper {

  public static final Operation deleteAll = deleteAllFrom(
      "member_points",
      "lottery_entry_results",
      "applications",
      "members",
      "lottery_entries",
      "entries",
      "festivals_items",
      "festivals_conduct_services",
      "festivals",
      "items",
      "conduct_services",
      "events",
      "event_types",
      "sponsors"
  );

  public static final Operation resetSequences = sql(
      "ALTER SEQUENCE sponsor_id RESTART WITH 1",
      "ALTER SEQUENCE event_code RESTART WITH 1",
      "ALTER SEQUENCE conduct_service_code RESTART WITH 1",
      "ALTER SEQUENCE item_code RESTART WITH 1",
      "ALTER SEQUENCE festival_id RESTART WITH 1",
      "ALTER SEQUENCE entry_id RESTART WITH 1",
      "ALTER SEQUENCE member_id RESTART WITH 1"
  );

  public static final Operation insertSponsors =
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

  public static final Operation insertEventTypes =
      insertInto("event_types")
          .columns("event_type_code", "event_type_name")
          .values("R01", "ランニング")
          .values("B01", "自転車レース")
          .build();

  public static final Operation insertEvents =
      insertInto("events")
          .columns("event_code", "event_name", "event_type_code")
          .values(1, "フルマラソン", "R01")
          .build();
}
