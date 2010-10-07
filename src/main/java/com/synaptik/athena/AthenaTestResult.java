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


public class AthenaTestResult {

	protected String testName;
	protected float time;
	protected long start;
	protected String error;
	protected String errorClass;
	protected String failure;
	protected String failureClass;
	protected String failureException;
	
	public AthenaTestResult() {
	}
	
	public String toXML(String className) {
		StringBuilder result = new StringBuilder();
		result.append("\t\t<testcase classname=\"").append(className).append("\" name=\"").append(testName).append("\" time=\"").append(time).append("\">\n");
		if (failure != null) {
			result.append("\t\t\t<failure type=\"").append(failureClass).append("\" message=\"").append(escapeHTML(failure)).append("\">\n");
			result.append(escapeHTML(failureException)).append("\n");
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
	

	// Do we need to handle errorStream?
	public void parse(String outputStream, String errorStream) {
		final String AFE = "junit.framework.AssertionFailedError";
		final String TIME = "Time:";
		String[] lines = outputStream.split("\n");
		boolean capture = false;
		for (String line : lines) {
			if (capture) {
				if (line.startsWith("\t")) {
					failureException += line + "\n";
				} else {
					capture = false;
				}
			}
			if (line.startsWith(AFE)) {
				failureClass = AFE;
				capture = true;
				failure = line.substring(AFE.length() + 2);
				failureException = line + "\n";
			}
			if (line.startsWith(TIME)) {
				String temp = line.substring(TIME.length()).trim();
				this.time = Float.parseFloat(temp);
			}
		}
	}
	
}
