# traveltracker
Travel Tracker for CS 345

This is a readme. As the title suggests, you should read me.

## Database Notes
The database is written in SQLite, any queries to that database should be done using Java's documentation on incorporating SQLite.
If there are any issues accessing the database check the documentation on Xerial/JDBC. 
https://github.com/xerial/sqlite-jdbc

If you're running into an issue where they can't find the JDBC class, you simply have to find the "sqlite-jdbc-3.32.3.2.jar" in the root repositry of the Git. Once you have this jar library file you need to add it to your project's working library through your IDE's method of adding jar files.

If you receive a "SQLite Database is Locked" error, it's because you haven't closed your last connection to the database before creating a new one. db.close()

When querying the database you should use executeQuery() paired with a ResultSet object, when altering the database you should use executeUpdate(). This is 
because the ResultSet object can store the information that you queried, allowing you to operate on the data. This can then be put back into the database 
with an executeUpdate(). The executeUpdate() doesn't need an object because you don't store anything in the program from that command, it's a way to push
your changes to the database.

## Sprint 1
* Goal: User should be able to search/find locations and add/remove them from their travel log.
    - [Class Diagram](https://drive.google.com/file/d/11yCjMl8PNKK4R_5L2aCVjqPbpJA9rFeN/view?usp=sharing) (click on 'Sprint 1 - Class Diagram' tab on the bottom)
    - [Review Agenda](https://docs.google.com/document/d/1bs-s56xOaBFBxPl2S9CGJ1s0Qm7sTqiuuwDVgVT6zbw/edit?usp=sharing)
    - [Retrospective](https://docs.google.com/document/d/1gwihNZGYR9QlfwOC5aEMi2xGmAw6mYUqKJyXcgVB_3s/edit?usp=sharing)

## Sprint 2
* Goal: Admin will be able to create a database that holds locations and User will be able to create their favorite locations
    - [Class Diagram](https://drive.google.com/file/d/11yCjMl8PNKK4R_5L2aCVjqPbpJA9rFeN/view?usp=sharing) (click on 'Sprint 2 - Class Diagram' tab on the bottom)
