package com.example.festival.service.infrastructure.member;

import com.example.festival.service.domain.model.member.Member;
import com.example.festival.service.domain.model.member.MemberId;
import com.example.festival.service.domain.model.member.MemberRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisMemberRepository implements MemberRepository {

  private final MemberMapper memberMapper;

  public MybatisMemberRepository(MemberMapper memberMapper) {
    this.memberMapper = memberMapper;
  }

  @Override
  public Member findMember(MemberId memberId) {

    return memberMapper.selectMember(memberId);
  }
}
