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

import junit.framework.Assert;
import android.app.Activity;
import android.app.Instrumentation;
import android.inputmethodservice.KeyboardView;
import android.os.SystemClock;
import android.test.InstrumentationTestCase;
import android.test.TouchUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class Athena {
	Instrumentation i;
	Activity a;
	InstrumentationTestCase itc;
	
	public Athena(Instrumentation instrumentation, Activity activity) {
		this.i = instrumentation;
		this.a = activity;
	}
	
	View getView(int id) {
		View result = a.findViewById(id);
		Assert.assertNotNull("Unable to find view: " + id, result);
		return result;
	}
	public void click(int id) {
		clickView(getView(id));
	}
	
//	void buildViewList(View v) {
//		views.put(v.getId(), v);
//		if (v instanceof ViewGroup) {
//			ViewGroup vg = (ViewGroup)v;
//			for (int index = 0; index < vg.getChildCount(); index ++) {
//				View child = vg.getChildAt(index);
//				buildViewList(child);
//			}
//		}
//	}
	
	public void printViews() {
		this.printViews(0, this.a.getWindow().getDecorView());
	}
	void printViews(int tab, View v) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < tab; i ++) {
			result.append("\t");
		}
		result.append(String.valueOf(v.getId())).append("=").append(v.getClass().getName());
		Log.i("Athena", result.toString());
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup)v;
			for (int index = 0; index < vg.getChildCount(); index ++) {
				View child = vg.getChildAt(index);
				printViews(tab +1, child);
			}
		}
	}
	
	void clickView(View v) {
//		TouchUtils.tapView(itc, v);
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		float x = (float)location[0] + ((float)v.getWidth() / 2.0f);
		float y = (float)location[1] + ((float)v.getHeight() / 2.0f);
		
		MotionEvent click = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0);
		i.sendPointerSync(click);
		
		MotionEvent release = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0);
		i.sendPointerSync(release);
		i.waitForIdleSync();
	}
	
	public String getText(int id) {
		TextView view = (TextView)getView(id);
		return String.valueOf(view.getText());
	}
	
	public void enterText(int id, String text) {
		View v = getView(id);
//		clickView(v);
		v.requestFocus();
		
		i.sendStringSync(text);
	}
}
