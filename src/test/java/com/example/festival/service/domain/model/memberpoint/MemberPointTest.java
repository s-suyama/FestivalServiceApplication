package com.example.festival.service.domain.model.memberpoint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.PointAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberPointTest {

  @DisplayName("ポイントを使用することができること")
  @Test
  void testUseSuccess1() {

    MemberPoint memberPoint = fixture();
    memberPoint.use(new PointAmount(BigDecimal.valueOf(3)));

    assertAll("memberPoint",
        () -> assertEquals(Integer.valueOf(1), memberPoint.memberId().value()),
        () -> assertEquals(LocalDate.of(2019, 4, 1), memberPoint.givenPointDate()),
        () -> assertEquals(BigDecimal.valueOf(10), memberPoint.givenPoint().value()),
        () -> assertEquals(BigDecimal.valueOf(7), memberPoint.usedPoint().value())
    );
  }

  @DisplayName("利用可能ポイントをすべて使用することができること")
  @Test
  void testUseSuccess2() {

    MemberPoint memberPoint = fixture();
    memberPoint.use(new PointAmount(BigDecimal.valueOf(6)));

    assertAll("memberPoint",
        () -> assertEquals(Integer.valueOf(1), memberPoint.memberId().value()),
        () -> assertEquals(LocalDate.of(2019, 4, 1), memberPoint.givenPointDate()),
        () -> assertEquals(BigDecimal.valueOf(10), memberPoint.givenPoint().value()),
        () -> assertEquals(BigDecimal.valueOf(10), memberPoint.usedPoint().value())
    );
  }

  @DisplayName("利用可能ポイントより多いポイントを使用するとエラーになること")
  @Test
  void testUseError() {

    MemberPoint memberPoint = fixture();
    assertThrows(IllegalArgumentException.class,
        () -> memberPoint.use(new PointAmount(BigDecimal.valueOf(7))));
  }

  @DisplayName("利用可能ポイント数を正しく取得できること")
  @Test
  void testAvailablePoint1() {

    MemberPoint memberPoint = fixture();
    BigDecimal result = memberPoint.availablePoint(LocalDate.of(2019, 5, 1)).value();
    assertThat(result, is(BigDecimal.valueOf(6)));
  }

  @DisplayName("有効期限を過ぎている場合、利用可能ポイント数がゼロであること")
  @Test
  void testAvailablePoint2() {

    MemberPoint memberPoint = fixture2();
    BigDecimal result = memberPoint.availablePoint(LocalDate.of(2019, 5, 1)).value();
    assertThat(result, is(BigDecimal.ZERO));
  }

  @DisplayName("ポイントをすべて使い切っている場合、利用可能ポイント数がゼロであること")
  @Test
  void testAvailablePoint3() {

    MemberPoint memberPoint = fixture3();
    BigDecimal result = memberPoint.availablePoint(LocalDate.of(2019, 5, 1)).value();
    assertThat(result, is(BigDecimal.ZERO));
  }

  private MemberPoint fixture() {

    return new MemberPoint(
        new MemberId(1),
        LocalDate.of(2019, 4, 1),
        new PointAmount(BigDecimal.valueOf(10)),
        new PointAmount(BigDecimal.valueOf(4))
    );
  }

  private MemberPoint fixture2() {
    return new MemberPoint(
        new MemberId(1),
        LocalDate.of(2018, 4, 1),
        new PointAmount(BigDecimal.valueOf(10)),
        new PointAmount(BigDecimal.valueOf(4))
    );
  }

  private MemberPoint fixture3() {
    return new MemberPoint(
        new MemberId(1),
        LocalDate.of(2019, 3, 30),
        new PointAmount(BigDecimal.valueOf(10)),
        new PointAmount(BigDecimal.valueOf(10))
    );
  }
}
