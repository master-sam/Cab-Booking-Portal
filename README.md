Simple cab booking admin portal

You have to build an inter-city cab management portal to be used as an admin and booking tool.
This portal should be able to :
Register cabs.
Onboard various cities where cab services are provided.
Change current city (location) of any cab.
Change state of any cab. For this you will have to define a state machine for the cab ex: a cab must have at least these two basic states; IDLE and ON_TRIP
Book cabs based on their availability at a certain location. In case more than one cab are available , use the following strategy:
Find out which cab has remained idle the most and assign it.
In case of clash above, randomly assign any cab.
Assumption : a cab once assigned a trip cannot cancel/reject it
 
Other Details :
Input: a snapshot of all cabs with their metadata and location
a List of <Cab_Id, Cab_State, City_Id>
In case the Cab_State is ON_TRIP, the City_Id will be indeterminate
 
Bonus :
Provide insights such as for how much time was a cab idle in a given duration ?
Keep a record of the cab history of each cab. (A cab history could just be a record of what all states a cab has gone through)
Find cities where the demand for cabs is high and the time when the demand is highest.

YouTube explanation and testing:
https://www.youtube.com/playlist?list=PL1MGIY5grCTZv8y4J9677pdo0m3DXW2q8
