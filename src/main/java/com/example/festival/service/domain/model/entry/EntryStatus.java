package com.example.festival.service.domain.model.entry;

/**
 * エントリ枠状態.
 */
public enum EntryStatus {
  /** 募集前. */
  beforeRecruitment,
  /** 募集中. */
  recruiting,
  /** 抽選中. */
  underLottery,
  /** 参加者確定. */
  participantConfirmation
}
