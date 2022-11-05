package org.chun.hsytmp.custom;

import lombok.RequiredArgsConstructor;
import org.chun.hsytmp.ExampleInterface;
import org.chun.hsytmp.entity.TempUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class TempUserCustom implements ExampleInterface {
  private final EntityManager entityManager;

  @Override
  public TempUser findByUserNum(Long userNum) throws Exception {
    String sql = """
          SELECT
              USER_NUM,
              USER_NAME,
              USER_GENDER,
              USER_MOBILE
          FROM TEMP_USER
          WHERE USER_NUM = :userNum
        """;
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
    String sql = """
          SELECT
              USER_NUM,
              USER_NAME,
              USER_GENDER,
              USER_MOBILE
          FROM TEMP_USER
          WHERE USER_GENDER = :userGender
        """;
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
    String sql = """
          SELECT TRUE
          FROM TEMP_USER
          WHERE USER_NAME = :userName
            AND USER_GENDER = :userGender
        """;
    Query sqlQuery = entityManager.createNativeQuery(sql);
    sqlQuery.setParameter("userName", userName);
    sqlQuery.setParameter("userGender", userGender);
    Object result = sqlQuery.getSingleResult();
    return result != null;
  }
}
