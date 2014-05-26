BlackBerry fall sensor manager (2010)
==========

A BlackBerry Java project to interact with a custom designed hardware fall sensor over Bluetooth. The application was to correctly handle multiple sensors, and implement the custom message protocol.

In addition, the design was such that the DataStore and the higher-level components were completely decoupled. Additional DataStore observers could be added easily, potentially at runtime, and could monitor records that had not been delivered yet (they would be notified when it was).

Writeup of the project was presented at IEEE HealthCom 2010. [See the paper](https://github.com/hughobrien/blackberry-fall-sensor-manager/blob/master/thirdparty/HealthCom2010/HealthCom2010.pdf?raw=true) for details.

An emulated version of the hardware device was also created, to ease testing (virtualFallSensor).

The code was then adapted into a teaching tool for undergraduate software engineering students. This is the version here. It hasn't been built in some time, please contact me if it doesn't work.

Demo
----
![demonstration](https://raw.githubusercontent.com/hughobrien/blackberry-fall-sensor-manager/master/thirdparty/HealthCom2010/figures/storm-mod.png)
