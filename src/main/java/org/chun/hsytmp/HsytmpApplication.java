package org.chun.hsytmp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chun.hsytmp.config.TestDataProperties;
import org.chun.hsytmp.custom.TempUserCustom;
import org.chun.hsytmp.plugin.JsonPluginSearcher;
import org.chun.hsytmp.plugin.SqlPluginSearcher;
import org.chun.hsytmp.repository.TempUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class HsytmpApplication implements CommandLineRunner {

  private final ConfigurableApplicationContext application;
  private final TestDataProperties testDataProperties;
  private final TempUserRepository repository;
  private final TempUserCustom custom;

  private final SqlPluginSearcher sql;
  private final JsonPluginSearcher json;

  public static void main(String[] args) {
    SpringApplication.run(HsytmpApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    try (Scanner scanner = new Scanner(System.in)) {
      do {
        System.out.print("執行 (1)Repository, (2)EntityManager, (3)SQL, (4)Json: ");
        switch (scanner.nextLine()) {
          case "1" -> doRepository();
          case "2" -> doEntityManager();
          case "3" -> doSql();
          case "4" -> doJson();
        }

        System.out.print("是否結束程式?(Y/N) ");
      } while (!"Y".equals(scanner.nextLine()));
    }
    System.out.println("程式已結束.");
    SpringApplication.exit(application);
  }

  private void doRepository() {
    log.info("使用 Repository 進行測試.");
    log.info("pk 取得資料:{}", repository.findByUserNum(testDataProperties.getUserNum()));
    final String userGender = testDataProperties.getUserGender();
    log.info("取得同性別資料:{}", repository.findAllByUserGender(userGender));
    log.info("pk 此用戶是否存在:{}", repository.isMemberExists(testDataProperties.getUserName(), userGender));
  }

  private void doEntityManager() throws Exception {
    log.info("使用 EntityManager 進行測試.");
    log.info("pk 取得資料:{}", custom.findByUserNum(testDataProperties.getUserNum()));
    final String userGender = testDataProperties.getUserGender();
    log.info("取得同性別資料:{}", custom.findAllByUserGender(userGender));
    log.info("pk 此用戶是否存在:{}", custom.isMemberExists(testDataProperties.getUserName(), userGender));
  }

  private void doSql() throws Exception {
    log.info("使用 掛載.sql檔案 進行測試.");
    log.info("pk 取得資料:{}", sql.findByUserNum(testDataProperties.getUserNum()));
    final String userGender = testDataProperties.getUserGender();
    log.info("取得同性別資料:{}", sql.findAllByUserGender(userGender));
    log.info("pk 此用戶是否存在:{}", sql.isMemberExists(testDataProperties.getUserName(), userGender));

  }

  private void doJson() throws Exception {
    log.info("使用 掛載.json檔案 進行測試.");
    log.info("pk 取得資料:{}", json.findByUserNum(testDataProperties.getUserNum()));
    final String userGender = testDataProperties.getUserGender();
    log.info("取得同性別資料:{}", json.findAllByUserGender(userGender));
    log.info("pk 此用戶是否存在:{}", json.isMemberExists(testDataProperties.getUserName(), userGender));
  }
}
