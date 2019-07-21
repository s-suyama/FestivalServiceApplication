package com.example.festival.service.infrastructure.entry;

import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.entry.EntryRepository;
import com.example.festival.service.domain.model.entry.FirstArrivalEntry;
import com.example.festival.service.domain.model.entry.FirstArrivalLotteryType;
import com.example.festival.service.domain.model.entry.LotteryEntry;
import com.example.festival.service.domain.model.festival.FestivalId;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisEntryRepository implements EntryRepository {

  private final EntryMapper entryMapper;

  /**
   * Constructor.
   */
  public MybatisEntryRepository(EntryMapper entryMapper) {
    this.entryMapper = entryMapper;
  }

  @Override
  public Entry findEntry(FestivalId festivalId, EntryId entryId) {

    EntryDto dto = entryMapper.selectEntry(festivalId, entryId);

    if (dto == null) {
      return null;
    }

    if (dto.firstArrivalLotteryType == FirstArrivalLotteryType.firstArrival) {

      return new FirstArrivalEntry(
          dto.festivalId,
          dto.entryId,
          dto.entryName,
          dto.entryDescription,
          dto.eventCode,
          dto.capacity,
          dto.participationFees,
          dto.applicationNumbers,
          dto.applicationStartDate,
          dto.applicationEndDate,
          dto.entryStatus);
    } else {

      return new LotteryEntry(
          dto.festivalId,
          dto.entryId,
          dto.entryName,
          dto.entryDescription,
          dto.eventCode,
          dto.capacity,
          dto.participationFees,
          dto.applicationNumbers,
          dto.applicationStartDate,
          dto.applicationEndDate,
          dto.entryStatus,
          dto.lotteryDate,
          dto.followingEntryId);
    }
  }

  @Override
  public void saveEntry(Entry entry) {

    entryMapper.updateEntry(entry);
  }
}
