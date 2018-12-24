
# 1595 Scouting App receiver
This is the companion app for the [1595 Scouting App](https://github.com/1595Dragons/Scouting-app). It acts as the reciever for the data sent by the app, & also has the ability to take data & immediately parse it into the scouting_data csv. This uses java & the bluetooth capabilities is made possible by using the [BlueCove java lib](http://bluecove.org).


## Prerequisites & installation
All you need in order for the app to run is:
* A laptop with Bluetooth, & must run Windows (*macOS doesnt support the bluetooth library*)
* The [program itself](https://github.com/1595Dragons/Scouting-app-server/raw/master/1595%20Scouting%20App.zip)

Once the program has been downloaded, simply extract the folder to a location where you will be able to find it easily (like the desktop). The zip file includes the program itself in the form of an executable, & a folder called "*Bundled JRE*". **Do NOT remove these files from the extracted folder**. If you do, the program will not be able to find the bundled JRE, & will be unable to launch as a result.

At this point, it would be ideal to check if the devices you are using to take data are [paired with the computer](https://support.microsoft.com/en-us/help/15290/windows-connect-bluetooth-device) that the program is going to be running on.

Then, make sure bluetooth is enabled & on (otherwise the app will crash instanly), then launch the program. It will create 2 CSVs in the folder, one for pit scouting, & the other for scouting during matches. You should then have a total of 4 items in the folder (The app, the bundled JRE, & the 2 CSVs). 

After this step, everything is setup, & running. Launch the [1595 Scouting App](https://github.com/1595Dragons/Scouting-app) on the devices that you will be using to scout, & enter the MAC Address, which should be displayed on the main window of the receiver. If you entered the address correctly, you should then see the devices name appear in the list of connected devices within the next few seconds. You are then ready to start scouting. Good luck!

## Limitations
This app has not been tested on any Linux, so compatability is not guaranteed. 

The app also supports up to 6 devices to be displayed. More than 6 devices have not been tested, & may break the display. This *shouldnt* affect data though.

## Support
If you run into any issues, please post it to the [issues page](https://github.com/1595Dragons/Scouting-app-server/issues).


## Using the config file
The config file (`config.json`) is the configuration for what kind of data is to be recorded and in what period of the game it corresponds with.


Anything in the `Autonomous` object will correspond to what data is taken during the autonomous period,
anything in the `TeleOp` object will correspond to what data is taken during the teleoperated period, and
anything in the `Endgame` object will correspond to what data is taken during the end game period.



The valid format to be used is:
    `"Header and or name" : "DataType", value...]`


The correct datatypes to be entered are:"
	- Text (for any values that need to be entered as text, such as a string)
	- Number (for any values that need to be entered as numbers, such as counts of objects)
	- Boolean (for true false values, such as having a specific autonomous function)
 	- Boolean group (Like booleans, but are represented as radio buttons. If one thing is true, everything else is false)


With a given data type, there are specific values that need to be used.
For the datatype 'Text', the following value is the default value as a string. This is usually an empty string.
For the datatype 'Number', the following values need to be the default value, the minimum value, the maximum vallue, and by how much the values increase/decrease.
For the datatype 'Boolean', the following value is the default value. Either true or false.
For the datatype 'Boolean group', the following value must be a json object, which contains the name or the header,
followed the default value (either true or false).