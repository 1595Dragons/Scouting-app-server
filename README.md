# 1595 Scouting App receiver
This is the required server software for the [1595 Scouting App](https://github.com/1595Dragons/Scouting-app).


## Installing and whats included
Start by downloading the [zip file](https://github.com/1595Dragons/Scouting-app-server/raw/master/1595%20Scouting%20App.zip) that contains the executable, and the included configuration file.
Once the zip file has finished downloading, unzip the files and store them in some place that you can easily find it.

### Config.json
This is the configuration file that will be used to determine what data fields will be generated, and what data will be collected.
For more information on the proper format to be used for filling this out see [using the config file](#Using-the-config-file).
Be sure to restart the app after edits have been saved, and to keep this file in the same location of the scouting app executable.

If the app cannot find the config it will create a new one, and display an error on the console. At this point you should edit the config, and then restart the app.

### Scouting App.exe
This is the main executable. Once launched it will check for a valid config, and then if a database exists with the proper headers.
After the database check has been performed, and no other errors have occurred, the scouting app will them check if a Bluetooth server can be started.
At this point, the server can be used to scout teams, and if the Bluetooth server was able to start it will also be able to receive data from connected devices.

### Scouting-data.db
All the collected data is stored in a SQLite database named `scouting-data.db`.This file does not exist until the scouting app is launched for the first time.
After the scouting app parses the config file, it then tries to look for a database file.
If one exists, but has the wrong headers, it renames the old database (`scouting-data1.db`), and creates a new database file.

To export the data to a .csv file, launch the scouting app, click *View Data*, and on the Raw data tab select *Export as .csv*.

## Using the config file
The config file (`config.json`) is the configuration for what kind of data is to be recorded and in what period of the game it corresponds with.

Anything in the `Autonomous` object will correspond to what data is taken during the autonomous period,
anything in the `TeleOp` object will correspond to what data is taken during the teleop period, and
anything in the `Endgame` object will correspond to what data is taken during the end game period.

The valid format to be used is:
    `"Header and or name" : ["DataType", values...]`


The correct datatypes to be entered are:

	- Text (for any values that need to be entered as text, such as a string)
	- Number (for any values that need to be entered as numbers, such as counts of objects)
	- Boolean (for true false values, such as having a specific autonomous function)
 	- BooleanGroup (Like booleans, but are represented as radio buttons. If one thing is true, everything else is false)

With a given data type, there are specific values that need to be used. Below is the list of values to be used for each datatype.

### Text
The following value is the default value as a string. This is usually an empty string.

Example: `"Do the thing":["Text", ""]`

### Number
The following values need to be the default value, the minimum value, the maximum value, and by how much the values increase/decrease by.

Example: `"Get the points":["Number", 0, 0, 10, 1]`

### Boolean
The following value is the default value (true or false).

Example: `"Win":["Boolean", true]`

### BooleanGroup
THe following value must be another json object, which contains boolean objects for the follwoing radio buttons. See [the boolean section](#Boolean) for what values to enter.

Example:

	"Climb level":["BooleanGroup", {
		"No climb" : true, 
		"Robot climbs on level 1" : false, 
		"Robot climbs on level 2" : false, 
		"Robot climbs on level 3" : false
	}]

## A note about macOS compatibility and usage.
While the scouting app server is written in java (a cross-platform language), the [Bluetooth library used](https://bluecove.org) does not work on macOS, so you will be limited to just using the server to scout.

Another thing to note is that there is no pre-made executable (.app), so in order to launch the application, you will need to download the git repository, and then execute either `Scouting app with console.sh` or `Scouting app.sh` via the terminal.

## Support
If you run into any issues or bugs, please post it to the [issues page](https://github.com/1595Dragons/Scouting-app-server/issues).