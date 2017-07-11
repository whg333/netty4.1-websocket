package com.whg.util.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 
 * <p>从commons tools拷贝过来的，目的是为了解决commons tools和TrackingAPI都有相同包名的com.hoolai.util.DateUtils调用冲突</p>
 * 时间相关的工具方法,包括以下几方面<br>
 * <li> 日期时间计算
 * <li> 字符串解析为日期
 * <li> 日期格式化
 * <br>
 * <b> 首先检查你需要的功能是否已经存在，如果发现你需要相关功能不存在，请添加</b>
 * <br>
 * 实现依赖于common-lang包中的实现，所以format的效率比SimpleDateFormat更高
 * 
 */
public class DateUtils {

    /**
     * <p>Formats a date/time into a specific pattern.</p>
     * 
     * @param date  the date to format
     * @param pattern  the pattern to use to format the date
     * @return the formatted date
     */
	public static String format(Date date, String pattern){
		return DateFormatUtils.format(date, pattern);
	}
	
    /**
     * <p>Formats a date/time into a specific pattern.</p>
     * 
     * @param millis  the date to format expressed in milliseconds
     * @param pattern  the pattern to use to format the date
     * @return the formatted date
     */
	public static String format(long millis, String pattern){
		return DateFormatUtils.format(millis, pattern);
	}
	
    /**
     * <p>Formats a calendar into a specific pattern.</p>
     * 
     * @param calendar  the calendar to format
     * @param pattern  the pattern to use to format the calendar
     * @return the formatted calendar
     */
	public static String format(Calendar calendar, String pattern){
		return DateFormatUtils.format(calendar, pattern);
	}
	
    /**
     * <p>Parses a string representing a date.</p>
     * 
     * @param str  the date to parse, not null
     * @param parsePatterns  the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws ParseException if none of the date patterns were suitable
     */
	public static Date parse(String str, String parsePattern) throws ParseException{
		return org.apache.commons.lang.time.DateUtils.parseDate(str, new String[]{parsePattern});
	}
	
	/**
	 * 根据日期格式创建格式化器<br>
	 * SimpleDateFormat并不支持多线程访问，所以尽可能使用format和parse接口
	 * @param pattern
	 * @return
	 */
	public static DateFormat getDateFormatter(String pattern){
		return new SimpleDateFormat(pattern);
	}
	
	// 日期创建
	public static Date newDate(int year, int month, int day) {
		return newDate(year, month, day, 0, 0, 0);
	}
	
	/**
	 * 创建日期
	 * @param year
	 * @param month 月份是1-12
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 计算2个时间之间的天数<br>
	 * 不足一天的时间直接舍去
	 * @param startTime 开始时间(毫秒数) 
 	 * @param endTime 结束时间(毫秒数)
	 * @return 间隔天数
	 */
	public static int getDiffDay(long startTime, long endTime){
		if(startTime > endTime){
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(startTime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long start = cal.getTimeInMillis();
		
		cal.setTimeInMillis(endTime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long end = cal.getTimeInMillis();
		
		int days = (int)((end - start)/(1000*60*60*24));
		return days;
	}
	
	/**
	 * 计算一天的开始时刻和结束时刻
	 * @param calDate
	 * @return 开始时刻和结束时刻
	 */
	public static Date[] getDayBeginEnd(Date calDate){
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(calDate); 
		calBegin.set(Calendar.HOUR_OF_DAY, 0) ; 
		calBegin.set(Calendar.MINUTE, 0); 
		calBegin.set(Calendar.SECOND, 0);
		calBegin.set(Calendar.MILLISECOND, 0); 
		
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(calDate); 
		calEnd.set(Calendar.HOUR_OF_DAY, 23) ; 
		calEnd.set(Calendar.MINUTE, 59); 
		calEnd.set(Calendar.SECOND, 59);
		calEnd.set(Calendar.MILLISECOND, 0); 
		
		Date[] dateArray = new Date[2] ;
		dateArray[0] = calBegin.getTime() ;
		dateArray[1] = calEnd.getTime() ;
		return dateArray ;
	}
    
    /**
     * <p>当前标准北京时间当前距1970年1月1日的天数</p>
     * 用于更新第二天数据的情况下使用
     * @return
     */
    public static int getStandardTodayIntValueAtBeijing(){
    	return (int) TimeUnit.MILLISECONDS.toDays(currentStandardTimeMillisAtBeijing());
    }
    
    /**
     * <p>当前(快3小时的)北京时间当前距1970年1月1日的天数</p>
     * 用于更新第二天数据的情况下使用
     * @return
     */
    public static int getTodayIntValueAtBeijing(){
    	return (int) TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtBeijing());
    }
	
    private static long currentStandardTimeMillisAtBeijing() {
		long time_difference = TimeUnit.HOURS.toMillis(8);
		return TimeUtil.currentTimeMillis() + time_difference;
	}
    
    private static long currentTimeMillisAtBeijing() {
    	// 北京凌晨3点时候的时间当做是第二天，用于更新数据
		long time_difference = TimeUnit.HOURS.toMillis(5);
		return TimeUtil.currentTimeMillis() + time_difference;
	}
    
    /**
     * 当前韩国时间距1970年1月1日的天数
     * @return
     */
    public static int getTodayIntValueAtKorea(){
    	return (int) TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtKoreaOrJapen());
    }

    /**
     * 当前韩国时间（凌晨3点当一天开始）距1970年1月1日的天数
     * @return
     */
    public static int getTodayIntValueAtKoreaAt3(){
        return (int) TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtKoreaOrJapen(3));
    }
    
