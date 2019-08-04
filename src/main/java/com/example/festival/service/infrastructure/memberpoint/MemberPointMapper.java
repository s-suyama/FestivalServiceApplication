package com.example.festival.service.infrastructure.memberpoint;

import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.model.memberpoint.MemberPoint;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberPointMapper {

  List<MemberPoint> selectMemberPointList(@Param("memberId") MemberId memberId);

  int selectCount(@Param("memberId") MemberId memberId);

  int insertMemberPoint(MemberPoint memberPoint);

  int deleteMemberPoints(@Param("memberId") MemberId memberId);
}
