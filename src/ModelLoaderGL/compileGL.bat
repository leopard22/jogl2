@echo off
echo Compiling %1 with JOGL...
javac -classpath "d:\jogl\jogl.jar;d:\jogl\gluegen-rt.jar;d:\jogl\OBJLoader.jar;." %1
echo Finished.