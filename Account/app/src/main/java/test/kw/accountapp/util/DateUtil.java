package test.kw.accountapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /**
     * 字符串转日期
     * @param date
     * @return
     */
    private static Date strToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 返回星期
     * @param date
     * @return
     */
    public static String getWeekDay(String date){
        //数组存储xingq
        String [] weekdays = {
                "SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THUESDAY","FRIDAY","SATURDAY"
        };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(date));
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return weekdays[weekDay];
    }

    public static String getDateTitle(String date){
        String []months = {"january","February","March","April","May","June","August","September","Octpber","Novomber","December"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(date));
        int monthIndex = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return months[monthIndex]+" "+String.valueOf(day);
    }
}
