package org.chun.hsytmp.plugin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.hsytmp.ExampleInterface;
import org.chun.hsytmp.entity.TempUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class JsonPluginSearcher implements ExampleInterface {
  private final EntityManager entityManager;
  private final JsonPlugin jsonPlugin;

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
    List results = sqlQuery.getResultList();
    return (List<TempUser>) results.stream()
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

  private String getSqlCommand(String key) {
    Map<String, String> sqlCluster = jsonPlugin.getSqlFile().get("tempUser");
    final String command = sqlCluster.get(key);
    return command;
  }
}
