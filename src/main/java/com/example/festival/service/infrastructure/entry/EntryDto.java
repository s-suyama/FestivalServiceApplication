package com.example.festival.service.infrastructure.entry;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.entry.EntryStatus;
import com.example.festival.service.domain.model.entry.FirstArrivalLotteryType;
import com.example.festival.service.domain.model.event.EventCode;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.type.Amount;
import com.example.festival.service.domain.type.NumberOfPeople;

import java.time.LocalDate;

public class EntryDto {

  FestivalId festivalId;

  EntryId entryId;

  String entryName;

  String entryDescription;

  EventCode eventCode;

  NumberOfPeople capacity;

  Amount participationFees;

  NumberOfPeople applicationNumbers;

  FirstArrivalLotteryType firstArrivalLotteryType;

  LocalDate applicationStartDate;

  LocalDate applicationEndDate;

  EntryStatus entryStatus;

  LocalDate lotteryDate;

  EntryId followingEntryId;

  private EntryDto() {
  }
}
