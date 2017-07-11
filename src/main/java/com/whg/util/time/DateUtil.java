package com.whg.util.time;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtil {

	private static final long time_difference = TimeUnit.HOURS.toMillis(5);
	
	private static final long actual_time_disfference = TimeUnit.HOURS.toMillis(8);
	
	public static final String DAY = "yyyy-MM-dd";
	
	public static final String SECONDS = "HH:mm:ss";
	
	public static final String DAY_SECONDS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YEAR_MONTH = "yyyyMM";
	
	public static final String YEAR_MONTH_DAY = "yyyyMMdd";
	
	public static long currentTimeMimute(){
		return timeMimute(TimeUtil.currentTimeMillis());
	}
	
	public static long timeMimute(long millis){
		return TimeUnit.MILLISECONDS.toMinutes(millis);
	}
	
	/** 四舍五入时间到当前分钟上 */
	public static long roundMinuteCurrentTimeMillis(){
		long now = TimeUtil.currentTimeMillis();
		return roundMinuteTimeMillis(now);
	}
	
	/** 四舍五入时间到分钟上 */
	public static long roundMinuteTimeMillis(long time){
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTimeInMillis(time);
		long nowSecond = nowCal.get(Calendar.SECOND);
		
		long mimutes = TimeUnit.MILLISECONDS.toMinutes(time);
		if(nowSecond > 30){
			mimutes++;
		}
		return TimeUnit.MINUTES.toMillis(mimutes);
	}
	
	/** 当前小时的毫秒 */
	public static long currentHourMillis(){
		return hourMillis(TimeUtil.currentTimeHour());
	}
	
	public static long hourMillis(long hour){
		return TimeUnit.HOURS.toMillis(hour);
	}
	
	public static long hour(long milli){
		return TimeUnit.MILLISECONDS.toHours(milli);
	}
	
	public static String f(long millis, String format){
		if(millis <= 0){
			return "";
		}
		return DateUtils.format(millis, format);
	}
	
	public static final long millis2Day = 24*60*60*1000;
	public static final long millis2Hour = 60*60*1000;
	public static final long millis2Minute = 60*1000;
	public static final long millis2Second = 1000;
	
	public static String millisFormat(long millis){
		long day = millis / millis2Day;
		long remainder = millis % millis2Day;
		long hour = remainder / millis2Hour;
		remainder = remainder % millis2Hour;
		long minute = remainder / millis2Minute;
		remainder = remainder % millis2Minute;
		long second = remainder / millis2Second;
		return String.format("%d天%d小时%d分钟%d秒", day, hour, minute, second);
	}
	
	/*	
	public static int getTodayIntValue(long millis) {
        return (int) TimeUnit.MILLISECONDS.toDays(millis);
    }
    */
	
	/**
     * 返回当前距1970年1月1日的天数(3点为界)
     * 
     * @return
     */
    public static int getTodayIntValue() {
        return (int) TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtBeijing());
    }
    
    /**
     * 返回nDaysAtferToday距1970年1月1日的天数(0点为界)
     * 
     * @return
     */
    public static int getBeiJingCurrentDayIntValue() {
        return (int) (TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtBeijingActual()));
    }
	
	/**
     * 返回nDaysAtferToday距1970年1月1日的天数(0点为界)
     * 
     * @return
     */
    public static int getBeiJingBeforeDayIntValue(int nDaysAfterToday) {
        return (int) (TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtBeijingActual()) - nDaysAfterToday);
    }
    
    /**
     * 返回nDaysAtferToday距1970年1月1日的天数(0点为界)
     * 
     * @return
     */
    public static int getBeiJingAfterDayIntValue(int nDaysAfterToday) {
        return (int) (TimeUnit.MILLISECONDS.toDays(currentTimeMillisAtBeijingActual()) + nDaysAfterToday);
    }
    
    /**
     * 当前时间加上5小时的时差（3点为界）
     * 
     * @return
     */
    public static long currentTimeMillisAtBeijing() {
        return TimeUtil.currentTimeMillis() + time_difference;
    }
    
    /**
     * 当前时间加上8小时的时差（0点为界）
     * 
     * @return
     */
    private static long currentTimeMillisAtBeijingActual() {
        return TimeUtil.currentTimeMillis() + actual_time_disfference;
    }
    
    public static String currentYearMonthDay(){
    	return f(TimeUtil.currentTimeMillis(), YEAR_MONTH_DAY);
    }
    
    public static String currentYearMonth(){
    	return f(TimeUtil.currentTimeMillis(), YEAR_MONTH);
    }
    
    public static String currentYearMonthDaySeconds(long time){
    	return f(time, DAY_SECONDS);
    }
    
    public static long parseTime(String timeStr){
    	try {
			return DateUtils.parse(timeStr, DAY_SECONDS).getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
    }
    
    public static long parseDayTime(String dayTimeStr){
    	try {
			return DateUtils.parse(dayTimeStr, DAY).getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
    }
    
    public static int getTodayDay(){
    	Calendar now = Calendar.getInstance();
    	now.setTimeInMillis(currentTimeMimute());
    	return now.get(Calendar.DAY_OF_MONTH);
    } 
    
    
    public static int getTodayDayByTime(long millis){
    	Calendar now = Calendar.getInstance();
    	now.setTimeInMillis(millis);
    	return now.get(Calendar.DAY_OF_YEAR);
    } 
    
    public static String getTowMonthAgoYearMonth(){
    	Calendar now = Calendar.getInstance();  
    	now.setTimeInMillis(currentTimeMimute());
		now.add(Calendar.MONTH, -2);
		return f(now.getTimeInMillis(), YEAR_MONTH);
    }
    
    
    /**
     * 计算两时间之间天数 以三点为界限
     * @param satrTime	开始时间
     * @param endTime	结束时间
     * @return	两时间之间天数
     */
    public static int towDayTimeByThreeHour(long satrTime,long endTime){
		Calendar starTimeCalendar = Calendar.getInstance();
		starTimeCalendar.setTimeInMillis(satrTime);
		int starHour = starTimeCalendar.get(Calendar.HOUR_OF_DAY);
		if (starHour < 3) {
			starTimeCalendar.setTimeInMillis(satrTime - TimeUnit.DAYS.toMillis(1));
		} else { //以凌晨3点为界作为第二天的开始
			starTimeCalendar.setTimeInMillis(satrTime);
		}
		
		Calendar endTimeCalendar = Calendar.getInstance();
		endTimeCalendar.setTimeInMillis(endTime);
		int endHour = endTimeCalendar.get(Calendar.HOUR_OF_DAY);
		if (endHour < 3) {
			endTimeCalendar.setTimeInMillis(endTime - TimeUnit.DAYS.toMillis(1));
		} else { //以凌晨3点为界作为第二天的开始
			endTimeCalendar.setTimeInMillis(endTime);
		}
		int day = endTimeCalendar.get(Calendar.DAY_OF_YEAR) - starTimeCalendar.get(Calendar.DAY_OF_YEAR);
		/*System.out.println("createTime:"+ DateUtils.format(satrTime, "yyyy-MM-dd HH:mm:ss"));
		System.out.println("endTime:"+ DateUtils.format(endTime, "yyyy-MM-dd HH:mm:ss"));
		System.err.println("day:" + day);*/
		return day;
	}
    
    public static long changeTimeToThreeHour(long time){
    	String timeStr = DateUtils.format(time, "yyyy-MM-dd");
    	String threeTimeStr = " 03:00:00";
    	String newTimeStr = timeStr + threeTimeStr;
    	long t = parseTime(newTimeStr);
    	if(time < t){
    		Calendar timeCalendar = Calendar.getInstance();
    		timeCalendar.setTimeInMillis(t + TimeUnit.DAYS.toMillis(-1));
    		t = timeCalendar.getTimeInMillis();
    	}
    	return t;
    }
    
    public static void main(String[] args) {
    	long millis = TimeUnit.MINUTES.toMillis(80);
    	System.out.println(millisFormat(millis));
    	
    	/*System.out.println(TimeUtil.currentTimeMillis());
		System.out.println(f(TimeUnit.SECONDS.toMillis(1478598888L), DAY_SECONDS));
		
		int currDay = getTodayIntValue();
		System.out.println(currDay);
		System.out.println(f(TimeUnit.DAYS.toMillis(17116), DAY_SECONDS));
		
		Date date = new Date(System.currentTimeMillis());
		Calendar now = Calendar.getInstance();  
		
		now.add(Calendar.MONTH, -2);
		System.err.println("年: " + now.get(Calendar.YEAR));  
        System.err.println("月: " + (now.get(Calendar.MONTH) + 1) + "");  
        System.err.println("日: " + now.get(Calendar.DAY_OF_MONTH));  
        System.err.println("时: " + now.get(Calendar.HOUR_OF_DAY));  
        System.err.println("分: " + now.get(Calendar.MINUTE));  
        System.err.println("秒: " + now.get(Calendar.SECOND));  
        System.err.println("当前时间毫秒数：" + now.getTimeInMillis());  
        System.err.println(now.getTime());  
        
        System.err.println(getTowMonthAgoYearMonth());*/
    	long time = DateUtil.parseTime("2017-01-17 02:59:53");
    	long time2 = DateUtil.parseTime("2017-01-17 03:00:03");
    	
//    	System.err.println(towDayTimeByThreeHour(time2, Activities.calEndTime(time, -1)));
    	
    	java.util.Calendar cal = java.util.Calendar.getInstance();
		long l = cal.getTimeInMillis();
		System.out.println(l);
		System.out.println(DateUtil.f(l, DateUtil.DAY_SECONDS));
		
		System.out.println("System.currentTimeMillis="+System.currentTimeMillis());
		System.out.println(DateUtil.f(System.currentTimeMillis(), DateUtil.DAY_SECONDS));
		
		System.out.println("currentTimeSecond="+TimeUtil.currentTimeSecond());
		System.out.println("currentTimeMillis="+TimeUtil.currentTimeMillis()/1000);
		
		//1、取得本地时间：
		cal = java.util.Calendar.getInstance();
		//2、取得时间偏移量：
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		//3、取得夏令时差：
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		//4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		//之后，您再通过调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
		l = cal.getTimeInMillis();
		System.out.println("l="+l);
		System.out.println("lStr="+DateUtil.f(l, DateUtil.DAY_SECONDS));
		
		long utc = TimeUtil.currentTimeMillis();
		System.out.println("utc="+utc);
		System.out.println("utcStr="+DateUtil.f(utc, DateUtil.DAY_SECONDS));
		
		Instant instant = Instant.now();
		System.out.println(instant.toString());
		
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		System.out.println(now);
		
		//DateTimeZone zoneMontréal = DateTimeZone.forID( "America/Montreal" );
		//DateTime now = DateTime.now( zoneMontréal );
		
		/*
		DateTime nowD = new DateTime(); // Default time zone.
		DateTime zulu = nowD.toDateTime(DateTimeZone.UTC);
		System.out.println( "Local time in ISO 8601 format: " + nowD );
		System.out.println( "Same moment in UTC (Zulu): " + zulu );
		System.out.println("Same mills in UTC (Zulu): " + zulu.getMillis());
		System.out.println("Same moment in UTC (Zulu): " + DateUtil.f(zulu.getMillis(), DateUtil.DAY_SECONDS));
		
		DateTime nowD2 = DateTime.now(DateTimeZone.UTC);
		System.out.println("nowD2="+nowD2);
		long utcMillis = nowD2.getMillis();
		System.out.println("utcMillis="+utcMillis);
		System.out.println("utcMillisStr="+DateUtil.f(utcMillis, DateUtil.DAY_SECONDS));
		
		DateTimeZone zoneDefault = DateTimeZone.getDefault();
		DateTime nowD3 = DateTime.now(zoneDefault);
		System.out.println("nowD3="+nowD3);
		System.out.println(DateUtil.f(nowD3.getMillis(), DateUtil.DAY_SECONDS));
		
		System.out.println(DateTime.now().toDateTime(DateTimeZone.UTC));
		*/
		
		Calendar c = Calendar.getInstance();
	    System.out.println("current: "+c.getTime());

	    TimeZone z = c.getTimeZone();
	    int offset = z.getRawOffset();
	    if(z.inDaylightTime(new Date())){
	        offset = offset + z.getDSTSavings();
	    }
	    int offsetHrs = offset / 1000 / 60 / 60;
	    int offsetMins = offset / 1000 / 60 % 60;

	    System.out.println("offset: " + offsetHrs);
	    System.out.println("offset: " + offsetMins);

	    c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
	    c.add(Calendar.MINUTE, (-offsetMins));

	    System.out.println("GMT Time: "+c.getTime());
	    long utcMills2 = c.getTime().getTime();
	    System.out.println("utcMills2="+utcMills2);
	    System.out.println("utcMills2Str="+DateUtil.f(utcMills2, DateUtil.DAY_SECONDS));
	}
	
}
