package com.nkvl.app.classes;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class Wasted {
    public static String transferFromSeconds(int sec)
    {
        return new SimpleDateFormat("mm:ss").format(new Date(TimeUnit.SECONDS.toMillis(sec)));
    }
    public static String getToday() {
        LocalDateTime futureDate = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dtf.format(futureDate);
    }
}
