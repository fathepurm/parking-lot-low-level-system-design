# Low Level System Design - Parking lot

### Problem Statement
Design parking lot which can park 2/4 wheeler vehicles, issue parking ticket and based on duration of vehicle parking display parking cost.

### Project usage
- **application.properties** : *This file contains slot and instruction file required to run the application*
- **instructions.ini** : *This file contains the instructions*
- **Execution**
  - Build the project
  ```mvn clean package```
  - Copy the application.properties and instructions.ini to target/ folder
  - Run the application
  ``` java -jar target/parking-lot-0.0.1-SNAPSHOT-jar-with-dependencies.jar```
  - Sample output
  ```
  23:29:34.958 [main] DEBUG com.sd.parking.Initializer - Number of slots: 4
  23:29:34.961 [main] DEBUG com.sd.parking.Initializer - Instruction file: instructions.ini
  23:29:34.984 [main] DEBUG com.sd.handlers.Entry - Updating slots db!
  23:29:34.986 [main] INFO  com.sd.handlers.Entry - Assigned slot 1
  23:29:35.172 [main] INFO  com.sd.handlers.Entry - Created ticket d17c1eed-589c-4b27-abd4-2b8990c5f157 for FOUR_WHEELER vehicle KA03EE8162
  23:29:35.172 [main] DEBUG com.sd.handlers.Entry - Updating ticket db!
  23:29:35.186 [main] ERROR com.sd.handlers.Entry - Duplicate entry, already same vehicle KA03EE8162 is parked in slot 1
  23:29:35.187 [main] DEBUG com.sd.handlers.Entry - Updating slots db!
  23:29:35.189 [main] INFO  com.sd.handlers.Entry - Assigned slot 2
  23:29:35.189 [main] INFO  com.sd.handlers.Entry - Created ticket e851e78e-2a30-4232-8669-a6de5f717347 for FOUR_WHEELER vehicle KA03EE8163
  23:29:35.190 [main] DEBUG com.sd.handlers.Entry - Updating ticket db!
  23:29:35.195 [main] DEBUG com.sd.handlers.Entry - Updating slots db!
  23:29:35.198 [main] INFO  com.sd.handlers.Entry - Assigned slot 4
  23:29:35.199 [main] INFO  com.sd.handlers.Entry - Created ticket 41d8ca43-b7d1-471a-a6c5-a00012609316 for FOUR_WHEELER vehicle KA03EE8164
  23:29:35.199 [main] DEBUG com.sd.handlers.Entry - Updating ticket db!
  23:29:35.203 [main] ERROR com.sd.handlers.Entry - Slot is not available for FOUR_WHEELER vehicle - KA03EE8165
  23:29:35.204 [main] ERROR com.sd.handlers.Entry - Slot is not available for FOUR_WHEELER vehicle - KA03EE8166
  23:29:35.207 [main] INFO  com.sd.handlers.Search - List of slots with free status:
  23:29:35.208 [main] INFO  com.sd.handlers.Search - [Slot: 3, Slot type: TWO_WHEELER]
  23:29:35.208 [main] DEBUG com.sd.handlers.Search - Searching slot for vehicle KA03EE8162
  23:29:35.209 [main] INFO  com.sd.handlers.Search - [Vehicle No.: KA03EE8162 found in slot: 1]
  23:29:35.210 [main] DEBUG com.sd.handlers.Exit - Updating slots db!
  23:29:35.212 [main] INFO  com.sd.handlers.Exit - Exiting vehicle KA03EE8162 from  slot 1
  23:29:35.213 [main] DEBUG com.sd.handlers.Exit - Invalidating ticket
  23:29:35.213 [main] DEBUG com.sd.handlers.Exit - Updating tickets db!
  23:29:35.216 [main] DEBUG com.sd.handlers.Exit - Invalidated ticket for vehicle KA03EE8162
  23:29:35.218 [main] INFO  com.sd.handlers.Exit - Ticket cost for parking FOUR_WHEELER vehicle KA03EE8162 for 3 hours is 600 /- INR
  23:29:35.218 [main] DEBUG com.sd.handlers.Search - Searching tickets for vehicle KA03EE8162
  23:29:35.219 [main] INFO  com.sd.handlers.Search - Tickets list for vehicle No. KA03EE8162 are:
  23:29:35.219 [main] INFO  com.sd.handlers.Search - [Tickets ID: d17c1eed-589c-4b27-abd4-2b8990c5f157, Ticket Time: Thu Dec 30 01:29:34 IST 2021, Ticket Status: Invalid ]
  23:29:35.224 [main] DEBUG com.sd.handlers.Search - Searching tickets for vehicle type 4
  23:29:35.225 [main] INFO  com.sd.handlers.Search - Tickets list for vehicle type 4 are:
  23:29:35.225 [main] INFO  com.sd.handlers.Search - [Tickets ID: d17c1eed-589c-4b27-abd4-2b8990c5f157, Ticket Time: Thu Dec 30 01:29:34 IST 2021, Vehicle No.: KA03EE8162, Ticket Status: Invalid ]
  23:29:35.225 [main] INFO  com.sd.handlers.Search - [Tickets ID: e851e78e-2a30-4232-8669-a6de5f717347, Ticket Time: Thu Dec 30 02:29:35 IST 2021, Vehicle No.: KA03EE8163, Ticket Status: Valid ]
  23:29:35.226 [main] INFO  com.sd.handlers.Search - [Tickets ID: 41d8ca43-b7d1-471a-a6c5-a00012609316, Ticket Time: Thu Dec 30 03:29:35 IST 2021, Vehicle No.: KA03EE8164, Ticket Status: Valid ]
  ```


