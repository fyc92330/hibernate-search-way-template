package org.chun.hsytmp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "test.data")
public class TestDataProperties {
  private Long userNum;
  private String userName;
  private String userGender;
  private String userMobile;
}
