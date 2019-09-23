package com.example.festival.service.domain.model.memberpoint;

import com.example.festival.service.domain.Entity;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.PointAmount;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MemberPoint implements Entity {

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
  void use(PointAmount usePoint) {
    BigDecimal totalUsedPointAmountValue = usedPoint.value().add(usePoint.value());

    if (totalUsedPointAmountValue.compareTo(givenPoint.value()) > 0) {
      throw new IllegalArgumentException("付与ポイントより使用済ポイント数が多くなっています");
    }

    usedPoint = new PointAmount(totalUsedPointAmountValue);
  }

  /**
   * 引数で指定した対象日で利用可能なポイント数を返す.
   */
  PointAmount availablePoint(LocalDate targetDate) {

    if (targetDate.compareTo(expirationDate()) > 0) {
      return new PointAmount(BigDecimal.ZERO);
    }

    BigDecimal result = givenPoint.value().subtract(usedPoint.value());
    return new PointAmount(result);
  }

  /**
   * 有効期限を返す.
   */
  LocalDate expirationDate() {
    return givenPointDate.plusYears(1);
  }

  /**
   * 有効期限を過ぎていないかどうかを返す.有効期限を過ぎている場合、trueを返す.
   */
  boolean hasPassedExpirationDate(LocalDate paymentDate) {

    return paymentDate.compareTo(expirationDate()) > 0;
  }
}
