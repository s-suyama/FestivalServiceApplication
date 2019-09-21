package com.example.festival.service.domain.model.application;

import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import java.util.ArrayList;
import java.util.List;

public class FestivalApplicationPolicy {

  private List<Application> applicationList;

  public FestivalApplicationPolicy(List<Application> applicationList) {
    this.applicationList = new ArrayList<>(applicationList);
  }

  boolean hasAlreadyApplyForSameFestival(MemberId memberId, FestivalId festivalId) {

    for (Application application : applicationList) {
      if (application.festivalId().equals(festivalId)
          && application.memberId().equals(memberId)) {
        return true;
      }
    }

    return false;
  }
}
