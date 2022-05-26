# GNU Makefile
JAR=/usr/local/jdk1.8.0_131/bin/jar
JAVA=/usr/local/jdk1.8.0_131/bin/java
JAVAC=/usr/local/jdk1.8.0_131/bin/javac

JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

default: Event LamportClock Main Request

Event: Event.java
	$(JC) Event.java

LamportClock: LamportClock.java
	$(JC) LamportClock.java

Main: Main.java
	$(JC) Main.java

Request: Request.java
	$(JC) Request.java

run:
	java Main 4 2 4 20 5

clean:
	rm -f *.class