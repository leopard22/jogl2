package ModelLoaderGL;
// ModelLoaderGL.java
// Andrew Davison, November 2006, ad@fivedots.coe.psu.ac.th

/* A JFrame contains a JPanel which holds a GLCanvas. The GLCanvas
   displays a loaded OBJ model, which may be rotating. The model
   is scaled and centered at the origin. The scaling is controlled
   by the maxSize value which specifies the maximum size of the
   model's largest dimension.

   The listener for the canvas is ModelLoaderGLListener, and the updates
   to the canvas' display are triggered by FPSAnimator using 
   fixed-rate scheduling.

   The code uses the JSR-231 1.0.0 release build of JOGL, 
   14th September 2006.

   Usage:
      runGL modelGL <OBJ-name> [max-size] [-nr]

   The OBJ name is assumed to be for a file in the "models/" 
   subdirectory, and the ".OBJ" extension is added automatically.

   If a "max-size" value is not specified, then MAX_SIZE is used.
   "-nr" means "no rotation", so the default action is to rotate the
   model.
*/

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileFilter;

import java.awt.*;
import java.awt.event.*; 
import java.io.File;
import java.text.DecimalFormat;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.*;    // for FPSAnimator


public class ModelLoaderGL //extends JFrame
{
  private static int DEFAULT_FPS = 80;

  private static final int PWIDTH = 512;   // initial size of panel
  private static final int PHEIGHT = 512; 

  private static final float MAX_SIZE = 4.0f;  // for a model's dimension
  private JFrame frame;
  private ModelLoaderGLListener listener;
  private FPSAnimator animator;
  private JPanel p;
  private Container c;
  private boolean doRotate = true;
  private JPanel panel_1;

  /**
   * @wbp.parser.entryPoint
   */
  public ModelLoaderGL(String nm, float maxSize, boolean doRotate) 
  {
    //super("ModelLoaderGL");
	  frame = new JFrame("ModelLoaderJOGL");
	  frame.setResizable(false);
	  frame.setSize(800, 600);

    System.out.println("Max model size: " + maxSize);
    System.out.println("Rotating: " + doRotate);

    c = frame.getContentPane();
    //ouvrirOBJ();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	JPanel panel = new JPanel();
	
	JButton button = new JButton("Sauvegarder...");
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Sauvegarder...");
		}
	});
	
	JButton btnNewButton = new JButton("Fermer");
	btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("fermer.");
			
		}
	});
	JButton btnNewButton_1 = new JButton("Ouvrir...\n");
	btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("rotation");
			ouvrirOBJ();
		}
	});
	
	JScrollBar scrollBar = new JScrollBar();
	scrollBar.addAdjustmentListener(new AdjustmentListener() {
		public void adjustmentValueChanged(AdjustmentEvent arg0) {
			System.out.println("changement valeur");
		}
	});
	
	JButton button_1 = new JButton("\u2190");
	button_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("gauche");
			listener.rotateLeft();
		}
	});
	
	JButton button_2 = new JButton("\u2191");
	button_2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("haut");
			listener.rotateUp();
		}
	});
	
	JButton button_3 = new JButton("\u2192");
	button_3.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("droite");
			listener.rotateRight();

		}
	});
	
	JButton button_4 = new JButton("\u2193");
	button_4.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("bas");
			listener.rotateDown();
		}
	});
	
	final JComboBox comboBox = new JComboBox();
	comboBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(comboBox.getSelectedIndex() == 1){
				System.out.println("rouge");
				
			}
			else if(comboBox.getSelectedIndex() == 2){
				System.out.println("bleu");
				
			}
			else if(comboBox.getSelectedIndex() == 3){
				System.out.println("vert");
			}
			else if(comboBox.getSelectedIndex() == 4){
				System.out.println("jaune");
			}
			else if(comboBox.getSelectedIndex() == 5){
				System.out.println("orange");
			}
			else if(comboBox.getSelectedIndex() == 6){
				System.out.println("noir");
			}
			else if(comboBox.getSelectedIndex() == 7){
				System.out.println("gris");
			}
			else if(comboBox.getSelectedIndex() == 8){
				System.out.println("blanc");
			}
		}
	});
	comboBox.setModel(new DefaultComboBoxModel(new String[] {"Choisir couleur...", "Rouge", "Bleu", "Vert", "Jaune", "Orange", "Noir", "Gris", "Blanc"}));
	comboBox.setToolTipText("Choisir couleur...");
	GroupLayout gl_panel = new GroupLayout(panel);
	gl_panel.setHorizontalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(67)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
					.addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap(75, Short.MAX_VALUE))
			.addGroup(gl_panel.createSequentialGroup()
				.addGap(28)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(10)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
							.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
						.addGap(18)))
				.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(36))
	);
	gl_panel.setVerticalGroup(
		gl_panel.createParallelGroup(Alignment.LEADING)
			.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
				.addGap(36)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addComponent(scrollBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createSequentialGroup()
						.addComponent(button_2)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(button_4)
							.addComponent(button_3)
							.addComponent(button_1))
						.addGap(18)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
				.addComponent(btnNewButton_1)
				.addGap(18)
				.addComponent(button)
				.addGap(18)
				.addComponent(btnNewButton))
	);
	panel.setLayout(gl_panel);
	
	panel_1 = new JPanel();
	GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
				.addGap(18)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
					.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(133, Short.MAX_VALUE))
	);
	frame.getContentPane().setLayout(groupLayout);
	
	JMenuBar menuBar = new JMenuBar();
	frame.setJMenuBar(menuBar);
	
	JMenu mnFichier = new JMenu("Fichier");
	menuBar.add(mnFichier);
	
	JMenuItem mntmOuvrir = new JMenuItem("Ouvrir...");
	mntmOuvrir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			ouvrirOBJ();
		}
	});
	mnFichier.add(mntmOuvrir);
	
	JMenuItem mntmOuvrirUrl = new JMenuItem("Ouvrir url...");
	mnFichier.add(mntmOuvrirUrl);
	
	JMenuItem mntmSauvergarder = new JMenuItem("Sauvergarder...");
	mnFichier.add(mntmSauvergarder);
    frame.setVisible(true);

    
  } // end of ModelLoaderGL()


  private JPanel makeRenderPanel(String nm, float maxSize, boolean doRotate)
  /* Construct a GLCanvas in a JPanel, and add a 
     listener and animator. */
  {
    JPanel renderPane = new JPanel(); 
    renderPane.setLayout( new BorderLayout() );
    renderPane.setOpaque(false);
    renderPane.setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

    GLCanvas canvas = new GLCanvas(); 
    //FPSMouseControler = new FPSMouseController();
    //fpsMouseController.setCanvas(mycanvas);
    //listener = null;
    listener = new ModelLoaderGLListener(nm, maxSize, doRotate); 
    canvas.addGLEventListener(listener);

    animator = new FPSAnimator(canvas, DEFAULT_FPS, true); 

    renderPane.add(canvas, BorderLayout.CENTER);
    return renderPane;
  }  // end of makeRenderPanel()


