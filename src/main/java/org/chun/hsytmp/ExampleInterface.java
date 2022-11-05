package org.chun.hsytmp;

import org.chun.hsytmp.entity.TempUser;

import java.util.List;

public interface ExampleInterface {

  TempUser findByUserNum(Long userNum) throws Exception;

  List<TempUser> findAllByUserGender(String userGender) throws Exception;

  boolean isMemberExists(String userName, String userGender) throws Exception;

}
