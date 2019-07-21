package com.example.festival.service.domain.model.entry;

import com.example.festival.service.domain.model.festival.FestivalId;

public interface EntryRepository {

  Entry findEntry(FestivalId festivalId, EntryId entryId);

  void saveEntry(Entry entry);
}
