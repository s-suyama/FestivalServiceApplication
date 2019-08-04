package com.example.festival.service.infrastructure.memberpoint;

import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.model.memberpoint.MemberPoint;
import com.example.festival.service.domain.model.memberpoint.MemberPointRepository;
import com.example.festival.service.domain.model.memberpoint.MemberPoints;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisMemberPointRepository implements MemberPointRepository {

  private final MemberPointMapper memberPointMapper;

  public MybatisMemberPointRepository(MemberPointMapper memberPointMapper) {
    this.memberPointMapper = memberPointMapper;
  }

  @Override
  public MemberPoints findMemberPoints(MemberId memberId) {

    List<MemberPoint> memberPointList = memberPointMapper.selectMemberPointList(memberId);
    return new MemberPoints(memberPointList);
  }

  @Override
  public void saveMemberPoints(MemberPoints memberPoints) {

    int cnt = memberPointMapper.selectCount(memberPoints.memberId());
    if (cnt > 0) {
      int delCnt = memberPointMapper.deleteMemberPoints(memberPoints.memberId());
      if (delCnt != memberPoints.size()) {
        throw new IllegalStateException("member_points への delete が失敗しました");
      }
    }

    for (MemberPoint memberPoint : memberPoints.list()) {
      int insCnt = memberPointMapper.insertMemberPoint(memberPoint);
      if (insCnt != 1) {
        throw new IllegalStateException("member_point への insert が失敗しました");
      }
    }
  }
}
