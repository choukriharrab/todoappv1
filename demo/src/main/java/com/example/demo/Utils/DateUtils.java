package com.example.demo.Utils;

import java.text.SimpleDateFormat;
import java.sql.Date;

public class DateUtils {
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }
}
