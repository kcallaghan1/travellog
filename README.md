# traveltracker
Travel Tracker for CS 345

This is a readme. As the title suggests, you should read me.
## DisplayImage.java
* Credit for the code: [Jan Bodnar](https://github.com/janbodnar)
    - [Github Repository](https://github.com/janbodnar/Display-Image.git)
    - [Tutorial Link](https://zetcode.com/java/displayimage/)

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

Link to all Diagrams [here](https://drive.google.com/file/d/11yCjMl8PNKK4R_5L2aCVjqPbpJA9rFeN/view?usp=sharing)

## Sprint 1
* Goal: User should be able to search/find locations and add/remove them from their travel log.
    - [Class Diagram](https://viewer.diagrams.net/?page-id=ZwHcOdonUymgxgGQwOtW&highlight=0000ff&edit=_blank&layers=1&nav=1&page-id=ZwHcOdonUymgxgGQwOtW#G11yCjMl8PNKK4R_5L2aCVjqPbpJA9rFeN)
    - [Review Agenda](https://docs.google.com/document/d/1bs-s56xOaBFBxPl2S9CGJ1s0Qm7sTqiuuwDVgVT6zbw/edit?usp=sharing)
    - [Retrospective](https://docs.google.com/document/d/1gwihNZGYR9QlfwOC5aEMi2xGmAw6mYUqKJyXcgVB_3s/edit?usp=sharing)

## Sprint 2
* Goal: Admin will be able to create a database that holds locations and User will be able to create their favorite locations
    - [Class Diagram](https://viewer.diagrams.net/?page-id=VFEpWD1bOymtlLSVHwUH&highlight=0000ff&edit=_blank&layers=1&nav=1&page-id=VFEpWD1bOymtlLSVHwUH#G11yCjMl8PNKK4R_5L2aCVjqPbpJA9rFeN)
    - [Review Agenda](https://docs.google.com/document/d/1XQOStZZfRXacwk9MZQLphKxcYgYMD51B5Mgd-ofE2Ec/edit?usp=sharing)
    - [Retrospective](https://docs.google.com/document/d/1fRtSQSrhL0Kdo3CSHVXXQ9r7uDCzBrIbZqU2GfFBR58/edit?usp=sharing)
    - [Schema Diagram](https://docs.google.com/document/d/1xuo0ckXY9gpKe9JAxN9M5s7DHg_F3pNvL7Cd_gIhLjc/edit)

## Sprint 3
* Goal: A working database that allows for additions of travel-logs and locations, with the ability to add favorites if time permits.
    - [Class Diagram](https://viewer.diagrams.net/?page-id=HqoNyrA8FzuPfwiXrrQT&highlight=0000ff&edit=_blank&layers=1&nav=1&page-id=HqoNyrA8FzuPfwiXrrQT#G11yCjMl8PNKK4R_5L2aCVjqPbpJA9rFeN)
    - [Powerpoint Presentation](https://docs.google.com/presentation/d/1zWrdNF1j_-SzLQlLL7lWGC7pyn___cdWVYFoR-q6c6g/edit#slide=id.gd3c7dc5a02_0_10)
    - [Review Agenda](https://docs.google.com/document/d/1cA44m_fjAUfupRIhQ98fWUjmZRBBNxapbEgFYXl3oTI/edit?usp=sharing)
    - [Retrospective](https://docs.google.com/document/d/1SVUW7I6fiV9CYjBIdx2pY3S7xlaUOC6fcspvOdiHhms/edit?usp=sharing)
