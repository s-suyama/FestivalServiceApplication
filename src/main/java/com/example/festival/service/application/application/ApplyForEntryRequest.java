package com.example.festival.service.application.application;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class ApplyForEntryRequest {

  @Getter
  @Setter
  @NotNull(message = "大会番号は必須です")
  private Integer festivalId;

  @Getter
  @Setter
  @NotNull(message = "会員番号は必須です")
  private Integer memberId;

  @Getter
  @Setter
  @NotNull(message = "エントリ枠番号は必須です")
  private Integer entryId;

  @Getter
  @Setter
  @NotNull(message = "参加申込年月日は必須です")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDate applicationDate;

  FestivalId festivalId() {
    return new FestivalId(festivalId);
  }

  MemberId memberId() {
    return new MemberId(memberId);
  }

  EntryId entryId() {
    return new EntryId(entryId);
  }
}
