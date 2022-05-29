# Makefile for CSC2002S Assignment 1
# Cameron Rebelo RBLCAM001
# 15 August 2021

JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES= outArr.class medianThread.class medianFilter.class
         
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

runFilter: $(CLASS_FILES)
	java  -cp $(BINDIR) medianFilter