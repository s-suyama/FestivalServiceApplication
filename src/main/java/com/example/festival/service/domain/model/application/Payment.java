package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.Entity;
import com.example.festival.service.domain.type.PointAmount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 入金.
 */
public class Payment implements Entity {

  LocalDate paymentDate;

  PointAmount usePoints;

  private Payment() {
  }

  /**
   * All argument constructor.
   */
  Payment(LocalDate paymentDate, PointAmount usePoints) {

    if (paymentDate == null) {
      throw new IllegalArgumentException("支払日は必須です");
    }

    this.paymentDate = paymentDate;
    this.usePoints =
        Objects.requireNonNullElseGet(usePoints, () -> new PointAmount(BigDecimal.ZERO));
  }

  public LocalDate paymentDate() {
    return paymentDate;
  }

  public PointAmount usePoints() {
    return usePoints;
  }

  /**
   * 未払いのオブジェクトを生成して返す.
   */
  static Payment createUnpaid() {
    
    Payment result = new Payment();
    result.paymentDate = null;
    result.usePoints = new PointAmount(BigDecimal.ZERO);

    return result;
  }
}
