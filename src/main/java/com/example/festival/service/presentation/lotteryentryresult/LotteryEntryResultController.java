package com.example.festival.service.presentation.lotteryentryresult;

import com.example.festival.service.application.lotteryentryresult.LotteryEntryResultCommandService;
import com.example.festival.service.application.lotteryentryresult.RegisterLotteryEntryResultRequest;
import com.example.festival.service.support.ValidationErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LotteryEntryResultController {

  private final LotteryEntryResultCommandService commandService;

  public LotteryEntryResultController(LotteryEntryResultCommandService commandService) {
    this.commandService = commandService;
  }

  /**
   * 抽選結果を登録する.
   */
  @PostMapping(value = "/lottery-entry-result",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> registerLotteryEntryResult(
      @RequestBody @Validated RegisterLotteryEntryResultRequest request, Errors errors) {

    if (errors.hasErrors()) {
      throw new ValidationErrorException(errors);
    }

    commandService.registerLotteryEntryResult(request);

    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
