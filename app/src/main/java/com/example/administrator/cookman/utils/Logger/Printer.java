package com.example.administrator.cookman.utils.Logger;

public interface Printer {

  Printer t(String tag, int methodCount);

  Settings init(String tag);

  Settings getSettings();

  void d(String message, Object... args);
  void d2(String tag, String message);

  void d(Object object);

  void e(String message, Object... args);
  void e2(String tag, String message);

//  void e(Throwable throwable, String message, Object... args);

  void w(String message, Object... args);
  void w2(String tag, String message);

  void i(String message, Object... args);
  void i2(String tag, String message);

  void v(String message, Object... args);
  void v2(String tag, String message);

  void wtf(String message, Object... args);
  void wtf2(String tag, String message);

  void json(String json);
  void json2(String tag, String json);

  void xml(String xml);
  void xml2(String tag, String xml);

  void log(int priority, String tag, String message, Throwable throwable);

  void resetSettings();

}
