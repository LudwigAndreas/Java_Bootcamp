# you may rename this file into "Makefile" and run it with "make"
# but you have to insert --inputImage flag with specifying path to image

all:
	# Create target folder
	mkdir -p target
	# Build application
	javac -d target src/java/edu/school21/printer/*/*.java
	# Run application
	java -cp target edu.school21.printer.app.Main
