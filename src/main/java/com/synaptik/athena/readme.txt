Athena v0.1
-----------

Athena is a simple TestRunner meant to be run on an Android device. It will output
test results in an XML format digestible by Hudson.

In order to use Athena, you will need to do the following:

1) Copy athena-0.1.jar to your Android Test project's lib folder.
2) Modify your AndroidManifest.xml file to use com.synaptik.athena.AthenaTestRunner instead of
   com.android.test.InstrumentationTestRunner.
3) Add an AllTests.java file to the root of your test package. This file will need
   to extend AthenaTestCase and implement getTestClasses. getTestClasses should
   return a list of all of the test classes you would like to include in the test
   run.
4) To kick off your test run, use the following command-line (adding your test package where necessary):
   adb shell am instrument -w <PACKAGE>/com.synaptik.athena.AthenaTestRunner
   
   Likely you will want to pipe the results to TEST-all.xml and configure Hudson
   to ingest that file.
   
The source code is provided as-is and is under an LGPL license.
