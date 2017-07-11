package com.whg.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 打印StackTrace的工具类
 */
public final class StackTraceUtil {

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	/**
	 * Defines a custom format for the stack trace as String.
	 */
	private static String getCustomStackTrace(Throwable aThrowable) {
		//add the class name and any message passed to constructor
		final StringBuilder result = new StringBuilder( "BOO-BOO: " );
		result.append(aThrowable.toString());
		final String NEW_LINE = System.getProperty("line.separator");
		result.append(NEW_LINE);

		//add each element of the stack trace
		StackTraceElement element = null;
		for (int i = 0; i < aThrowable.getStackTrace().length; i++) {
			element = aThrowable.getStackTrace()[i];
			result.append( element );
			result.append( NEW_LINE );
		}
		return result.toString();
	}

	/** Demonstrate output.  */
	public static void main(String[] args) {
		final Throwable throwable = new IllegalArgumentException("Blah");
		System.out.println( getStackTrace(throwable) );
		System.out.println( getCustomStackTrace(throwable) );
	}
	
}