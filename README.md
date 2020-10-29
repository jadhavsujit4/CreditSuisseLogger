# CreditSuisseLogger
Logger application as a part of technical challenge

## Technology Used:
Java, Spring Boot, AOP, HSQLDB, Mockito

## Task:
Give an input file which is a log file contains lines of log entries as shown below. This log file contains lines of log entries where a single line denotes either the start or finish of an event. The type of log can be APPLICATION_LOG which tells that the log is produced by Application Server. The task is to read this file and create events.

#### Example contents of logfile.txt:

    {"id":"scsmbstgra", "state":"STARTED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495212}
    {"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495213}
    {"id":"scsmbstgrc", "state":"FINISHED", "timestamp":1491377495218}
    {"id":"scsmbstgra", "state":"FINISHED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495217}
    {"id":"scsmbstgrc", "state":"STARTED", "timestamp":1491377495210}
    {"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495216}

After reading the above file, an event with id "scsmbstgra" can be created. Along with this, the duration of the event is to be stored. Duration can be calculated by taking the difference of the log entries(Finished log timestamp - Started log timestamp). If the duration is more than 4ms, then assign an alert to this event. For the event with id "scsmbstgra", the duration will be 1491377495217 - 1491377495212 = 5ms. The alert for this event would be set as true. Other variables such as type and host if application must be stored in an event. This event must be stored in a HSQLDB.

## Approach
#### Reading Files and processing Logs to form an Event
A file can be read in many ways. But the approach to read the file must include reading reading larger files(file size in GB) without getting the OutOfMemoryError(). To avoid this, we must read the file line by line. Each line must be mapped to a Log Class to create a log object. This Log object is stored in a Map as a value. The key of the map is the Log id. So for the first time, it will check whether the key is already in the map or not. If not, then add the Log object right away. If its present, then it means that a Log object of either "STARTED" or "FINISHED" state (Note that the sequence can be anything. FINISHED state logs can appear before STARTED state logs) is already been found in the log file. If its is found, then the absolute value of the differences in the timestamps is calculated and it is denoted as duration. Event is created by setting the alert value based on this duration value. After this conversion of log to event, the log entry from map is removed. This is done so that memory usage would be taken in consideration.

Note: Cases such as a big log file only containing all "STARTED" or "FINISHED" logs. In this case, forming an event won't be possible. This means that the map will be populated with many values and there can be chances of OutOfMemoryError(). To solve this, we can save the log id in a Set and add that log entry in a map. Only limited values will be added to a map(say 100). When map gets full, few of the values will be removed and saved in a database table. So the next time, we search for a log entry, we first search in the map, and if its availabe then create an event from it and remove the log value from the map. If it is not present in map, then check in the Set, if found, then query the database to get that log object and then create the event and remove the entry from the database. There is a tradeoff in this approach as we are saving unnecessary data in the database; if not used this method, then there are chances of OutOfMemoryError().

#### Saving in DB
From the above approach, we would get a collection of Events that are needed to be stored in the database. This is a one time process and whole list of events will be saved to database in a single go.

Note: We can save event while reading it from the file. But then for saving a single event everytime would hamper the performance of the application.

## Running the application
This is a maven project. 
#### For running test and building a jar
    mvn clean install
    
#### For running the application
    java -jar /jarLocation /LogFileLocation
    
The command must look like this:

    java -jar target/org.creditsuisse-0.0.1-SNAPSHOT.jar /home/sujit/eclipse-workspace/CreditSuisseLogger/src/main/java/com/sujit/creditsuisse/logfile.txt

Note: I have printed the events that are stored in the database at the end of the application. This can be turned of changing the logger property to just display info level logs.
