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

import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestSuite;
import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.test.AndroidTestRunner;
import android.util.Log;

/**
 * Much of this is based on the InstrumentationTestRunner in the Android SDK.
 * 
 * @author Dan Watling <dan@synaptik.com>
 */
public class AthenaTestRunner extends Instrumentation {
	private static final String LOG_KEY = AthenaTestRunner.class.getCanonicalName();
	private AndroidTestRunner mRunner;
	private Bundle mResults = new Bundle();
	private StringBuilder result = new StringBuilder();
	private TestSuite mTestSuite;
	private List<AthenaTestSuite> suiteResults;
	
	public static final String ARGUMENT_TEST_PACKAGE = "package";
	
	@Override
	public void onCreate(Bundle arguments) {
		Log.d(LOG_KEY, "onCreate called (bundle size=" + arguments.size() + ")");
		for (String key : arguments.keySet()) {
			Log.d(LOG_KEY, key + arguments.get(key));
		}
		super.onCreate(arguments);
		Log.d(LOG_KEY, this.getContext().getPackageName());
		Log.d(LOG_KEY, this.getContext().getClass().getName());
		
		suiteResults = new ArrayList<AthenaTestSuite>();
		
		mTestSuite = buildTestSuite();
		mRunner = new AndroidTestRunner();
		mRunner.setContext(this.getContext());
		mRunner.setInstrumentaiton(this);
		mRunner.setTest(mTestSuite);
		
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		start();
	}
	
	@Override
	public void onStart() {
		Log.d(LOG_KEY, "onStart called");
		super.onStart();
		mRunner.addTestListener(new MyTestListener());
		if (mRunner.getTestCases() != null && mRunner.getTestCases().size() > 0) {
			Log.i(LOG_KEY, "Number of tests: " + mRunner.getTestCases().size());
			mRunner.runTest();
		}
		result.append("<testsuites>\n");
		for (AthenaTestSuite suite : suiteResults) {
			result.append(suite.toXML());
		}
		result.append("</testsuites>\n");
		mResults.putString(Instrumentation.REPORT_KEY_STREAMRESULT, result.toString());		
		
		finish(Activity.RESULT_OK, mResults);
	}
	
	@Override
	public void onDestroy() {
		Log.d(LOG_KEY, "onDestroy called");
		super.onDestroy();
	}
	
	@Override
	public boolean onException(Object obj, Throwable e) {
		Log.d(LOG_KEY, "onException called");
		return super.onException(obj, e);
	}
	
	private TestSuite buildTestSuite() {
		TestSuite result = new TestSuite();
		/** @TODO Need to retrieve list of all test classes from context package path instead of requiring AllTests**/
		String allTestsClass = this.getContext().getPackageName() + ".AllTests";
		Class<AthenaTestCase> clazz = null;
		try {
			clazz = (Class<AthenaTestCase>)Class.forName(allTestsClass);
			List<Class> classes = clazz.newInstance().getTestClasses();
			for (Class c : classes) {
				result.addTestSuite(c);
			}
		} catch (ClassNotFoundException e) {
			Log.e(LOG_KEY, "Class not found " + allTestsClass + ". You need to specify this in order to use AthenaTestRunner.");
		} catch (IllegalAccessException e) {
			Log.e(LOG_KEY, "Class not found " + allTestsClass + ". You need to specify this in order to use AthenaTestRunner.");
		} catch (InstantiationException e) {
			Log.e(LOG_KEY, "Class not found " + allTestsClass + ". You need to specify this in order to use AthenaTestRunner.");
		}
		
		Log.i(LOG_KEY, "Name: " + result.getName());
		Log.i(LOG_KEY, "Number of test cases: " + result.countTestCases());
		Log.i(LOG_KEY, "Test count: " + result.testCount());
		return result;
	}
	
	protected class MyTestListener implements TestListener {
		protected long start;
		protected long end;
		protected Throwable error;
		protected AssertionFailedError failure;
		
		public void addError(Test test, Throwable t) {
			this.error = t;
		}

		public void addFailure(Test test, AssertionFailedError t) {
			this.failure = t;
		}
		
		public void endTest(Test test) {
			this.end = System.currentTimeMillis();
			addToResults(test, this.start, this.end, this.error, this.failure);
		}

		public void startTest(Test test) {
			this.end = -1;
			this.error = null;
			this.failure = null;
			this.start = System.currentTimeMillis();
		}
	}
	
	protected void addToResults(Test t, long start, long end, Throwable error, AssertionFailedError failure) {
		String className = AthenaTestResult.getClassNameFromString(t.toString());
		String testName = AthenaTestResult.getTestNameFromString(t.toString());
		AthenaTestSuite suite = getOrCreateSuite(className);
		AthenaTestResult testResult = new AthenaTestResult(testName, start, end, error, failure);
		suite.results.add(testResult);
	}
	
	protected AthenaTestSuite getOrCreateSuite(String className) {
		AthenaTestSuite result = null;
		
		for (AthenaTestSuite suite : suiteResults) {
			if (className.equals(suite.className)) {
				result = suite;
				break;
			}
		}
		
		if (result == null) {
			result = new AthenaTestSuite(className);
			suiteResults.add(result);
		}
		return result;
	}
	
}
