JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Robot.java\
	RobotsMaker.java\
	Dust.java\
	Room.java\
	Simulation.java\
	App.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class