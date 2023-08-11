# you may rename this file into Makefile and run it with "make"

all:
	mkdir -p target
	# build applicaiton
	javac -d target/ src/java/edu/school21/printer/*/*.java
	# copy resources
	cp -r src/resources target/
	# create jar file
	jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C ./target .
	# run application
	java -jar target/images-to-chars-printer.jar 
