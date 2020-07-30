package us.dontcareabout.avMine.client.util;

import com.google.gwt.i18n.client.NumberFormat;

import us.dontcareabout.gwt.client.Console;

public class TimeUtil {
	private static final NumberFormat normalFormat = NumberFormat.getFormat("##");
	private static final NumberFormat char2Format = NumberFormat.getFormat("00");

	public static String format(double seconds) {
		return fromat((long)seconds);
	}

	public static String fromat(long seconds) {
		String result = char2Format.format(seconds % 60);
		seconds = seconds / 60;
		result = (seconds > 10 ? char2Format.format(seconds % 60) : normalFormat.format(seconds % 60))
			+ ":" + result;
		seconds = seconds / 60;
		return seconds >= 1 ? normalFormat.format(seconds) + ":" + result : result;
	}

	static void testFormat() {
		Console.log(fromat(59));	//0:59
		Console.log(fromat(60));	//1:00
		Console.log(fromat(61));	//1:01
		Console.log(fromat(599));	//9:59
		Console.log(fromat(600));	//10:00
		Console.log(fromat(601));	//10:01
		Console.log(fromat(3599));	//59:59
		Console.log(fromat(3600));	//1:00:00
		Console.log(fromat(4199));	//1:09:59
		Console.log(fromat(4200));	//1:10:00
		Console.log(fromat(4201));	//1:10:01
		Console.log(fromat(36000));	//10:00:00
		Console.log(fromat(36599));	//10:09:59
	}
}
