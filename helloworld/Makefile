.PHONY: all build test clean

JAVAC = javac
JAVA = java
SRCDIR = src
TESTDIR = test
BUILDDIR = build
CLASSPATH = $(BUILDDIR)
JUNIT_JAR = junit-platform-console-standalone-1.11.0.jar

all: build

build: $(BUILDDIR)
	$(JAVAC) -d $(BUILDDIR) $(SRCDIR)/HelloWorldApplication.java

$(BUILDDIR):
	mkdir -p $(BUILDDIR)

test: build
	$(JAVAC) -d $(BUILDDIR) -cp $(CLASSPATH):$(JUNIT_JAR) $(TESTDIR)/HelloWorldApplicationTest.java
	$(JAVA) -jar $(JUNIT_JAR) --class-path $(CLASSPATH) --select-class HelloWorldApplicationTest

run: build
	$(JAVA) -cp $(CLASSPATH) HelloWorldApplication

clean:
	rm -rf $(BUILDDIR)