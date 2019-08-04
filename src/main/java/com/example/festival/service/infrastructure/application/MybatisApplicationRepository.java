package com.example.festival.service.infrastructure.application;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.application.ApplicationRepository;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisApplicationRepository implements ApplicationRepository {

  private final ApplicationMapper applicationMapper;

  /**
   * Constructor.
   */
  public MybatisApplicationRepository(ApplicationMapper applicationMapper) {
    this.applicationMapper = applicationMapper;
  }

  @Override
  public Application findApplication(FestivalId festivalId, MemberId memberId) {

    return applicationMapper.selectApplication(festivalId, memberId);
  }

  @Override
  public void addApplication(Application application) {

    int cnt = applicationMapper.insertApplication(application);
    if (cnt != 1) {
      throw new IllegalStateException("applications への insert が失敗しました");
    }
  }

  @Override
  public void saveApplication(Application application) {

    int cnt = applicationMapper.updateApplication(application);
    if (cnt != 1) {
      throw new IllegalStateException("applications への update が失敗しました");
    }
  }
}
