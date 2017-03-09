package com.example.administrator.cookman.utils.Logger;

import org.json.JSONObject;

/**
 * Logger is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful
 */
public final class Logger {
  public static final int DEBUG = 3;
  public static final int ERROR = 6;
  public static final int ASSERT = 7;
  public static final int INFO = 4;
  public static final int VERBOSE = 2;
  public static final int WARN = 5;

  private static final String DEFAULT_TAG = "PRETTYLOGGER";

  private static Printer printer = new LoggerPrinter();
  private static String Tag = DEFAULT_TAG;

  //no instance
  private Logger() {
  }

  /**
   * It is used to get the settings object in order to change settings
   *
   * @return the settings object
   */
  public static Settings init() {
    return init(DEFAULT_TAG);
  }

  /**
   * It is used to change the tag
   *
   * @param tag is the given string which will be used in Logger as TAG
   */
  public static Settings init(String tag) {
    printer = new LoggerPrinter();
    return printer.init(tag);
  }

  public static void resetSettings() {
    printer.resetSettings();
  }

  public static Printer t(String tag) {
    return printer.t(tag, printer.getSettings().getMethodCount());
  }

  public static Printer t(int methodCount) {
    return printer.t(null, methodCount);
  }

  public static Printer t(String tag, int methodCount) {
    return printer.t(tag, methodCount);
  }

  public static void log(int priority, String tag, String message, Throwable throwable) {
    printer.log(priority, tag, message, throwable);
  }

  public static void d(String message) {
    printer.d(message);
  }

  public static void d(String tag, String message) {
    printer.d2(tag, message);
  }

  public static void d(Object object) {
    printer.d(object);
  }

  public static void e(String message) {
    printer.e(message);
  }

  public static void e(String tag, String message) {
    printer.e2(tag, message);
  }

//  public static void e(Throwable throwable, String message, Object... args) {
//    printer.e(throwable, message, args);
//  }

  public static void i(String message) {
    printer.i(message);
  }

  public static void i(String tag, String message) {
    printer.i2(tag, message);
  }

  public static void v(String tag, String message) {
    printer.v2(tag, message);
  }

  public static void v(String message) {
    printer.v(message);
  }

  public static void w(String message) {
    printer.w(message);
  }

  public static void w(String tag, String message) {
    printer.w2(tag, message);
  }

  public static void wtf(String message) {
    printer.wtf(message);
  }

  public static void wtf(String tag, String message) {
    printer.wtf2(tag, message);
  }

  /**
   * Formats the json content and print it
   *
   * @param json the json content
   */
  public static void json(String json) {
    printer.json(json);
  }

  public static void json(String tag, String json) {
    printer.json2(tag, json);
  }

  public static void json(String tag, JSONObject json) {
    if(json == null){
      printer.json2(tag, "");
    }
    else {
      printer.json2(tag, json.toString());
    }
  }

  /**
   * Formats the json content and print it
   *
   * @param xml the xml content
   */
  public static void xml(String xml) {
    printer.xml(xml);
  }

  public static void xml(String tag, String xml) {
    printer.xml2(tag, xml);
  }

  public static LogLevel getLogLevel() {
    return printer.getSettings().getLogLevel();
  }
}
