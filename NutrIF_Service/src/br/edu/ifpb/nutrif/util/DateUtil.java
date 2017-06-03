package br.edu.ifpb.nutrif.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.Weeks;

import br.edu.ladoss.entity.Dia;

public class DateUtil {
	
	public static int DATA_IGUAL = 0;
	
	public static int UM_DIA_MINUTOS = 1440;
	
	public static int UM_DIA = 1;
	
	public static Date INICIO_DIA = setTime(0, 0, 0);
	
	public static Date FIM_DIA = setTime(23, 59, 0);
	
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
	
	public static int getTodayHoursDiff(Date finalDayOfWeek) {
        
		Date now = new Date();
        
		Period period = getPeriodBetweenDate(now, finalDayOfWeek);
        
        int diff = period.getHours();        
        
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
	
	public static Period getPeriodBetweenDate(Date inicio, Date fim) {
		
		Interval interval = new Interval(inicio.getTime(), fim.getTime());
		Period period = interval.toPeriod();
		
		return period;
	}
	
	public static boolean isGreater(Date inicio, Date fim, Date diferenca) {
		
		DateTime dataInicial = new DateTime(inicio);
		DateTime dataFinal = new DateTime(fim);
		
		Hours hours = Hours.hoursBetween(dataInicial, dataFinal);
		
		int horasDiferenca = diferenca.getHours();
		
		return hours.isGreaterThan(Hours.hours(horasDiferenca));
	}
	
	public static boolean isLonger(Period p1, Period p2) {
		
		Instant now = Instant.now();
		
		Duration d1 = p1.toDurationTo(now);
		Duration d2 = p2.toDurationTo(now);
		
		return d1.isLongerThan(d2);
	}
	
	public static long getMinutesBetweenDate(Date inicio, Date fim) {
		
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
	
	public static Date setTime(int hora, int minuto, 
			int segundo) {
		
		Calendar calendar = Calendar.getInstance();
		
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, segundo);
        calendar.set(Calendar.MINUTE, minuto);
        calendar.set(Calendar.HOUR_OF_DAY, hora);

        return calendar.getTime();	
	}
	
	/**
	 * Calcular a quantidade de dias entre datas.
	 * 
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
		
		List<Date> dates = new ArrayList<Date>();
		
		Calendar calendar = new GregorianCalendar();		
		calendar.setTime(startdate);
		
		enddate = addDays(enddate, UM_DIA);

		while (calendar.getTime().before(enddate)) {
			
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		
		return dates;
	}
	
	/**
	 * Calcular a quantidade de semanas entre datas.
	 * 
	 * @param inicio
	 * @param fim
	 * @return
	 */
	public int weeksBetweenDate(Date inicio, Date fim) {
		
		DateTime inicoDateTime = new DateTime(inicio);
		DateTime fimDateTime = new DateTime(fim);

		return Weeks.weeksBetween(inicoDateTime, fimDateTime).getWeeks();
	}
	
	/**
	 * Retornar a data completa de um determinado dia da semana.
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public static Date getDateOfDayWeek(int dayOfWeek) {
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		
		return c.getTime();
	}
	
	/**
	 * Calcular a data para o dia da semana atual ou anterior.
	 *  
	 * @param minusWeek
	 * @param dayOfWeekCalendar
	 * @return
	 */
	public static Date getDateOfDayWeek(int minusWeek, int dayOfWeekCalendar) {
		
		DateTime today = DateTime.now();
		DateTime dayLastWeek = today.minusWeeks(minusWeek).withDayOfWeek(
				toDateTimeConstantsDayOfWeek(dayOfWeekCalendar));
		
		return dayLastWeek.toDate();		
	}
	
	public static List<Date> getAllDatesPastOfDayWeek(int pastWeek, int dayOfWeekCalendar) {
		
		List<Date> datas = new ArrayList<Date>();
		
		for (int countWeek = 1; countWeek <= pastWeek; countWeek++) {
			
			Date data = getDateOfDayWeek(countWeek, dayOfWeekCalendar);
			datas.add(data);			
		}
		
		return datas;
	}
	
	/**
	 * Converter o dia da semana no formato Calendar (Domingo a Sábado) para o DateTime (Segunda a Domingo).
	 * 
	 * @param dayOfWeekInCalendar
	 * @return
	 */
	public static int toDateTimeConstantsDayOfWeek(int dayOfWeekInCalendar){
		
		int dayOfWeek = (dayOfWeekInCalendar + DateTimeConstants.DAYS_PER_WEEK - DateTimeConstants.MONDAY) 
				% DateTimeConstants.DAYS_PER_WEEK;		
		
		return dayOfWeek != 0 ? dayOfWeek: DateTimeConstants.SUNDAY;
	}
	
}
