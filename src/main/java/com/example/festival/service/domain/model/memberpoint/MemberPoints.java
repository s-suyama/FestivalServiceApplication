package com.example.festival.service.domain.model.memberpoint;

import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.type.PointAmount;
import com.example.festival.service.support.BusinessErrorException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MemberPoints {

  private List<MemberPoint> list;

  /**
   * Constructor.
   */
  public MemberPoints(List<MemberPoint> list) {

    this.list = new ArrayList<>(list);
    this.list.sort(Comparator.comparing(MemberPoint::givenPointDate));
  }

  public List<MemberPoint> list() {
    return list;
  }

  /**
   * 引数のポイントが使えるかどうかを判定し、有効期限の近いポイントから使用する.
   * なお、保持する MemberPoint オブジェクトの状態を変更する。
   */
  public void usePoints(LocalDate paymentDate, PointAmount usePointAmount) {

    PointAmount pointBalance = pointBalance(paymentDate);
    if (pointBalance.value().compareTo(usePointAmount.value()) < 0) {
      throw new BusinessErrorException("ポイント数が不足しています");
    }

    // この値がゼロになるまでこれまで付与されたポイントからポイントを使用していく
    BigDecimal x = usePointAmount.value();

    for (MemberPoint memberPoint : list) {

      // 有効期限のチェック
      if (memberPoint.hasPassedExpirationDate(paymentDate)) {
        continue;
      }

      // ポイント残高のチェック
      PointAmount availablePoint = memberPoint.availablePoint(paymentDate);
      if (!availablePoint.isPositive()) {
        continue;
      }

      if (availablePoint.value().compareTo(x) <= 0) {
        memberPoint.use(availablePoint);
        x = x.subtract(availablePoint.value());
      } else {
        memberPoint.use(new PointAmount(x));
        x = BigDecimal.ZERO;
        break;
      }
    }
  }

  /**
   * MemberId を返す.
   */
  public MemberId memberId() {

    MemberPoint memberPoint = list.get(0);
    return memberPoint.memberId();
  }

  public int size() {
    return list.size();
  }

  private PointAmount pointBalance(LocalDate paymentDate) {

    PointAmount result = new PointAmount(BigDecimal.ZERO);

    for (MemberPoint memberPoint : list) {
      PointAmount availablePoint = memberPoint.availablePoint(paymentDate);
      result = result.add(availablePoint);
    }

    return result;
  }
}
