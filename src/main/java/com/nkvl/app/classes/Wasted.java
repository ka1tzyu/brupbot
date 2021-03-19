package com.nkvl.app.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class Wasted {
    public static String transferFromSeconds(int sec)
    {
        return new SimpleDateFormat("mm:ss").format(new Date(TimeUnit.SECONDS.toMillis(sec)));
    }
}
