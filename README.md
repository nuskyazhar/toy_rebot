# Toy Robot Challenge

**How do I behave**

 - I have 5 by 5 square table
 - I support below commands
	 - **PLACE X,Y,DIRECTION** 
		 - X,Y should be valid coordinates on the table
		 - Direction should be either **SOUTH,WEST,NORTH** or **EAST**
		 - Used to place the robot on the table
	- **LEFT**
		- Used to turn the robot to left side
	- **RIGHT**
		- Used to turn the robot to right side
	- **MOVE**
		- Used to move the robot one step forward in the direction it is currently facing
		- If the the new position is not valid for the table, nothing will happen
	- **REPORT**
		- Used to print current coordinates and the direction of the robot
	- **EXIT**
		- Used to exit from the program
 - I have to be started with a valid `PLACE` command

# How to run me

 - You should have `maven` and `java 8+` installed
 - Navigate to the directory where you see the pom.xml file (which is as same as the location of this file)
 - Execute below commands
	 - `mvn clean package`
	 - `mvn exec:java -Dexec.mainClass="com.nusky.iress01.BotApp"`
 - Alternatively, you can simply open the project in an IDE like Intellij and Run the `com.nusky.iress01.BotApp` class