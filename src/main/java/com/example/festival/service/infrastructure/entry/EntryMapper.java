package com.example.festival.service.infrastructure.entry;

import com.example.festival.service.domain.model.entry.Entry;
import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EntryMapper {

  Entry selectEntry(
      @Param("festivalId") FestivalId festivalId,
      @Param("entryId") EntryId entryId);

  void updateEntry(Entry entry);
}
