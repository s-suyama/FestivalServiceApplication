package com.example.festival.service.presentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class ApiError {

  @Getter
  @Setter
  private String message;

  @Getter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private final List<Detail> details = new ArrayList<>();

  public void addDetail(String target, String message) {
    details.add(new Detail(target, message));
  }

  private static class Detail {

    @Getter
    private final String target;

    @Getter
    private final String message;

    private Detail(String target, String message) {
      this.target = target;
      this.message = message;
    }
  }
}
