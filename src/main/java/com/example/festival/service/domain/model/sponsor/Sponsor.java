package com.example.festival.service.domain.model.sponsor;

/**
 * 主催者 Entity.
 */
public class Sponsor {

  SponsorId sponsorId;

  String sponsorName;

  String representativeName;

  Address address;

  PhoneNumber phoneNumber;

  Email email;

  /**
   * All argument constructor.
   */
  public Sponsor(
      SponsorId sponsorId,
      String sponsorName,
      String representativeName,
      Address address,
      PhoneNumber phoneNumber,
      Email email) {

    this.sponsorId = sponsorId;
    this.sponsorName = sponsorName;
    this.representativeName = representativeName;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  private Sponsor() {
  }

  public SponsorId sponsorId() {
    return sponsorId;
  }

  public String sponsorName() {
    return sponsorName;
  }

  public String representativeName() {
    return representativeName;
  }

  public Address address() {
    return address;
  }

  public PhoneNumber phoneNumber() {
    return phoneNumber;
  }

  public Email email() {
    return email;
  }
}
