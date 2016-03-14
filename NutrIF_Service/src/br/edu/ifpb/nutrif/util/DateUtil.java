package br.edu.ifpb.nutrif.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.edu.ladoss.entity.Dia;

public class DateUtil {

	private static Logger logger = LogManager.getLogger(DateUtil.class);
	
	public static int DATA_IGUAL = 0;
	
	public static Dia getCurrentDayOfWeek() {		
		
		Date current = new Date();		
		
		String dayName = new SimpleDateFormat("EEEE").format(current);
		logger.info("Dia atual: " + dayName);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		return new Dia(dayOfWeek, dayName);
	}
}
