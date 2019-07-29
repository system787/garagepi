# garagepi
RaspberryPi application to remotely manage a garage door's open/closed state

SpringBoot implementation is probably overkill but I was looking for a security implementation to prevent random people from somehow opening and closing my garage door.

Using Pi4J library to access the GPIO pins on my RaspberryPi3. Will likely implement a hidden Env variable with a specific username and password on the RaspberryPi itself instead of implementing a database and hashing system for authentication.
