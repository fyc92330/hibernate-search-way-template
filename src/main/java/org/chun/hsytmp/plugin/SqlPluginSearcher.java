package org.chun.hsytmp.plugin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.hsytmp.ExampleInterface;
import org.chun.hsytmp.entity.TempUser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SqlPluginSearcher implements ExampleInterface {

  private final EntityManager entityManager;

  @Override
  public TempUser findByUserNum(Long userNum) throws Exception {
    String sql = this.getSqlCommand("findByUserNum");
    Query sqlQuery = entityManager.createNativeQuery(sql);
    sqlQuery.setParameter("userNum", userNum);
    Object[] result = (Object[]) sqlQuery.getSingleResult();
    return TempUser.builder()
        .userNum(((BigInteger) result[0]).longValue())
        .userName(String.valueOf(result[1]))
        .userGender(String.valueOf(result[2]))
        .userMobile(String.valueOf(result[3]))
        .build();
  }

  @Override
  public List<TempUser> findAllByUserGender(String userGender) throws Exception {
    String sql = this.getSqlCommand("findAllByUserGender");
    Query sqlQuery = entityManager.createNativeQuery(sql);
    sqlQuery.setParameter("userGender", userGender);
    return (List<TempUser>) sqlQuery.getResultList().stream()
        .map(r -> {
          Object[] result = (Object[]) r;
          TempUser user = new TempUser();
          user.setUserNum(((BigInteger) result[0]).longValue());
          user.setUserName(String.valueOf(result[1]));
          user.setUserGender(String.valueOf(result[2]));
          user.setUserMobile(String.valueOf(result[3]));
          return user;
        })
        .collect(Collectors.toList());
  }

  @Override
  public boolean isMemberExists(String userName, String userGender) throws Exception {
    String sql = this.getSqlCommand("isMemberExists");
    Query sqlQuery = entityManager.createNativeQuery(sql);
    sqlQuery.setParameter("userName", userName);
    sqlQuery.setParameter("userGender", userGender);
    Object result = sqlQuery.getSingleResult();
    return result != null;
  }

  private String getSqlCommand(String key) throws IOException {
    String filePath = String.format("sql/%s.sql", key);
    Resource resource = new ClassPathResource(filePath);
    StringBuilder sb = new StringBuilder();
    File jsonFile = resource.getFile();
    try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
      String tempStr;
      do {
        tempStr = br.readLine();
        if (tempStr != null) {
          sb.append(tempStr).append(" ");
        }
      } while (tempStr != null);
    }

    return sb.toString();
  }
}
