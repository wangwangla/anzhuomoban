package test.kw.accountapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     *  输出  09:08
     * @param timeStamp
     * @return string
     */
    public static String getFormattedTime(long timeStamp){
        //使用Format进行格式化  yyyy-MM-dd  HH:mm:ss
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date(timeStamp));
    }

    /**
     * yyyy-MM-dd
     * @return
     */
    public static String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }
}
