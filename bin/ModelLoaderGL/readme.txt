
Chapter 17. Picking on the Models

From:
  Pro Java 6 3D Game Development
  Andrew Davison
  Apress, April 2007
  ISBN: 1590598172 
  http://www.apress.com/book/bookDisplay.html?bID=10256
  Web Site for the book: http://fivedots.coe.psu.ac.th/~ad/jg2

Contact Address:
  Dr. Andrew Davison
  Dept. of Computer Engineering
  Prince of Songkla University
  Hat Yai, Songkhla 90112, Thailand
  E-mail: ad@fivedots.coe.psu.ac.th


If you use this code, please mention my name, and include a link
to the book's Web site.

Thanks,
  Andrew


==================================
ModelLoaderGL

ModelLoaderGL is a viewer for Wavefront OBJ models, which
uses the OBJLoader package. It automatically centers and 
resizes the model, and rotates it. 


What's Here?
------------
- 2 Java files:
  ModelLoaderGL.java, ModelLoaderGLListener.java

- 2 batch files:
  compileGL.bat and runGL.bat

- a models/ directory
  It contains OBJ models for the viewer.

  The viewer should be supplied with the OBJ file name, minus
  its .obj extension.

    * a colored barbell: barbell.obj, barbell.mtl
    * a colored cube:    colorCube.obj, colorCube.mtl
    * a red couch:       couch.obj, couch.mtl
    * a helicopter:      heli.obj, heli.mtl
    * a chess pawn:      pawn.obj, pawn.mtl
    * a penguin:         penguin.obj, penguin.mtl, penguin.gif
           

==================================
Requirements:

* J2SE 5.0 or later from http://java.sun.com/j2se/
  (I use its nanosecond timer, System.nanoTime()).
  I recommend the release version of Java SE 6.

* JOGL, the JSR-231 1.1.0 release candidate 2, from 
  https://jogl.dev.java.net/
  See the readme.txt file in the top-level directory for 
  details on installing it. 

* The batch files in this directory, compileGL.bat and runGL.bat
  assume that the JOGL JAR and DLLs are in d:\jogl, 
  and that the OBJLoader package is also in d:\jogl,
  stored as the JAR OBJLoader.jar

-----

Compilation: 

1. Use the compileGL.bat batch file.

   $ compileGL *.java

-----
Execution: 

Use the runGL.bat batch file, with the name of the program,
ModelLoaderGL, and the name of an OBJ file in models\.

e.g. 
   $ runGL ModelLoaderGL pawn
       // loads pawn.obj and its MTL file, pawn.mtl, from models\

   $ runGL ModelLoaderGL penguin

Optional arguments can be given to change the model's
rendering size and to switch off the rotation. 
The default model size is 4.0f units.

e.g.
   $ runGL ModelLoaderGL couch 5.0f
       // render the couch a bit bigger than the default size

   $ runGL ModelLoaderGL heli -nr
       // -nr means "no rotation"

   $ runGL ModelLoaderGL colorCube 1.0f -nr

-----------
Last updated: 4th March 2007