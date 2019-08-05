package com.example.festival.service.domain.model.memberpoint;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.PointAmount;
import com.example.festival.service.support.BusinessErrorException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberPointsTest {

  @DisplayName("複数のポイントから有効期限が近い順に正しく使用できること")
  @Test
  void test() {

    List<MemberPoint> memberPointList = new ArrayList<>();

    memberPointList.add(
        fixture(1, LocalDate.of(2019, 4, 1), BigDecimal.valueOf(10), BigDecimal.ZERO)
    );
    memberPointList.add(
        fixture(1, LocalDate.of(2019, 4, 3), BigDecimal.valueOf(20), BigDecimal.ZERO)
    );
    memberPointList.add(
        fixture(1, LocalDate.of(2019, 4, 2), BigDecimal.valueOf(30), BigDecimal.ZERO)
    );

    MemberPoints memberPoints = new MemberPoints(memberPointList);
    memberPoints.usePoints(LocalDate.of(2019, 5, 1), new PointAmount(BigDecimal.valueOf(45)));

    List<MemberPoint> afterUseMemberPointList = memberPoints.list();

    assertAll("memberPoints",
        () -> assertEquals(BigDecimal.valueOf(10),
            afterUseMemberPointList.get(0).usedPoint().value()),
        () -> assertEquals(BigDecimal.valueOf(30),
            afterUseMemberPointList.get(1).usedPoint().value()),
        () -> assertEquals(BigDecimal.valueOf(5),
            afterUseMemberPointList.get(2).usedPoint().value())
    );
  }

  @DisplayName("有効期限が切れているポイントは使用できないこと")
  @Test
  void test2() {

    List<MemberPoint> memberPointList = new ArrayList<>();

    memberPointList.add(
        fixture(1, LocalDate.of(2018, 3, 31), BigDecimal.valueOf(10), BigDecimal.ZERO)
    );
    memberPointList.add(
        fixture(1, LocalDate.of(2018, 4, 1), BigDecimal.valueOf(20), BigDecimal.ZERO)
    );
    memberPointList.add(
        fixture(1, LocalDate.of(2018, 4, 2), BigDecimal.valueOf(30), BigDecimal.ZERO)
    );

    MemberPoints memberPoints = new MemberPoints(memberPointList);
    memberPoints.usePoints(LocalDate.of(2019, 4, 2), new PointAmount(BigDecimal.valueOf(30)));

    List<MemberPoint> afterUseMemberPointList = memberPoints.list();

    assertAll("memberPoints",
        () -> assertEquals(BigDecimal.ZERO,
            afterUseMemberPointList.get(0).usedPoint().value()),
        () -> assertEquals(BigDecimal.ZERO,
            afterUseMemberPointList.get(1).usedPoint().value()),
        () -> assertEquals(BigDecimal.valueOf(30),
            afterUseMemberPointList.get(2).usedPoint().value())
    );
  }

  @DisplayName("ポイントが不足している場合エラーになること")
  @Test
  void testError1() {

    List<MemberPoint> memberPointList = new ArrayList<>();

    memberPointList.add(
        fixture(1, LocalDate.of(2018, 3, 31), BigDecimal.valueOf(10), BigDecimal.ZERO)
    );
    memberPointList.add(
        fixture(1, LocalDate.of(2018, 4, 1), BigDecimal.valueOf(20), BigDecimal.ZERO)
    );
    memberPointList.add(
        fixture(1, LocalDate.of(2018, 4, 2), BigDecimal.valueOf(30), BigDecimal.ZERO)
    );

    MemberPoints memberPoints = new MemberPoints(memberPointList);

    assertThrows(BusinessErrorException.class, () ->
        memberPoints.usePoints(LocalDate.of(2018, 10, 1), new PointAmount(BigDecimal.valueOf(61))));
  }

  private MemberPoint fixture(
      int memberId,
      LocalDate givenPointDate,
      BigDecimal givenPoint,
      BigDecimal usedPoint) {

    return new MemberPoint(
        new MemberId(memberId),
        givenPointDate,
        new PointAmount(givenPoint),
        new PointAmount(usedPoint)
    );
  }
}
