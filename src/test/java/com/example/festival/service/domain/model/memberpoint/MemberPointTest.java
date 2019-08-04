package com.example.festival.service.domain.model.memberpoint;

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
    memberPoint.use(BigDecimal.valueOf(3));

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
    memberPoint.use(BigDecimal.valueOf(6));

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
    assertThrows(IllegalArgumentException.class, () -> memberPoint.use(BigDecimal.valueOf(7)));
  }

  private MemberPoint fixture() {

    return new MemberPoint(
        new MemberId(1),
        LocalDate.of(2019, 4, 1),
        new PointAmount(BigDecimal.valueOf(10)),
        new PointAmount(BigDecimal.valueOf(4))
    );
  }
}