    /**
     * 当前日本时间距1970年1月1日的天数
     * @return
     */
    public static int getTodayIntValueAtJapen(){
    	return (int) TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtKoreaOrJapen());
    }
    
    private static long currentTimeMillisAtKoreaOrJapen() {
    	// 日本韩国都是+9时区，
    	long time_difference = TimeUnit.HOURS.toMillis(9);
    	return TimeUtil.currentTimeMillis() + time_difference;
    }
    
    /**
     * 可以设置以一天中某个整点(0~23)为当天的开始
     * @param dayBeginAtTime 一天开始的整点时间。比如凌晨0点当做一天的开始，dayBeginAtTime=0；凌晨3点当做一天的开始，dayBeginAtTime=3。
     * @return
     */
    private static long currentTimeMillisAtKoreaOrJapen(int dayBeginAtTime) {
        if(dayBeginAtTime < 0 || dayBeginAtTime > 23){
            throw new IllegalArgumentException("dayBeginAtTime should be between 0 - 23.");
        }
        // 日本韩国都是+9时区
        long time_difference = TimeUnit.HOURS.toMillis(9 - dayBeginAtTime);
        return TimeUtil.currentTimeMillis() + time_difference;
    }

    /**
     * 当前越南时间距1970年1月1日的天数
     * @return
     */
    public static int getTodayIntValueAtVn() {
        return (int) TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtVn());
    }

    private static long currentTimeMillisAtVn() {
        //越南+7时区
        long time_difference = TimeUnit.HOURS.toMillis(7);
        return TimeUtil.currentTimeMillis() + time_difference;
    }
	/**
	 * Checks if two date objects are on the same day ignoring time
	 * 
	 * @param one
	 * @param other
	 * @return
	 */
	public static boolean isSameDay(Date one, Date other) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(one, other);
	}

	/**
	 * Checks if two date objects are on the same day ignoring time
	 * 
	 * @param one
	 * @param other
	 * @return
	 */
	public static boolean isSameDay(long one, long other) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(new Date(one), new Date(other));
	}
	
	/**
	 * 给定一个日期，清除日期以下时分秒毫秒为0
	 * @param date
	 * @return
	 */
	public static Date clearHourMinuteSecoendMilli(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal.getTime();
	}
	
	/**
     * 得到当前日期距1970.1.1的天数是星期几
     * @param currentDays 当前距1970年1月1日（周四）的天数
     * @return 与{@link Calendar#DAY_OF_WEEK}表示值相同，返回1表示周日，2~7表示周一至周六
     */
    public static int getDayOfWeek(int currentDays){
        if(currentDays < 0){
            throw new RuntimeException("Not support this date!");
        }
        int remainder = (currentDays + 5) % 7;// 1970年1月1日是周四
        int weekOfDay = remainder == 0 ? 7 : remainder;
        return weekOfDay;
    }
    
    /**
     * 得到当前日期距1970.1.1的天数是一个月里的几号
     * @param currentDays 当前距1970年1月1日的天数
     * @return 与{@link Calendar#DAY_OF_MONTH}表示值相同，每月第一天返回1
     */
    public static int getDayOfMonth(int currentDays){
        long millis = TimeUnit.DAYS.toMillis(currentDays);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
	
}
