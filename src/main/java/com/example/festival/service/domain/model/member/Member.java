package com.example.festival.service.domain.model.member;

import com.example.festival.service.domain.Entity;
import com.example.festival.service.domain.type.Address;
import com.example.festival.service.domain.type.Email;
import com.example.festival.service.domain.type.PhoneNumber;

/**
 * 会員.
 */
public class Member implements Entity {

  MemberId memberId;

  String memberName;

  Sex sex;

  Birthday birthday;

  Address address;

  PhoneNumber phoneNumber;

  Email email;

  private Member() {
  }

  public MemberId memberId() {
    return memberId;
  }
}
