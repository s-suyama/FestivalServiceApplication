package com.example.festival.service.integration;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.Operations.sql;

import com.ninja_squad.dbsetup.operation.Operation;

public class TestHelper {

  public static final Operation deleteAll = deleteAllFrom(
      "member_points",
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

  public static Operation resetSequences = sql(
      "ALTER SEQUENCE sponsor_id RESTART WITH 1",
      "ALTER SEQUENCE event_code RESTART WITH 1",
      "ALTER SEQUENCE conduct_service_code RESTART WITH 1",
      "ALTER SEQUENCE item_code RESTART WITH 1",
      "ALTER SEQUENCE festival_id RESTART WITH 1",
      "ALTER SEQUENCE entry_id RESTART WITH 1",
      "ALTER SEQUENCE member_id RESTART WITH 1"
  );
}
