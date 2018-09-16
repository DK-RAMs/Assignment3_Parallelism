# Parallelism_Assignment programs Makefile
# Dikabelo Ramashala
# 12th September 2018
# RMSDIK001

JAVAC = javac
JFLAGS = -g
SRC = src/
MAIN = main/
CLASSES = classes/
DOC = doc/
BIN = bin/
JAVADOC = javadoc

.SUFFIXES: .java .class

.java.class:
	$(JAVAC) -cp .:./bin -d $(BIN) $(JFLAGS) $*.java
	
CLASSES = \
	$(SRC)$(CLASSES)Tree.java\
	$(SRC)$(CLASSES)Calculator.java\
	$(SRC)$(MAIN)BotanyAnalyser.java\
	
default: classes
		memaloc

classes: $(CLASSES:.java=.class)

memaloc: java -Xmx256m $(BIN)BotanyAnalyser.class

doc: $(JAVADOC) $(SRC)$(CLASSES) -d $(DOC)

clean:
	$(RM) $(BIN)*.class