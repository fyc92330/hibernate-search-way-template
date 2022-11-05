package org.chun.hsytmp.repository;

import org.chun.hsytmp.entity.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TempUserRepository extends JpaRepository<TempUser, Long> {

  TempUser findByUserNum(Long userNum);

  List<TempUser> findAllByUserGender(String userGender);

  @Query(
      value = """
            SELECT TRUE
            FROM TEMP_USER
            WHERE USER_NAME = :userName
              AND USER_GENDER = :userGender
          """,
      nativeQuery = true
  )
  boolean isMemberExists(@Param("userName") String userName, @Param("userGender") String userGender);
}
