 Athena v1.1

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

Athena is a simple command-line utility that will run your Android test project and output
the test results in an XML format digestible by Hudson (and probably any other CI application).

In order to use Athena, you will need to do the following:

1) Have Java 1.6+ installed.
2) Place the contents of the dist/ folder on your system path.
3) Modify your build script to run the athena.bat file or athena file (depending
   on your system).
4) Point Hudson or your CI of choice to TEST-all.xml which will be found at the
   root of the project you are running this for.  
   
The source code is provided as-is and is under an Apache 2.0 license.

---

USAGE: athena <root of Android test project>
Example: athena ~/projects/my_proj_test

---


WHAT'S NEW

v1.0
- Completely rewritten. No longer requires you to modify your Android test project in anyway.
  No need for AllTests.java. 

