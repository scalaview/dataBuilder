package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = formatDate.parse("2013-3-11");
		Date d2 = formatDate.parse("2013-4-17");
		System.out.println(between(d2,d1));
//		Date date = new Date();
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.MONTH, month);
		// return cal.getTime();
//		Date t = getBeforeDate(d2,37);
//		System.out.println(formatDate.format(t));
		Date t = getBeforeDate(d2,37);
		System.out.println(t.toString());
		
	}

	public static Date getBeforeDate(Date pointDate, long days){
		Calendar calPoint = Calendar.getInstance();
		calPoint.setTime(pointDate);
		calPoint.clear(14);
		long millisecs = days * 0x5265c00L;
		long before = calPoint.getTime().getTime() - millisecs;
		return new Date(before);
	}
	
	public static long between(Date beginDate, Date endDate) {
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(beginDate);
		calEnd.setTime(endDate);
		calBegin.clear(14);
		calEnd.clear(14);
		long millisecs = calBegin.getTime().getTime()
				- calEnd.getTime().getTime();
//		long remainder = millisecs % 0x5265c00L;
//		return (millisecs - remainder) / 0x5265c00L;
		return millisecs  / 0x5265c00L;
	}
}
