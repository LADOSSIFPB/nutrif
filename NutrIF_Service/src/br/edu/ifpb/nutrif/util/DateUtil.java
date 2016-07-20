package br.edu.ifpb.nutrif.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Period;

import br.edu.ladoss.entity.Dia;

public class DateUtil {
	
	public static int DATA_IGUAL = 0;
	
	public static int UM_DIA_MINUTOS = 1440;
	
	public static int UM_DIA = 1;
	
	public static Dia getCurrentDayOfWeek() {		
		
		Date current = new Date();		
		
		return getDayOfWeek(current);
	}
	
	public static int getTodayDaysDiff(int finalDayOfWeek) {
        
		Calendar now = Calendar.getInstance();
        
		int nowDayOfWeek = now.get(Calendar.DAY_OF_WEEK);
		
        int diff = finalDayOfWeek - nowDayOfWeek;
        
        if (diff < 0) {
            diff += 7;
        }
        
        return diff;
    }
	
	public static Dia getDayOfWeek(Date date) {	
		
		String dayName = new SimpleDateFormat("EEEE").format(date);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		return new Dia(dayOfWeek, dayName);
	}
	
	/**
	 * Retornar a data completa de um determinado dia da semana.
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public static Date getDateOfDayWeek(int dayOfWeek) {
		
		Calendar c= Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		
		return c.getTime();
	}
	
	/**
	 * Incrementar dia(s) a uma data.
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		
		return cal.getTime();
	}
	
	/**
	 * Decrementar dias de uma data.
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date decreaseDays(Date date, int days) {
		
		int decreaseFactor = -1;
		
		return addDays(date, decreaseFactor * days);
	}
	
	public static Period getPeriodBetweenDate (Date inicio, Date fim) {
		
		Interval interval = new Interval(inicio.getTime(), fim.getTime());
		Period period = interval.toPeriod();
		
		return period;
	}
	
	public static int getMinutesBetweenDate (Date inicio, Date fim) {
		
		int minutes = Minutes.minutesBetween(new DateTime(inicio.getTime()), 
				new DateTime(fim.getTime())).getMinutes();
		
		return minutes;
	}
	
	public static Date setTimeInDate(Date date, int hora, int minuto, 
			int segundo) {
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hora);
		calendar.set(Calendar.MINUTE, minuto);
		calendar.set(Calendar.SECOND, segundo);

		Date newDate = calendar.getTime();
		
		return newDate;		
	}
	
	public static Date setTimeInDate(Date date, Date newTime) {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(newTime);
		
		return setTimeInDate(date, calendar.get(
				Calendar.HOUR_OF_DAY), 
				calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND));		
	}
}
