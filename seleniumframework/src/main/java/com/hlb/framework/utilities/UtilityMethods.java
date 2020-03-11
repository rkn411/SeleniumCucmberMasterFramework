package com.hlb.framework.utilities;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.hlb.framework.base.DriverManager;

import io.cucumber.core.api.Scenario;

public class UtilityMethods {

	/**
	 * This method is used for getting name of child directory of specified
	 * directory
	 * 
	 * @param path
	 * 
	 *            - path of directory
	 * @return - child directory name
	 */
	public static String getChildDirectoryName(String path) {
		File file = new File(path);

		File[] files = file.listFiles();

		for (File f : files) {
			if (f.isDirectory()) {
				return f.getName();
			}
		}
		return null;
	}

	public static void takeScreenShot(Scenario scenario, String... screenShotName) {
		final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
		if (screenShotName.length > 0) {
			scenario.embed(screenshot, "image/png", screenShotName[0]);
		} else {
			scenario.embed(screenshot, "image/png", scenario.getName());
		}
	}
	
	public static boolean isNumber(String number) {
		try {
			Integer.parseInt(number);
			return true;
		}catch(NumberFormatException exception) {
			return false;
		}
	}
	
	public enum DateFormat{
		ddMMyyyy_Slash("dd/MM/yyyy"), //26/01/2019
		MMddyyyy_Slash("MM/dd/yyyy"), //01/26/2019
		yyyyMMdd_Slash("yyyy/MM/dd"), //2019/01/26
		dMyyyy_Slash("d/M/yyyy"),
		dMMMMyyyy_Slash("d/MMMM/yyyy"), //1/January/2020
		ddMMyyyy_Hyphen("dd-MM-yyyy"), //26-01-2019
		MMddyyyy_Hyphen("MM-dd-yyyy"), //01-26-2019
		yyyyMMdd_Hyphen("yyyy-MM-dd"), //2019-01-26
		ddMMyyyy_Space("dd MM yyyy"), //26 01 2019
		MMddyyyy_Space("MM dd yyyy"), //01 26 2019
		yyyyMMdd_Space("yyyy MM dd"), //2019 01 26
		MMMM("MMMM"), // January
		MMM("MMM"), // Jan
		MM("MM"); // 01
		
		private String format;
		
		private DateFormat(String format) {
			this.format=format;
		}
		
		public String getDateFormat() {
			return format;
		}
	}
	
	/**
	 * This method return current date based on format passed
	 * @param dateFormat - Enum - Required date format
	 * @return current date in string format
	 */
	public static String getCurrentDate(DateFormat dateFormat) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat.getDateFormat()); 
		LocalDateTime localDate=LocalDateTime.now();
		return format.format(localDate);
	}
	
	/**
	 * Returns date based on date format passed
	 * @param dateFormat - Format of the date
	 * @param days - Number of days to be added to the current date
	 * @return - date
	 */
	public static String getDate(DateFormat dateFormat,long days) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat.getDateFormat());
		LocalDateTime localDate=LocalDateTime.now();
		localDate=localDate.plusDays(days);
		return format.format(localDate);
	}
	
	/**
	 * This method return current month based on format passed
	 * @param dateFormat - Enum - Required Month format
	 * @return current month in string format
	 */
	public static String getMonth(DateFormat monthFormat,int months) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(monthFormat.getDateFormat());
		LocalDateTime localDate=LocalDateTime.now();
		localDate= localDate.plusMonths(months);
		return format.format(localDate);
	}
	
	/**
	 * This method returns first day of current month
	 * @param dateFormat - Enum - Required Month format
	 * @return current month in string format
	 * @return
	 */
	public static String getFirstDayOfMonth(DateFormat dateFormat,int months) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat.getDateFormat());
		LocalDateTime localDate=LocalDateTime.now();
		localDate= localDate.plusMonths(months);
		localDate=localDate.with(TemporalAdjusters.firstDayOfMonth());
		return format.format(localDate);
	}
	
	/**
	 * This method returns last day of current month
	 * @param dateFormat - Enum - Required Month format
	 * @return current month in string format
	 * @return
	 */
	public static String getLastDayOfMonth(DateFormat dateFormat,int months) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat.getDateFormat());
		LocalDateTime localDate=LocalDateTime.now();
		localDate= localDate.plusMonths(months);
		localDate=localDate.with(TemporalAdjusters.lastDayOfMonth());
		return format.format(localDate);
	}
	
	public  enum NumberFormats{
		ONEDECIMAL("#.0"),
		TWODECIMALS("#.00"),
		TWODECIMALSWITHCOMMA("#,###.00"),
		THREEDECIMALS("#.000"),
		FOURDECIMALS("#.0000"),
		FIVEDECIMALS("#.00000"),
		FIVEDECIMALSWITHZERO("0.00000"),
		FIVEDECIMALSWITHCOMMA("#,###.00000");
		
		private String decimalFormat;
		
		private NumberFormats(String decimalFormat) {
			this.decimalFormat=decimalFormat;
		}
		
		public String getDecimalFormat() {
			return decimalFormat;
		}
	}
	
	/**
	 * This method is used for adding number of decimals to int/float/double values
	 * @param decimalFormat
	 * @param number
	 * @return formatted value in string
	 */
	public static String formatToDecimal(NumberFormats decimalFormat,double number,RoundingMode... roundMode) {
		if(roundMode.length>0) {
			DecimalFormat df= new DecimalFormat(decimalFormat.getDecimalFormat());
			df.setRoundingMode(roundMode[0]);
			return df.format(number);
		}
		return new DecimalFormat(decimalFormat.getDecimalFormat()).format(number);
	}
	
	public enum NumberOfDecimals{
		ONE(1),
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5);
		
		private int number;
		
		private NumberOfDecimals(int number) {
			this.number=number;
		}
		
		public int getNumber() {
			return this.number;
		}
	}
	
	public enum DecimalSign{
		INCREAMENT,DECREAMENT;
	}
	
	/**
	 * This method is used to add number of decimals after decimal point
	 * @param number - Number for which decimal places has to be added
	 * @param numberOfDecimals - Number of required decimals after decimal point
	 * @return
	 */
	public static String addDecimals(String number,NumberOfDecimals numberOfDecimals) {
		String beforeDecimal=number.split("\\.")[0];
		String decimals=number.split("\\.")[1];
		
		int diff=numberOfDecimals.getNumber()-decimals.length();
		
		if(diff>0) {
			for(int counter=0;counter<diff;counter++) {
				decimals=decimals+"0";
			}
		}else if(diff<0) {
			decimals=decimals.substring(0, numberOfDecimals.getNumber());
		}
		return beforeDecimal+"."+decimals;
	}
	
	/**
	 * This method is used format number to currency format
	 * @param number
	 * @return - return number in format like 1,234,456.12
	 */
	public static String formatNumber(double number) {
		String s=String.format("%,f", number);
		return s;
	}
	
	/**
	 * This method takes number in string, and represent negative number in braces () format
	 * @param number -
	 * @return
	 */
	public static String formatNegativeNumber(String number) {
		if(number.contains("-")) {
			return "("+number.replace("-", "")+")";
		}else {
			return number;
		}
	}
}
