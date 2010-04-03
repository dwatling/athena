package com.synaptik.athena;

import junit.framework.AssertionFailedError;

public class AthenaTestResult {

	protected String testName;
	protected long start;
	protected long end;
	protected Throwable error;
	protected AssertionFailedError failure;
	
	public  AthenaTestResult(String testName, long start, long end, Throwable error, AssertionFailedError failure) {
		this.testName = testName;
		this.start = start;
		this.end = end;
		this.error = error;
		this.failure = failure;
	}
	
	public String toXML() {
		StringBuilder result = new StringBuilder();
		float duration = (end - start) / 1000.0f;
		result.append("<testcase name=\"").append(testName).append("\" duration=\"").append(duration).append("\">\n");
		result.append("</testcase>\n");
		
		return result.toString();
	}
	
	public static String getTestNameFromString(String str) {
		return null;
	}
	public static String getClassNameFromString(String str) {
		return null;
	}
}
