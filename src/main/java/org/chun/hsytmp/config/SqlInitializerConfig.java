package org.chun.hsytmp.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.chun.hsytmp.plugin.JsonPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class SqlInitializerConfig {

  @Bean
  public JsonPlugin jsonPlugin() throws Exception {
    // 讀取jsonFile
    Resource resource = new ClassPathResource("json/tempUser.json");
    File jsonFile = resource.getFile();
    String tempStr;
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {
      do {
        tempStr = br.readLine();
        if (tempStr != null) {
          sb.append(tempStr);
        }
      } while (tempStr != null);
    }

    // 轉換內容格式
    Map<String, String> sqlContent = new ObjectMapper().readValue(sb.toString(), new TypeReference<>() {
    });

    // 加入plugin
    Map<String, Map<String, String>> plugin = new HashMap<>();
    plugin.put(resource.getFilename().split("\\.")[0], sqlContent);
    return new JsonPlugin(plugin);
  }

}
