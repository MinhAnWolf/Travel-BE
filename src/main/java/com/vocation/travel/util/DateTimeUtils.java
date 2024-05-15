package com.vocation.travel.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtils {
  public static LocalDateTime plus7UTC(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
  }

  public static boolean checkStartTimeAfterFinishTime(Date timeStart, Date timeFinish) {
    return timeStart.after(timeFinish);
  }

  public static boolean checkFinishTimeBeforeStartTime(Date timeStart, Date timeFinish) {
    return timeFinish.before(timeStart);
  }

  /////////////////////////////////////////
//  public static boolean checkCurrentTime(Date timeStart, Date currentTime){
//    return ;
//  }

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
