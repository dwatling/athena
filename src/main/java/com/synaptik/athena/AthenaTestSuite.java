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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AthenaTestSuite {
	public String className;
	public List<AthenaTestResult> results;
	
	public AthenaTestSuite(String className) {
		this.className = className;
		results = new ArrayList<AthenaTestResult>();
	}
	
	protected String getPackage() {
		return className.substring(0, className.lastIndexOf("."));
	}
	
	protected String getClassName() {
		return className.substring(className.lastIndexOf(".")+1);
	}
	
	protected int getErrors() {
		int result = 0;
		for (AthenaTestResult testResult : results) {
			if (testResult.error != null) {
				result ++;
			}
		}
		return result;
	}
	
	protected int getFailures() {
		int result = 0;
		for (AthenaTestResult testResult : results) {
			if (testResult.failure != null) {
				result ++;
			}
		}
		return result;
	}
	
	protected long getTime() {
		long result = 0;
		for (AthenaTestResult testResult : results) {
			if (testResult.failure != null) {
				result += (testResult.end - testResult.start);
			}
		}
		
		return result;
	}
	
	protected String getTimestamp() {
		String result = "";
		long start = Long.MAX_VALUE;
		for (AthenaTestResult testResult : results) {
			if (testResult.start < start) {
				start = testResult.start;
			}
		}
		
		Date d = new Date(start);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		result = sdf.format(d);
		
		return result;
	}
	
	public String toXML() {
		StringBuilder result = new StringBuilder();
		result.append("\t<testsuite errors=\"").append(getErrors()).append("\" failures=\"").append(getFailures()).append("\" name=\"").append(getClassName()).append("\" package=\"").append(getPackage()).append("\" tests=\"").append(results.size()).append("\" time=\"").append(getTime() / 1000.0f).append("\" timestamp=\"").append(getTimestamp()).append("\">\n");
		result.append("\t\t<properties/>\n");
		for (AthenaTestResult tr : results) {
			result.append(tr.toXML(className));
		}
		result.append("\t\t<system-out>\n");
		result.append("\t\t</system-out>\n");
		result.append("\t\t<system-err>\n");
		result.append("\t\t</system-err>\n");
		result.append("\t</testsuite>\n");
		
		return result.toString();
	}
}
