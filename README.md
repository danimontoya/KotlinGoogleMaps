# Kotlin - Google Maps

TechStack: Clean Architecture, MVVM, Dagger2, Coroutines, LiveData, Arrow, Retrofit and unit tests.

The challenge: Display some vehicles in google maps
Task 1: Loading data and displaying it
Write an app containing a list. The list should show all the vehicle data in the bounds of Hamburg (53.694865, 9.757589 & 53.394655, 10.099891).
Use this endpoint to get the vehicles:
https://fake-poi-api.mytaxi.com/?p1Lat={Latitude1}&p1Lon={Longitude1}&p2Lat={Latitude2}&p2Lon={Longit ude2}

Fill the list items with some useful vehicle-information provided by the JSON response. You can create custom list items with selected vehicle data - simply impress us
Make the app look neat and structure the code appropriately.
Hint: You are encouraged to use libraries if you consider them useful. Please document the sources of code you did not write yourself (if they are not listed in the gradle dependencies).

Task 2: Google Maps showing the vehicles
Implement a map Activity/Fragment with Google Maps. Show all available vehicles on the map. Use the bounds of the map to request the vehicles.
The map should zoom and center on a specific vehicle when it is selected in the previously implemented list.

Additional requirements
Please attach a working .apk when submitting your code.
Don't use any Android Studio preview or beta version when creating your test. Please use the stable version only.
Please name your zip-file with your full name
