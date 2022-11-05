package org.chun.hsytmp.plugin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class JsonPlugin {
  Map<String, Map<String, String>> sqlFile;
}
