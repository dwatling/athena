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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AthenaTestSuite {
	public String className;
	public List<AthenaTest> tests;
	
	public AthenaTestSuite(String className) {
		this.className = className;
		tests = new ArrayList<AthenaTest>();
	}
	
	protected String getPackage() {
		return className.substring(0, className.lastIndexOf("."));
	}
	
	protected String getClassName() {
		return className.substring(className.lastIndexOf(".")+1);
	}
	
	protected int getErrors() {
		int result = 0;
		for (AthenaTest test : tests) {
			if (test.result.error != null) {
				result ++;
			}
		}
		return result;
	}
	
	protected int getFailures() {
		int result = 0;
		for (AthenaTest test : tests) {
			if (test.result.failure != null) {
				result ++;
			}
		}
		return result;
	}
	
	protected long getTime() {
		long result = 0;
		for (AthenaTest test : tests) {
			if (test.result.failure != null) {
				result += test.result.time;
			}
		}
		
		return result;
	}
	
	protected String getTimestamp() {
		String result = "";
		long start = Long.MAX_VALUE;
		for (AthenaTest test : tests) {
			if (test.result.start < start) {
				start = test.result.start;
			}
		}
		
		if (start == Long.MAX_VALUE) {
			// This means there were no tests in the suite. Set it to a reasonable 
			// value
			start = System.currentTimeMillis();
		}
		
		Date d = new Date(start);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		result = sdf.format(d);
		
		return result;
	}
	
	public void populateTests(File classFile) throws IOException {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(classFile);
			br = new BufferedReader(fr);
			
			String line = br.readLine();
			while (line != null) {
				if (line.contains("void test")) {
					String testName = getTestName(line);
					AthenaTest test = new AthenaTest();
					test.name = testName;
					tests.add(test);
				}
				line = br.readLine();
			}
		} finally {
			if (br != null) { br.close(); }
		}
	}
	
	public String getTestName(String line) {
		String result = line.substring(line.indexOf(" test")+1);
		for (int i = 0; i < result.length(); i ++) {
			char ch = result.charAt(i);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				result = result.substring(0, i);
				break;
			}
		}
		return result;
	}
	
	
	public String toXML() {
		StringBuilder result = new StringBuilder();
		result.append("\t<testsuite errors=\"").append(getErrors()).append("\" failures=\"").append(getFailures()).append("\" name=\"").append(getClassName()).append("\" package=\"").append(getPackage()).append("\" tests=\"").append(tests.size()).append("\" time=\"").append(getTime()).append("\" timestamp=\"").append(getTimestamp()).append("\">\n");
		result.append("\t\t<properties/>\n");
		for (AthenaTest test : tests) {
			result.append(test.result.toXML(className));
		}
		result.append("\t\t<system-out>\n");
		result.append("\t\t</system-out>\n");
		result.append("\t\t<system-err>\n");
		result.append("\t\t</system-err>\n");
		result.append("\t</testsuite>\n");
		
		return result.toString();
	}
}
