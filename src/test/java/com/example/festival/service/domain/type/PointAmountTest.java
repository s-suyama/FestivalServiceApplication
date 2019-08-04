package com.example.festival.service.domain.type;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PointAmountTest {

  @DisplayName("コンストラクタでのオブジェクト生成が行えること")
  @Test
  void testConstructorSuccess() {

    PointAmount result = new PointAmount(BigDecimal.valueOf(100));
    assertThat(result.value(), is(BigDecimal.valueOf(100)));
  }

  @DisplayName("ZEROでPointAmountを生成できること")
  @Test
  void testConstructorSuccessByZero() {

    PointAmount result = new PointAmount(BigDecimal.ZERO);
    assertThat(result.value(), is(BigDecimal.ZERO));
  }

  @DisplayName("nullでPointAmountを生成できないこと")
  @Test
  void constructorError1() {

    assertThrows(IllegalArgumentException.class, () ->
        new PointAmount(null));
  }

  @DisplayName("負の値でPointAmountを生成できないこと")
  @Test
  void constructorError2() {

    assertThrows(IllegalArgumentException.class, () ->
        new PointAmount(BigDecimal.valueOf(-1)));
  }

  @DisplayName("少数でPointAmountを生成できないこと")
  @Test
  void constructorError3() {

    assertThrows(IllegalArgumentException.class, () ->
        new PointAmount(BigDecimal.valueOf(0.1)));
  }
}
