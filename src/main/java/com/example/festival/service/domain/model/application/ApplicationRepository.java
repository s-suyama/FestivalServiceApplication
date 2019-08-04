package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;

public interface ApplicationRepository {

  Application findApplication(FestivalId festivalId, MemberId memberId);

  void addApplication(Application application);

  void saveApplication(Application application);
}
