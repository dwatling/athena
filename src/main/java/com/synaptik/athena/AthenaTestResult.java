/**
 * Copyright 2010 Synaptik Solutions
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 * @author Dan Watling <dan@synaptik.com>
 */
package com.synaptik.athena;

import java.io.PrintWriter;
import java.io.StringWriter;

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
	
	public String toXML(String className) {
		StringBuilder result = new StringBuilder();
		float duration = (end - start) / 1000.0f;
		result.append("\t\t<testcase classname=\"").append(className).append("\" name=\"").append(testName).append("\" time=\"").append(duration).append("\">\n");
		if (failure != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			failure.printStackTrace(pw);
			result.append("\t\t\t<failure type=\"").append(failure.getClass().getName()).append("\" message=\"").append(escapeHTML(failure.getMessage())).append("\">\n");
			result.append(escapeHTML(sw.toString())).append("\n");
			result.append("\t\t\t</failure>\n");
		}
		result.append("\t\t</testcase>\n");
		
		return result.toString();
	}
	
	/**
	 * Given testMyMethod(com.mypackage), it will return testMyMethod
	 * @param str
	 * @return
	 */
	public static String getTestNameFromString(String str) {
		String result = str.substring(0, str.indexOf("("));
		return result;
	}
	/**
	 * Given testMyMethod(com.mypackage), it will return com.mypackage
	 * @param str
	 * @return
	 */
	public static String getClassNameFromString(String str) {
		String result = str.substring(str.indexOf("(")+1);
		result = result.substring(0, result.length()-1);	// remove trailling ')'
		return result;
	}

	/**
	 * Replaces <>" with HTML equivalents (e.g. &lt;&gt;&quot;)
	 * 
	 * I'm sure there is a standard way of doing this. But this should work for now...
	 */
	protected String escapeHTML(String input) {
		String result = input.replaceAll("<", "&lt;");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("\"", "&quot;");
		return result;
	}
	
}
