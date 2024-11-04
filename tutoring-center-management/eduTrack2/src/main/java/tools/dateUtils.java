package tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
public class dateUtils {
	
	 public dateUtils() {TimeZone.setDefault(TimeZone.getTimeZone("UTC"));	}

	private Locale locale;

	    public Instant getDateUTCTimeZone(Date date){
	        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	        Instant instant = date.toInstant();
	        Date newDate = Date.from(instant);
	        addHourToDate(newDate, 1);//add one hour for UTC
	        return newDate.toInstant();
	    }
	    public Date createDateWithFormat(String dateString) {//todo duplicated
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        try {
	            return format.parse(dateString);
	        } catch (ParseException e) {
	            return null;
	        }
	    }
	    public void setTimeDateToDate(Date newDateTime, Date dateTime, int seconds) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(newDateTime);
	        Calendar newDateCalendar = Calendar.getInstance();
	        newDateCalendar.setTime(dateTime);
	        calendar.set(Calendar.HOUR_OF_DAY, newDateCalendar.get(Calendar.HOUR_OF_DAY));
	        calendar.set(Calendar.MINUTE, newDateCalendar.get(Calendar.MINUTE));
	        calendar.set(Calendar.SECOND, seconds);
	        newDateTime.setTime(calendar.getTimeInMillis());
	    }

	    public void setTimeLocalTimeToDate(Date date, LocalTime localTime, int seconds) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);

	        calendar.set(Calendar.HOUR_OF_DAY, localTime.getHour());
	        calendar.set(Calendar.MINUTE, localTime.getMinute());
	        calendar.set(Calendar.SECOND, seconds);

	        date.setTime(calendar.getTimeInMillis());
	    }
	    public Date instantToDate(Instant instant){
	        return Date.from(instant);
	    }

	    public void addMinutesToDate(Date date, int minutes) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MINUTE, minutes);
	        date.setTime(calendar.getTimeInMillis());
	    }
	    public void addHourToDate(Date date, int hours) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.HOUR_OF_DAY, hours);
	        date.setTime(calendar.getTimeInMillis());
	    }

	    public void subMinutesToDate(Date date, int minutes) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MINUTE, -minutes);
	        date.setTime(calendar.getTimeInMillis());
	    }
	    public void subHourToDate(Date date, int hours) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.HOUR_OF_DAY, -hours);
	        date.setTime(calendar.getTimeInMillis());
	    }

	    public int calculateDifferenceInMinutes(Date date1, Date date2) {
	        long differenceInMillis = Math.abs(date2.getTime() - date1.getTime());
	        int differenceInMinutes = (int)differenceInMillis / (1000 * 60);
	        return differenceInMinutes;
	    }

	    public boolean isDateInRange(Date date, Date date1, Date date2) {
	        long dateTmpTimestamp = date.getTime();
	        long horaireHeureDebTimestamp = date1.getTime();
	        long hhfTimestamp = date2.getTime();

	        return dateTmpTimestamp >= horaireHeureDebTimestamp && dateTmpTimestamp <= hhfTimestamp;
	    }
	    public int getDayOfWeekNumber(Date date){
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.DAY_OF_WEEK);
	    }

	    public LocalTime getLocalTime(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY),
	                calendar.get(Calendar.MINUTE));
	    }
	    public int getHourOfDate(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.HOUR_OF_DAY);
	    }

	    public int getMinuteOfDate(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.MINUTE);
	    }

	    public void subDaysToDate(Date date, int days) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, -days);
	        date.setTime(calendar.getTimeInMillis());
	    }
	    public void addDaysToDate(Date date, int days) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, days);
	        date.setTime(calendar.getTimeInMillis());
	    }

	    public Date parseDate(String dateString, String pattern) {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	        try {
	            return simpleDateFormat.parse(dateString);
	        } catch (ParseException e) {
	            System.err.println(e.getMessage());
	            return null;
	        }
	    }
	    public String parseString(Date date, String pattern) {
	        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	        return sdf.format(date);
	    }
	    public Date changeDatePattern(Date date, String newPattern){
	        return parseDate(parseString(date, newPattern), newPattern);
	    }
	    public String getDayName(Date date){
	        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", locale);
	        return dayFormat.format(date).substring(0, 1).toUpperCase() + dayFormat.format(date).substring(1);
	    }
	    public String getFullDayName(Date date){
	        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", locale);
	        return dayFormat.format(date).substring(0, 1).toUpperCase() + dayFormat.format(date).substring(1);
	    }
	    public String getDayNumber(Date date){
	        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	        return dayFormat.format(date);
	    }
	    public String getFullMonthName(Date date){
	        SimpleDateFormat dayFormat = new SimpleDateFormat("MMMM", locale);
	        return dayFormat.format(date).substring(0, 1).toUpperCase() + dayFormat.format(date).substring(1);
	    }
	    public String getMonthName(Date date){
	        SimpleDateFormat dayFormat = new SimpleDateFormat("MMM", locale);
	        return dayFormat.format(date).substring(0, 1).toUpperCase() + dayFormat.format(date).substring(1);
	    }
	    public String getMonthNumber(Date date){
	        SimpleDateFormat dayFormat = new SimpleDateFormat("MM", locale);
	        return dayFormat.format(date);
	    }
	    public int getLastDayOfMonth(Date date){
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    }
	    public int getYear(Date date){
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.YEAR);
	    }

	    public long convertLocalDateToTimestamp(LocalDate localDate) {
	        LocalDateTime localDateTime = localDate.atStartOfDay();
	        Date date = Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
	        return date.getTime();
	    }
	    public Date localDateToDate(LocalDate localDate) {
	        LocalDateTime localDateTime = localDate.atStartOfDay();
	        return Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
	    }
	    public  LocalTime convertToLocalTime(String timeString) {
	        String[] parts = timeString.split(":");
	        int hours = Integer.parseInt(parts[0]);
	        int minutes = Integer.parseInt(parts[1]);
	        int seconds = Integer.parseInt(parts[2]);

	        return LocalTime.of(hours, minutes, seconds);
	    }

	    public  String parseString(Instant instant, String pattern) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneOffset.UTC);
	        return formatter.format(instant);
	    }

	    public  String parseString(Timestamp timestamp, String pattern) {
	        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	        return sdf.format(timestamp);
	    }

	    public LocalTime stringToLocalTime(String timeString) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	        return LocalTime.parse(timeString, formatter);
	    }

	    public boolean isSunday(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	        return dayOfWeek == Calendar.SUNDAY;
	    }
	    public String getCurrentMonthAsString() {
	        return LocalDateTime.now().getMonth().toString();
	    }
	    
}
