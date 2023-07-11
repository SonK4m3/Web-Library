package Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Date stringToDate(String dateString) {
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static java.util.Date sqlDate2utilDate(java.sql.Date sqlDate) {
		return Date.from(sqlDate.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public static java.sql.Date utilDate2sqlDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}
	
	public static java.util.Date getExceptionReceivingDate() {
		Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 2);

        return calendar.getTime();
	}
	
	public static java.util.Date timestamp2utilDate(java.sql.Timestamp sqlTimestamp) {
		long milliseconds = sqlTimestamp.getTime();
		java.util.Date utilDate = new java.util.Date(milliseconds);
		return utilDate;
	}
	
	public static LocalDateTime convert2LocalDateTime(java.sql.Time sqlTime) {
		long timeMilliseconds = sqlTime.getTime();
		java.time.LocalTime localTime = java.time.LocalTime.ofInstant(java.time.Instant.ofEpochMilli(timeMilliseconds), java.time.ZoneId.systemDefault());
		java.time.LocalDate localDate = java.time.LocalDate.now();
		return localDate.atTime(localTime);
	}
	
	public static String javaDate2StrDateTime(java.util.Date date) {
		return sdf.format(date);
	}
	
	public static java.util.Date sqlTime2javaDate(java.sql.Time sqlTime) {
		long milliseconds = sqlTime.getTime();
		return new java.util.Date(milliseconds);
	}
}