// -----------------------------------------

  public void ouvrirOBJ(){

		String path="";
		  FileFilter objFile = new FileFilter() {
				
				@Override
				public String getDescription() {
					return "Modele OBJ";
				}
				
				@Override
				public boolean accept(File f) {
					if (f.getName().toLowerCase().endsWith("obj") || f.isDirectory()) {
						return true;
					}
					return false;
				}
			};
			JFileChooser choixFichier = new JFileChooser();
			choixFichier.addChoosableFileFilter(objFile);
			choixFichier.setFileFilter(objFile);
			int retour = choixFichier.showOpenDialog(null);
			if (retour == JFileChooser.APPROVE_OPTION) {
				File choix = choixFichier.getSelectedFile();
				path = choix.getAbsolutePath();
				if (path.endsWith(".obj")) {
					// ajout du modelloader
					if(p!= null){panel_1.remove(p);p=null;}
					p = makeRenderPanel(path, MAX_SIZE, true);
					//p.revalidate();
					panel_1.add(p);
					//c.add(p , BorderLayout.CENTER);
					
					/*
					 * ajouter Keylistner & mouselistner
					 * 
					 */
					
					frame.addWindowListener( new WindowAdapter() {
					      public void windowClosing(WindowEvent e)
					      /* The animator must be stopped in a different thread from
					         the AWT event queue, to make sure that it completes before
					         exit is called. */
					      { new Thread( new Runnable() {
					          public void run() {
					            animator.stop();
					            System.exit(0);
					          }
					        }).start();
					      } // end of windowClosing()
					    });

					    //frame.pack();

					    animator.start();
					
					
					
					
					p.revalidate();
					
					
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Type de fichier non pris en charge", "Erreur", 0);
				}
			}
	
  }
  /**
   * @wbp.parser.entryPoint
   */
  public static void main(String[] args)
  {
    if ((args.length < 1) || (args.length > 3))
      System.out.println("Usage runGL modelGL <Obj-name> [max-size] [-nr]");
    else if (args.length == 1)
       new ModelLoaderGL(args[0], MAX_SIZE, true);  
    else if (args.length == 3) {
      float maxSize = getMaxSize(args[1]);
      boolean doRotate = !args[2].equals("-nr");
      new ModelLoaderGL(args[0], maxSize, doRotate);  
    }
    else {   // length is 2, so -nr or max-size arg is present
      if (args[1].equals("-nr"))
        new ModelLoaderGL(args[0], MAX_SIZE, false);  
      else {
        float maxSize = getMaxSize(args[1]);
        new ModelLoaderGL(args[0], maxSize, true); 
      } 
    }
  }  // end of main()


  private static float getMaxSize(String arg)
  {
    float maxSize = MAX_SIZE;
    try {
      maxSize = Float.parseFloat(arg);
    } 
    catch (NumberFormatException e) 
    {  System.out.println(arg + " not a float; using " + MAX_SIZE);  }

    return maxSize;
  }  // end of getMaxSize()

} // end of ModelLoaderGL class
