package com.wikia.webdriver.common.core.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.wikia.webdriver.common.properties.Credentials;

/**
 * Configuration handler. This Class should handle run configuration and global properties.
 * Configuration handling:
 * 1. If there is a System Property specified - return value of this property
 * 2. If no System Property is found - value is provided from configuration files (config_sample.yml if no config.yml provided)
 * NOTE: system property should override ANY property specified in config files
 */
public class Configuration {

  private static Map<String, String> config;

  private static Map<String, String> readConfiguration() {
    if (config == null) {
      Yaml yaml = new Yaml();
      InputStream input = null;
      try {
        input = new FileInputStream(new File("config.yml"));
      } catch (FileNotFoundException ex) {
        try {
          input = new FileInputStream(new File("config_sample.yml"));
        } catch (FileNotFoundException ex2) {
          System.out.println("CAN'T LOCATE CONFIG FILE");
        }
      }
      config = (Map<String, String>) yaml.load(input);
    }
    return config;
  }

  private static String getProp(String propertyName) {
    String value =
        System.getProperty(propertyName) != null ? System.getProperty(propertyName) : String
            .valueOf(readConfiguration().get(propertyName));
    return value;
  }

  public static String getBrowser() {
    return getProp("browser");
  }

  public static String getEnv() {
    return getProp("env");
  }

  public static String getWikiName() {
    return getProp("wikiName");
  }

  public static String getPlatformVersion() {
    return getProp("platformVersion");
  }

  public static String getPlatform() {
    return getProp("platform");
  }

  public static String getDeviceId() {
    return getProp("deviceId");
  }

  public static String geMobileConfig() {
    return getProp("mobileConfig");
  }

  public static String getCredentialsFilePath() {
    return getProp("credentialsPath");
  }

  public static String getQS() {
    return getProp("qs");
  }

  public static String getAppiumIp() {
    return getProp("appiumIp");
  }

  public static String getDeviceName() {
    return getProp("deviceName");
  }

  public static String getDisableFlash() {
    return getProp("disableFlash");
  }

  public static Credentials getCredentials() {
    return new Credentials(new File(getCredentialsFilePath()));
  }

  public static String getEnvType() {
    if (getEnv().contains("prod")) {
      return "prod";
    } else if (getEnv().contains("preview")) {
      return "preview";
    } else if (getEnv().contains("sandbox")) {
      return "sandbox";
    } else if (getEnv().contains("dev")) {
      return "dev";
    }
    return "";
  }
}
