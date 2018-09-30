all: rs.jar

rs.jar: RecoSys.class
	jar cf rs.jar RecoSys*.class

RecoSys.class:
	hadoop com.sun.tools.javac.Main RecoSys.java

execute:
	rm -rf out/
	hadoop jar rs.jar RecoSys in out

clean:
	rm -rf *.class *.jar
	rm -rf out/
