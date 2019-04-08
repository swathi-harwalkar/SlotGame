# SlotGame

1. Place the Test_Task.html file under the executables folder like "../src/test/resources/executables/Test_Task.html"
OR specify a "testURL" property at config.properties file with complete path to the html file and uncomment driver.get(Config.getProperty("testURL")); in BaseTestApplication.java file

2. Specify the value of "browser" ie "chrome/firefox/edge" in the config.properties file.

3.Find the testng.xml file "..\src\test\resources\runner\testng.xml" and Run As TestNG Suite

4. Find the results under test-output folder "..\test-output\emailable-report.html" or "\test-output\index.html

5. Find the app.log file for logs under root.
