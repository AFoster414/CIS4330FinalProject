"# CIS4330FinalProject" Project Title: Sensor Troubleshooting App

Group Number: 2 Team Members: Austin Foster (tul10823@temple.edu), Cory D Cox (tui95979@temple.edu)

Sensor: • Accelerometer • Temperature Sensor (ambient temp. & device temp.) • Sound sensor (speaker/microphone) • Proximity Sensor

Abstract: Almost everyone has a modern phone which uses several sensors at a time to perform various tasks. Many apps rely on these sensors in order to operate and errors occur in these apps, it may be hard to differentiate if it’s a bug in the app or if the sensor is damaged. A damaged sensor can really hinder someone’s experience using mobile apps. Thus, we came up with an app that allows users to monitor multiple sensors all in one app. With this app, users can evaluate sensors apart from mobile apps and be able to troubleshoot their phones, all on their own. Further, IT professionals can use this app to compare with other phones, for an easier way to diagnose and isolate issues with mobile devices.

Scenario: Targets people who experience issues using mobile apps that depend on one or more sensors utilized by mobile devices. Not everyone is tech-savvy or knowledgeable about troubleshooting devices, so this app can be the first step in aiding that process. For example, if someone is experiencing issues with a voice recording app, the user can use this particular app to see if the microphone itself has issues, or the app in question is experiencing a bug. Aiding both experienced and unexperienced mobile device users, this app will help make sure people’s sensors are operating ideally in order to fully experience and enjoy apps that depend on these sensors.

Ground Truths Used:

Accelerometer: 
-Reading is below 9.81 m/s (should always be at least 9.81 bc of gravity) = Not Working Correctly.
Sensor = null? => Sensor is not working at all or device does not have one
-Reading is at least 9.81 m/s? = Working Correctly
-Reading is constantly reading > 130 m/s? = Not Working Correcty (In testing, the highest we could achieve was ~129 m/s from shaking the device).

Temperature: 
-Sensor = null? => Sensor is not working at all or device does not have one

Proximity: Sensor = null? => 
-Sensor is not working at all or device does not have one
-Reading is consistently detecting something close in proximity => Not Working Correctly (In practice, the user will not always have the sensor triggering when using the app)
-Sensor has not been triggered yet? => May not be working, try triggering it!

Microphone: 
-Sensor = null? => Sensor is not working at all or device does not have one
