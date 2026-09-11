# compiling initial .java files into .jar (s)
javac src/main/java/org/example/Main.java
jar cfe result.jar org.example.Main -C src/main/java org/example/Main.class

# running the resulting file
java -jar result.jar

# running test with Gradle
./gradlew test

# generating the documentation from javadoc comments
javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages org.example -protected

# deleting file (s) generated during compilation
rm result.jar
rm src/main/java/org/example/Main.class