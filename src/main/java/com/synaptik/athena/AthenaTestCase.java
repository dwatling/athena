package com.synaptik.athena;

import android.app.Activity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

/**
 * @author Dan Watling
 */
public class AthenaTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
	
	public Athena athena;

	public AthenaTestCase(String pkg, Class<T> activityClass) {
		super(pkg, (Class<T>)activityClass);
	}
	
	public void setUp() {
		athena = new Athena(getInstrumentation(), getActivity());
//		athena.buildViewList(getActivity().getWindow().getDecorView());
		athena.itc = this;
	}
	
	public void tearDown() {
		getActivity().finish();
		athena.i.finish(0, new Bundle());
	}

}
