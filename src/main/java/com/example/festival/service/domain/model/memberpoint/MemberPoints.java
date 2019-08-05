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
  public void usePoints(LocalDate paymentDate, PointAmount pointAmount) {

    // この値がゼロになるまでこれまで付与されたポイントからポイントを使用していく
    BigDecimal x = pointAmount.value();

    for (MemberPoint memberPoint : list) {

      // 有効期限のチェック
      LocalDate expirationDate = memberPoint.givenPointDate().plusYears(1);
      if (paymentDate.compareTo(expirationDate) > 0) {
        continue;
      }

      // ポイント残高のチェック
      BigDecimal availableUsePoint = memberPoint.givenPoint().value()
          .subtract(memberPoint.usedPoint().value());
      if (availableUsePoint.compareTo(BigDecimal.ZERO) == 0) {
        continue;
      }

      if (availableUsePoint.compareTo(x) <= 0) {
        memberPoint.use(availableUsePoint);
        x = x.subtract(availableUsePoint);
      } else {
        memberPoint.use(x);
        x = BigDecimal.ZERO;
        break;
      }
    }

    // 有効期限の近いものからポイントを使用していき、利用したいポイントに満たなかった場合エラー
    if (x.compareTo(BigDecimal.ZERO) > 0) {
      throw new BusinessErrorException("ポイント数が不足しています");
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
}
