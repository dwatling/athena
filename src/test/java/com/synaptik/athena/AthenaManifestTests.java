package com.synaptik.athena;

import junit.framework.TestCase;
import java.io.File;

public class AthenaManifestTests extends TestCase {
	Athena myAthena;
	protected void setUp() throws Exception {
		super.setUp();
		myAthena = new Athena();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testFindPackage() throws Exception {
        File root = new File("testData");
        System.out.println(root.getAbsolutePath());
		assertEquals(myAthena.getPackageNameFromManifest(root), "package=\"fi.vtt.psw.tests\"");
	}
	

}
