package org.chun.hsytmp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TempUser {
  @Id
  @Column(name = "user_num", nullable = false)
  private Long userNum;
  @Column(name = "user_name", nullable = false)
  private String userName;
  @Column(name = "user_gender", nullable = false)
  private String userGender;
  @Column(name = "user_mobile")
  private String userMobile;
}
