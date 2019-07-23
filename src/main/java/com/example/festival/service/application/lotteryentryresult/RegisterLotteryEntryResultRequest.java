package com.example.festival.service.application.lotteryentryresult;

import com.example.festival.service.domain.model.entry.EntryId;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.lotteryentryresult.LotteryResult;
import com.example.festival.service.domain.model.member.MemberId;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class RegisterLotteryEntryResultRequest {

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
  @NotNull(message = "抽選結果は必須です")
  private LotteryResult lotteryResult;

  public FestivalId festivalId() {
    return new FestivalId(festivalId);
  }

  public MemberId memberId() {
    return new MemberId(memberId);
  }

  public EntryId entryId() {
    return new EntryId(entryId);
  }
}
