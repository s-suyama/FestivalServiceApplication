package com.example.festival.service.infrastructure.application;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApplicationMapper {

  Application selectApplication(
      @Param("festivalId") FestivalId festivalId,
      @Param("memberId") MemberId memberId);

  int insertApplication(Application application);

  int updateApplication(Application application);
}
