all: test

run:solver.class
	java -jar solver

solver.class:solver.java
	javac solver.java

clean:
	del solver.class