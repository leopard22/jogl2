package ModelLoaderGL;
// ModelLoaderGLListener.java
// Andrew Davison, November 2006, ad@fivedots.coe.psu.ac.th

/* Display a loaded OBJ model, scaled and centered, 
   and possibly rotating.
*/

import java.text.DecimalFormat;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import OBJLoaderGL.OBJModel;

//import OBJLoaderGL.OBJModel;

public class ModelLoaderGLListener implements GLEventListener 
{
  private static final float INCR_MAX = 0.45f;   // for rotation increments
  private static final double Z_DIST = 7.0;      // for the camera position

  private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp

  private GLU glu;

  private String modelName;
  private OBJModel model;
  private float maxSize;
  private boolean doRotate;

  // rotation variables
  private float rotX, rotY, rotZ;     // total rotations in x,y,z axes
  private float incrX, incrY, incrZ;  // increments for x,y,z rotations



  public ModelLoaderGLListener(String nm, float sz, boolean r)
  { 
    modelName = nm;
    maxSize = sz;
    doRotate = r;
  } // end of ModelLoaderGLListener



  public void init(GLAutoDrawable drawable) 
  // perform start-up tasks
  {
    GL2 gl = drawable.getGL().getGL2(); 
    glu = new GLU();

    // gl.setSwapInterval(0);   
       /* switches off vertical synchronization, for extra speed (maybe) */

    // initialize the rotation variables
    rotX = 0; rotY = 0; rotZ = 0;
    Random random = new Random();
    incrX = (0.5f +random.nextFloat()/2)*INCR_MAX;   // INCR_MAX/2 - INCR_MAX degrees
    incrY = (0.5f +random.nextFloat()/2)*INCR_MAX; 
    incrZ = (0.5f +random.nextFloat()/2)*INCR_MAX; 
    
    gl.glClearColor(0.4f, 0.65f, 0.92f, 1.0f);  
                  // sky colour background for GLCanvas
    
    // z- (depth) buffer initialization for hidden surface removal
    gl.glEnable(GL.GL_DEPTH_TEST);
    
    gl.glShadeModel(GL.GL_LINE_SMOOTH);    // use smooth shading
    addLight(gl);
    // load the OBJ model
    model = new OBJModel(modelName, maxSize, gl, true);
  } // end of init()


  private void addLight(GL2 gl)
  // two white light sources 
  {
    // enable light sources
    gl.glEnable(GL2.GL_LIGHTING);
    gl.glEnable(GL2.GL_LIGHT0);
    gl.glEnable(GL2.GL_LIGHT1);

    float[] whiteLight = {1.0f, 1.0f, 1.0f, 1.0f};  // bright white

    float lightPos[] = {10.0f, 10.0f, -10.0f, 1.0f}; 
       // light located at the right, top, and back 
       // light 0 has white ambient, diffuse, and specular components by default
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
    
    float lightPos1[] = {-10.0f, -10.0f, 10.0f, 1.0f};
       // light located at the left, bottom, and front 
    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, whiteLight, 0);  // diffuse white
    gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos1, 0);
	  /*gl.glClearColor(0.8f, 0.5f, 0.0f, 1.0f);
	  
      gl.glEnable(GL2.GL_DEPTH_TEST);
      gl.glClearDepth(1.0f);
     
      gl.glShadeModel(GL2.GL_SMOOTH);
      gl.glEnable(GL2.GL_LIGHTING);

      gl.glEnable(GL2.GL_LIGHT0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light_0_ambient, 0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_0_diffuse, 0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_0_specular, 0);
      gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_0_position, 0);

      gl.glEnable(GL2.GL_COLOR_MATERIAL);
      gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
      gl.glMateriali(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 90);
      gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, material_specular, 0);*/
  }  // end of addLight()


  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
  // called when the drawable component is moved or resized
  {
    GL2 gl = drawable.getGL().getGL2();

    if (height == 0)
      height = 1;    // to avoid division by 0 in aspect ratio below

    gl.glViewport(x, y, width, height);  // size of drawing area 

    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluPerspective(45.0, (float)width/(float)height, 1, 100); // 5, 100); 
              // fov, aspect ratio, near & far clipping planes

    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
  } // end of reshape()


  public void display(GLAutoDrawable drawable) 
  // the model is rotated and rendered
  {
    // update the rotations (if rotations were specified)
    if (doRotate) {
      rotX = (rotX + incrX) % 360.0f;
      rotY = (rotY + incrY) % 360.0f;
      rotZ = (rotZ + incrZ) % 360.0f;
    }

    GL2 gl = drawable.getGL().getGL2();

    // clear colour and depth buffers
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();

    glu.gluLookAt(0,0,Z_DIST, 0,0,0, 0,1,0);   // position camera

    // apply rotations to the x,y,z axes
    if (doRotate) {
      gl.glRotatef(rotX, 1.0f, 0.0f, 0.0f);
      gl.glRotatef(rotY, 0.0f, 1.0f, 0.0f);
      gl.glRotatef(rotZ, 0.0f, 0.0f, 1.0f);
    }

    model.draw(gl);      // draw the model

    gl.glFlush();
  } // end of display


  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, 
                             boolean deviceChanged) 
  /* Called when the display mode or device has changed.
     Currently unimplemented in JOGL */
  {}



@Override
public void dispose(GLAutoDrawable arg0) {
	// TODO Auto-generated method stub
	
}

 
 } // end of ModelLoaderGLListener class

