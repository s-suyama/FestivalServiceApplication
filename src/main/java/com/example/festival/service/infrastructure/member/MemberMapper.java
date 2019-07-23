package com.example.festival.service.infrastructure.member;

import com.example.festival.service.domain.model.member.Member;
import com.example.festival.service.domain.model.member.MemberId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

  Member selectMember(@Param("memberId") MemberId memberId);
}
