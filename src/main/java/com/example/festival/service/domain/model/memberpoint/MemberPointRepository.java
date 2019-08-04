package com.example.festival.service.domain.model.memberpoint;

import com.example.festival.service.domain.model.member.MemberId;

public interface MemberPointRepository {

  MemberPoints findMemberPoints(MemberId memberId);

  void saveMemberPoints(MemberPoints memberPoints);
}
