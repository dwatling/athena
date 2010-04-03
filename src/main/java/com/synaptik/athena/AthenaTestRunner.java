package com.synaptik.athena;

import java.util.HashMap;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;
import junit.framework.TestResult;
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
	private HashMap<Class<? extends TestCase>, List<TestResult>> results = new HashMap<Class<? extends TestCase>, List<TestResult>>();
	
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
		result.append("<testsuite>\n");
		if (mRunner.getTestCases() != null && mRunner.getTestCases().size() > 0) {
			Log.i(LOG_KEY, "Number of tests: " + mRunner.getTestCases().size());
			mRunner.runTest();
		}
		result.append("</testsuite>\n");
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
		/** Need to retrieve list of all test classes from context package path **/ 
//		result.addTestSuite(DanTest.class);
		
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
		
		@Override
		public void addError(Test test, Throwable t) {
			this.error = t;
		}

		@Override
		public void addFailure(Test test, AssertionFailedError t) {
			this.failure = t;
		}
		
		@Override
		public void endTest(Test test) {
			this.end = System.currentTimeMillis();
			if (this.error != null) {
				addErrorToResults(test, this.start, this.end, this.error);
			} else if (this.failure != null) {
				addFailureToResults(test, this.start, this.end, this.failure);
			} else {
				addPassToResults(test, this.start, this.end);
			}
		}

		@Override
		public void startTest(Test test) {
			this.end = -1;
			this.start = System.currentTimeMillis();
		}
	}
	
	protected void addErrorToResults(Test t, long start, long end, Throwable error) {
	}
	protected void addFailureToResults(Test t, long start, long end, AssertionFailedError error) {
	}
	protected void addPassToResults(Test t, long start, long end) {
	}
}
