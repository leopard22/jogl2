
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

=============================================
Contents of this Directory

* OBJLoaderGL/
    - the OBJLoader package. The ModelLoaderGL and TourModelsGL
      applications use it, so compile and install it first.

* ModelLoaderGL/
    - a viewer for OBJ models 

* TourModelsGL/
    - a 3D world containing OBJ models, which also illustrates picking,
      3D sound, and fog. The 3D sound uses JOAL. 

=============================================

JOGL Installation

* I downloaded JSR-231 1.1.0 release candidate 2 of JOGL 
  from https://jogl.dev.java.net. I chose the Windows build from 
  January 23rd, jogl-1.1.0-rc2-windows-i586.zip, which contains a lib\ 
  subdirectory holding two JARS (jogl.jar and gluegen-rt.jar) and 
  four DLLs (jogl.dll, gluegen-rt.dll, jogl_awt.dll, and jogl_cg.dll).

* I extracted the lib\ directory, renamed it to jogl\, and stored it 
  on my test machine's d: drive (i.e. as d:\jogl\).
  
---------
Last updated: 4th March 2007