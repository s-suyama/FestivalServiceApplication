package com.example.festival.service.infrastructure.application;

import com.example.festival.service.domain.model.application.Application;
import com.example.festival.service.domain.model.application.ApplicationRepository;
import com.example.festival.service.domain.model.application.FestivalApplicationPolicy;
import com.example.festival.service.domain.model.festival.FestivalId;
import com.example.festival.service.domain.model.member.MemberId;
import java.util.ArrayList;
import java.util.List;
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
  public FestivalApplicationPolicy createFestivalApplicationPolicy(
      FestivalId festivalId, MemberId memberId) {

    Application application = applicationMapper.selectApplication(festivalId, memberId);

    if (application == null) {
      return new FestivalApplicationPolicy(new ArrayList<>());
    }

    List<Application> applicationList = new ArrayList<>();
    applicationList.add(application);

    return new FestivalApplicationPolicy(applicationList);
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
