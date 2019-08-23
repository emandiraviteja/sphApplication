# Features implemented

* Displaying both year and annual volumes per card.
* Displaying a clickable down image inside year card when ever the quarter in that year is down compared to previous quarter.
* Implemented offline caching of the data.
* Detecting network availability and displaying snack-bar notifications as needed.
* Handling errors by showing snack-bar notifications with relevant description.

# Tests implemented
* Testing various exceptions related to NULL, IO, JSON parsing.
* Testing whether declined quarter is properly marked or not inside YearListItem.
* UI testing of existence of relevant cards.
* Testing offline cache, no cache conditions.
Note: Code coverage reported are generated only for UI tests.

# Steps to run the UI tests
Clone the project and open it in Andorid Studio.
Open terminal and execute following command to start running ui tests and to generate coverage report.

    .\gradlew createDebugCoverageReport

On completion of the execute of above command coverage report will be avaialble at this path inside project folder.

    app\build\reports\coverage\debug\index.html

Right now UI tests cover about 76% of the code.

![alt text](https://raw.githubusercontent.com/emandiraviteja/sphApplication/master/docs/coverage.PNG)