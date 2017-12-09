# ConnecTogether

This documentation tracks the progress of our app "Contact App".


## Iteration 3
Iteration 3 Goals Accomplished:
- NFC functionality implemented (able to transfer Contacts)
- Full test suite implemented
- Additional functionality implemented for UI
  - Settings information and functionality
  - My Info functionality fully realized
  - Scan Activity able to access camera
  
Iteration 3 Unfinished:
- Scan in text recognition function
- Optional user backup of data to remote server
- Ability to only share specific information over NFC
- Ability to upload and store different file types
- Search bar functionality for contacts and groups

**Comments:**
All set goals for Iteration 3 were completed and marked accordingly in GitHub. Espresso and Unit tests were written per project specification and implemented accordingly. Note that as in previous iteration, per Damevski, one of the Espresso tests is demonstrated utilizing an uploaded video
Individual responsibilities were as follows: Aaron worked primarily on Espresso/Unit testing and getting the GitHub page set up properly, Shane was responsible for front end UI implementation details, Austin switched between working on NFC functionality and getting the final pieces of the SQLite database set up, and Tal worked exclusively on getting NFC functionality up and running. 
In summary, we finished out Iteration 3 strong and completed all necessary goals. All remaining user stories in GitHub were initially designed to be stretch goals and the ConnecTogether app as a whole is complete and functional. 

## Iteration 2
Iteration 2 Goals Accomplished:
- Full functionality for SQLite database
- Implement test suite for work completed
- Additional functionality for implemented UI
  - Ability to add new contacts
  - Ability to add new attributes to a contact
  - Associate a photo with a contact
- Basic NFC functionality

Iteration 2 Unfinished Extra Functionality (not part of implemented user stories):
- Ability to associate generic files with a Contact
- Search through Contacts screen using Search bar
- Send contact over NFC

**Comments:**
We reached all our goals for Iteration 2. In particular, this included implementing a more complete and robust testing suite. Our Unit Tests focused on testing functionality required in our implemented Scenarios, to reinforce the Instrumentation Tests. Given the nature of our app, we had to adjust the types of tests we ran for submission.
Our app only includes one new Espresso test for this iteration. This is due to the nature of our app, which is primarily a backend-heavy app which does not have many changing UI elements to check against. Additionally, the Android Emulator cannot simulate NFC capabilities. Because of this, we have implemented tests in other ways. We tested the NFC capability manually, using two Android phones, and recorded a video to demonstrate the app's ability to cover all scenarios in the NFC User Story.
Because we could not test against the UI using Espresso tests, we implemented additional Unit Tests beyond the requirements, with 14 total unit tests. This, combined with our one Espresso Test and video demonstration, comprise our testing submission for this iteration. All of these issues have been discussed and okayed by Dr. Damevski. 

We kept the same group roles as last iteration: Aaron worked primarily on Espresso testing and Unit testing, Austin worked on the Database interactions, Shane worked on UI and frontend interaction, and Tal worked on NFC functionality. We all worked to clean the code up, using abstraction principles learned in class and the checkstyle tools to improve the quiality of our codebase.

In summary, we produced much cleaner and more thoroughly tested code this iteration then last. Going forward, we plan to continue this trend of improving the quality of our code while implementing more functionality in the last iteration.

## Iteration 1
Iteration 1 Goals Accomplished:
- UI for Home, My Info, Contacts, and New Contact screens
- Mock UI for Group, Important, and Settings screens
- Instantiate Contacts
-SQLite database to store contacts, groups, etc.
  - note: database is present, not linked into app funcitonality yet

Iteration 1 Goals Unfinished:
- Ability to associate files with a Contact
- Search through Contacts screen using Search bar
- Implement test suite for work completed

**Comments:**
Iteration 1 started September 29, ended October 19.

Summary of Iteration 1: 
Tal worked on implementing NFC functionality to the app. Aaron worked on feature driven design of interface and testing for the app. Shane effectively built the entire UI and infrastructure for the app. Austin was responsible for implementing the entirety of the back end SQLite Database.

As can be seen, the testing suite corresponding to the scenarios was not completed. We were unable to deliver tests for the User Stories dealing with basic NFC Functionality and some basic features dealing with contacts. Our group greatly underestimated how much effort was involved in creating functional Espresso tests, and did not leave enough time at the end of the iteration to implement them fully. In the future, it will be absolutely crucial to start testing earlier than in this iteration. 

In summary, we overshot on the amount of work we should have completed in Iteration one. Going forward, we plan to implement more manageable work loads, and to have a higher quality and more thorough testing done on our implemented code.
