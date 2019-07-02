# InstilledDemo
Instilled demo application.

To build this app in a great IDE, either launch your copy of IntelliJ IDEA, or 
download a 30 day eval here: https://www.jetbrains.com/idea/download  **

To build the project, just select the menu Build->Rebuild Project

To run the app, find the InstilledDemo.jar executable jar in the out/artifacts/InstilledDemo_jar and then execute the app from
a command line like this:

java -jar InstilledDemo.jar 0-1000 2000-3000 2500-4000

To run the application to generate integration JSON data for the vis.js toolkit timeline component, simply add the -outputjson switch
to the command line and run again:

java -jar InstilledDemo.jar -outputjson 0-1000 2000-3000 2500-4000

To preview program output consumed and displayed by the vis.js timeline component, just copy and paste the JSON output
into the text box at the top of this page and press the "Load" button:

https://visjs.org/examples/timeline/dataHandling/dataSerialization.html

** Or, run the ant script included, substituting local locations for your JDK installation, etc.
   Optionally, all jar files for JUnit (the only dependencies for this project) are included here. The source should readily compile
   in any IDE.
