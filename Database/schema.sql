PRAGMA foreign_keys = ON;
.headers on
.mode csv
.mode column

/*
	permissions is a field that will either be "user" or "admin"
	in theory this could also be a boolean field called "admin"
	Need UNIQUE constraints on email and username
*/

CREATE TABLE accounts(
	accountId INTEGER PRIMARY KEY,
	email TEXT NOT NULL UNIQUE,
	username TEXT NOT NULL UNIQUE,
	password TEXT NOT NULL,
	permissions TEXT NOT NULL
);

/*
	UNIQUE logName/accountID combination required
	don't want the same person to create two logs with the same name
*/

CREATE TABLE travelLogs(
	logId INTEGER PRIMARY KEY,
	accountId INTEGER NOT NULL,
	logName TEXT NOT NULL,
	logDescription TEXT,
	FOREIGN KEY(accountId) REFERENCES accounts,
	UNIQUE(logName, accountId)
);

CREATE TABLE locations(
	locationId INTEGER PRIMARY KEY,
	locationName TEXT NOT NULL,
	locationAddress TEXT NOT NULL UNIQUE
);

CREATE TABLE categories(
	categoryId INTEGER PRIMARY KEY,
	categoryName TEXT NOT NULL UNIQUE
);

CREATE TABLE loggedLocations(
	id INTEGER PRIMARY KEY,
	travelLogId INTEGER NOT NULL,
	locationId INTEGER NOT NULL,
	FOREIGN KEY(travelLogId) REFERENCES travelLogs,
	FOREIGN KEY(locationId) REFERENCES locations
);

/*
	Locations can have many categories and categories can have
	many locations. Which is why we need a separate table to map
	that relationship.
*/

CREATE TABLE locationToCategory(
	id INTEGER PRIMARY KEY,
	locationId INTEGER NOT NULL,
	categoryId INTEGER NOT NULL,
	FOREIGN KEY(locationId) REFERENCES locations,
	FOREIGN KEY(categoryId) REFERENCES categories
);

/*
	Cannot have one account with multiple favorites of the same place,
	hence the unique constraint on the combination
*/

CREATE TABLE favorites(
	favoriteId INTEGER PRIMARY KEY,
	accountId INTEGER NOT NULL,
	locationId INTEGER NOT NULL,
	FOREIGN KEY(accountId) REFERENCES accounts,
	FOREIGN KEY(locationId) REFERENCES locations,
	UNIQUE(accountId, locationId)
);

/*
	Image path will store the location of the image, that will
	be held itself in an external directory
*/

CREATE TABLE pictures(
	pictureId INTEGER PRIMARY KEY,
	accountId INTEGER NOT NULL,
	locationId INTEGER NOT NULL,
	picturePath TEXT NOT NULL UNIQUE,
	FOREIGN KEY(accountId) REFERENCES accounts,
	FOREIGN KEY(locationID) REFERENCES locations,
	UNIQUE(accountId, locationId)
);

INSERT INTO accounts(
	email,
	username,
	password,
	permissions
)
VALUES(
	"admin@gmail.com",
	"admin",
	"admin123",
	"admin"
);
