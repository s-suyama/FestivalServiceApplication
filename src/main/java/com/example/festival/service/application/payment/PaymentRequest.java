package com.example.festival.service.application.payment;

import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class PaymentRequest {

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
  @NotNull(message = "入金年月日は必須です")
  @JsonFormat(pattern = "yyyy/MM/dd")
  private LocalDate paymentDate;

  @Getter
  @Setter
  @Min(value = 0, message = "使用ポイントはゼロ以上を指定してください")
  private BigDecimal usePoints;

  FestivalId festivalId() {
    return new FestivalId(festivalId);
  }

  MemberId memberId() {
    return new MemberId(memberId);
  }
}
