package com.vocation.travel.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {
  public static LocalDateTime plus7UTC(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
  }

  public static boolean checkStartTimeAfterFinishTime(LocalDateTime timeStart, LocalDateTime timeFinish) {
    return timeStart.isAfter(timeFinish);
  }

  public static boolean checkFinishTimeBeforeStartTime(LocalDateTime timeStart, LocalDateTime timeFinish) {
    return timeFinish.isBefore(timeStart);
  }

  public static boolean checkBetweenTime(LocalDateTime timeStart, LocalDateTime timeFinish) {
    LocalDateTime now = LocalDateTime.now();
    return timeStart.isAfter(now) && timeFinish.isBefore(now);
  }

  public static LocalDateTime compareToDDMMYY(LocalDateTime time) {
    return LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 0, 0);
  }

  public static boolean compareToEqual(LocalDateTime time1, LocalDateTime time2) {
    return time1.isEqual(time2);
  }
}
