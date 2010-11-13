package com.synaptik.athena;

import junit.framework.TestCase;

public class AthenaTestResultTests extends TestCase {

	public void testParse() throws Exception {
		final String DATA = 
			"junit.framework.AssertionFailedError: Unexpected exception\n" +
			"\tat org.junit.Assert.fail(Assert.java:74)\n" +
			"\tat UnexpectedExceptionTest1.testGet(Unknown Source)\n" +
			"\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n";
		AthenaTestResult atr = new AthenaTestResult();
		atr.parse(DATA, null);
		
		assertEquals("junit.framework.AssertionFailedError", atr.failureClass);
		assertEquals("Unexpected exception", atr.failure);
		assertEquals(DATA, atr.failureException);
	}
	
	public void testParse_NoMessage() throws Exception {
		final String DATA = 
			"junit.framework.AssertionFailedError:\n" +
			"\tat org.junit.Assert.fail(Assert.java:74)\n" +
			"\tat UnexpectedExceptionTest1.testGet(Unknown Source)\n" +
			"\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n";
		AthenaTestResult atr = new AthenaTestResult();
		atr.parse(DATA, null);
		
		assertEquals("junit.framework.AssertionFailedError", atr.failureClass);
		assertEquals("", atr.failure);
		assertEquals(DATA, atr.failureException);
	}
	
	public void testGetTestNameFromString() throws Exception {
		assertEquals("testMyTest", AthenaTestResult.getTestNameFromString("testMyTest(com.synaptik)"));
		assertEquals(null, AthenaTestResult.getTestNameFromString(null));
	}
	
	public void testGetClassNameFromString() throws Exception {
		assertEquals("com.synaptik", AthenaTestResult.getClassNameFromString("testMyTest(com.synaptik)"));
		assertEquals(null, AthenaTestResult.getClassNameFromString(null));
	}
	
	public void testEscapeHTML() throws Exception {
		assertEquals("&lt;&gt;&quot;ABC", AthenaTestResult.escapeHTML("<>\"ABC"));
		assertEquals(null, AthenaTestResult.escapeHTML(null));
	}
}
