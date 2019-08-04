package com.example.festival.service.domain.model.memberpoint;

import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.PointAmount;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MemberPoint {

  private MemberId memberId;

  private LocalDate givenPointDate;

  private PointAmount givenPoint;

  private PointAmount usedPoint;

  private MemberPoint() {
  }

  /**
   * All argument constructor.
   */
  public MemberPoint(
      MemberId memberId,
      LocalDate givenPointDate,
      PointAmount givenPoint,
      PointAmount usedPoint) {

    this.memberId = memberId;
    this.givenPointDate = givenPointDate;
    this.givenPoint = givenPoint;
    this.usedPoint = usedPoint;
  }

  MemberId memberId() {
    return memberId;
  }

  LocalDate givenPointDate() {
    return givenPointDate;
  }

  PointAmount givenPoint() {
    return givenPoint;
  }

  PointAmount usedPoint() {
    return usedPoint;
  }

  /**
   * 引数で使用した値分のポイントを使用する.
   */
  void use(BigDecimal value) {
    BigDecimal totalUsedPointAmountValue = usedPoint.value().add(value);

    if (totalUsedPointAmountValue.compareTo(givenPoint.value()) > 0) {
      throw new IllegalArgumentException("付与ポイントより使用済ポイント数が多くなっています");
    }

    usedPoint = new PointAmount(totalUsedPointAmountValue);
  }
}
