package com.example.festival.service.infrastructure.entry;

import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.entry.EntryRepository;
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

    return entryMapper.selectEntry(festivalId, entryId);
  }

  @Override
  public void changeEntry(Entry entry) {

    entryMapper.updateEntry(entry);
  }
}
