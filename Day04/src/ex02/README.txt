# you may rename this file into Makefile and run it with "make"

all:
	mkdir -p target
	# build applicaiton
	javac -cp lib/JCDP-2.0.1.jar:lib/jcommander-1.82.jar -d target src/java/edu/school21/printer/*/*/*.java src/java/edu/school21/printer/*/*.java
	# copy libraries into project files
	cd target; jar xf ../lib/JCDP-2.0.1.jar com; jar xf ../lib/jcommander-1.82.jar com; cd ..
	# copy resources
	cp -r src/resources target/
	# delete old jar
	rm -rf target/images-to-chars-printer.jar
	# create jar file
	jar -cfm target/images-to-chars-printer.jar src/manifest.txt -C target .
	# run application
	java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN