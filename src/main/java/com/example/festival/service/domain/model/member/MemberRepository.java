package com.example.festival.service.domain.model.member;

public interface MemberRepository {

  Member findMember(MemberId memberId);
}
