# SC2002 Object Oriented Design and Programming AY2022-23
## SS10 Group 3

### SC2002 Project Moblima

### Welcome Message
___
Welcome to Group 3's OODP Project. This project allowed our team to venture beyond the syllabus of this course to gain insightful skills and knowledge by applying what we learnt over the semester. 

We would also like to thank our Teaching Assistant Xu Shifeng for teaching us what to take note and look out for over the semester during our lab sessions.
___

# Group 3 Members
- Gan Hao Yi
- Chew Zhi Qi
- Tan Jue Lin
- Eddy Cheng Kuan Quan
- Sanskkriti Jain

___
## Project Description
This application is used as a system to process online booking, purchase of movie tickets, listing of movies and sales reporting.

Cinema staff will be able to manage the movie booking process and configure system settings.

___
### Requirements:
#### Must Have:
Cineplexes.txt with the following format: 
Moblima JEM|APA:1:Premium,ARB:2:Regular,APC:3:Premium,ARD:4:Regular
- Where the Cineplex name will be in front of the '|', followed by the Cinema Code:Cinema Name:Type of Cinema (Premium/Regular)

admin.txt with the following format:
ant|WZRHGrsBESr8wYFZ9sx0tPURuZgG2lmzyvWpwXPKz8U=
- Where the admin username will be entered in the format: "admin/" + the username in front of the |, followed by the password that was hashed in SHA256. The default password is "12345".

TicketPrice.txt
- Refer to src/DataStorage/TicketPrice.txt and TicketCharges.java for the format and layout of the ticket prices.

env.txt
- Refer to src/DataStorage/env.txt for the format of the environment variables.


___
### Additional Features:
- The admin password is hashed to improve security of the system.
- Premium / Regular types of Cinemas are introduced, and it affects the ticket price as well as the number of seats and seating layout available.
- Current Date / Time affects what movie is listed for customers, for example when the movie release date is within 7 days of current date, the showing status will update at 12a.m. from "Coming Soon" to "Preview". When the movie's release date is past the current date, the showing status will update to "Now Showing".
- The admin will be able to see movies that are "Coming Soon" and "End Of Showing" while customers / guests cannot.
- Show Time for movies will not be shown when the time has passed to book the ticket.
