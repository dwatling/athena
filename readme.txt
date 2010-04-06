 Athena

 Copyright 2010 Synaptik Solutions
 
 Licensed under the Apache License, Version 2.0 (the "License"); 
 you may not use this file except in compliance with the License. 
 You may obtain a copy of the License at 
 
 http://www.apache.org/licenses/LICENSE-2.0 
 
 Unless required by applicable law or agreed to in writing, software 
 distributed under the License is distributed on an "AS IS" BASIS, 
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 See the License for the specific language governing permissions and 
 limitations under the License. 
 
---

Athena is a simple TestRunner meant to be run on an Android device. It will output
test results in an XML format digestible by Hudson (and probably any other CI application).

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
   
The source code is provided as-is and is under an Apache 2.0 license.

---

For instructions on how to generate the 'android:android:1.6' dependency, see ./maven/readme.txt
