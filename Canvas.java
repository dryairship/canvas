import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.swing.colorchooser.*;
import javax.swing.UIManager;
import java.io.*;
/**
 * Canvas by Priydarshi Singh
 * @author Priydarshi Singh
 * @version 1.0
 */
public class Canvas{
    private static JFrame f;
    private static String tool = "peb";
    private static JLabel stb = new JLabel("Welcome to Canvas!!"), usb = new JLabel("                                                                                                                                                                                                       ");
    private static JMenuBar mb = new JMenuBar();
    private static JMenu file = new JMenu("File");
    private static JMenuItem neF = new JMenuItem("New"), opF = new JMenuItem("Open..."), svF = new JMenuItem("Save"), exi = new JMenuItem("Exit");
    private static JMenu colm = new JMenu("Color");
    private static JMenuItem eco = new JMenuItem("Edit Colors");
    private static JMenu edim = new JMenu("Edit");
    private static JMenuItem undo = new JMenuItem("Undo"),redo = new JMenuItem("Redo");
    private static JMenu help = new JMenu("Help");
    private static JPanel atP = new JPanel(new GridLayout(1,2));
    private static JPanel cP = new JPanel();
    private static JMenuItem abo = new JMenuItem("About Paint"),hth = new JMenuItem("Help Topics");
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        f = new JFrame("Canvas");
        try{ f.setIconImage(ImageIO.read(new FileImageInputStream(new File("CanvasData/Canvas.jpg"))));}catch(Exception e){}
        Container cont = f.getContentPane();
        cont.setLayout(new BorderLayout());
        final DrawPad pad = new DrawPad();
        cont.add(pad, BorderLayout.CENTER);
        cont.add(new JScrollPane(pad));
        JPanel panel = new JPanel();
        f.setJMenuBar(mb);
        mb.add(file);
        mb.add(edim);
        mb.add(colm);
        mb.add(help);
        file.add(neF);
        file.add(opF);
        file.add(svF);
        file.addSeparator();
        file.add(exi);
        edim.add(undo);
        edim.add(redo);
        colm.add(eco);
        help.add(abo);
        help.addSeparator();
        help.add(hth);
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        neF.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        opF.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        svF.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        exi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
        eco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        hth.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        abo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JOptionPane.showMessageDialog(null,"Canvas ® \n"
                        +"\n"
                        +"This Paint Program is made by  \n"
                        +"Priydarshi Singh. \n"
                        +"\n","About Canvas",1);
                }});
        hth.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){help(); }});
        JPanel span = new JPanel(new GridLayout(0,2));
        JButton erb = new JButton(new ImageIcon("CanvasData/eras.png")), cbsb = new JButton(new ImageIcon("CanvasData/bsiz.png")), txb = new JButton(new ImageIcon("CanvasData/text.png")),
        recb = new JButton(new ImageIcon("CanvasData/rect.png")), brub = new JButton(new ImageIcon("CanvasData/brus.png")),ellb = new JButton(new ImageIcon("CanvasData/ell.png")),
        lib = new JButton(new ImageIcon("CanvasData/line.png")), peb = new JButton(new ImageIcon("CanvasData/penc.png")), nob = new JButton(new ImageIcon("CanvasData/nob.png")),
        filb = new JButton(new ImageIcon("CanvasData/filb.png"));
        atP.add(nob);  atP.add(filb);  atP.setVisible(false);
        final AbstractColorChooserPanel accp = new JColorChooser().getChooserPanels()[0];
        cP.add(accp);
        accp.getColorSelectionModel().addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){pad.setCol(accp.getColorSelectionModel().getSelectedColor()); }});
        neF.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){ pad.clear();}});
        exi.addActionListener(new ActionListener(){  public void actionPerformed(ActionEvent e){ f.dispose();}});
        eco.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){pad.color();}});
        erb.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){ pad.eraser();tool = "erb";}});
        cbsb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.changeb();}});
        txb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.text();tool = "txb";}});
        recb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.rect(); tool = "recb";}});
        brub.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){ pad.brush(); tool = "brub";}});
        ellb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ pad.ellipse();tool = "ellb";}});
        lib.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.line(); tool = "lib";}});
        svF.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ pad.save();}});
        opF.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.open();}});
        peb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.pencil();tool = "peb";}});
        filb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.isShapeToBeFilled(true);}});
        nob.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.isShapeToBeFilled(false);}});
        undo.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.undo();}});
        redo.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){pad.redo();}});
        erb.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("erb"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        cbsb.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("cbsb"));}  

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        txb.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("txb"));} 

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        recb.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("recb"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        brub.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("brub"));} 

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        ellb.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("ellb"));} 

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        lib.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("lib"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        svF.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("svF"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        opF.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("opF"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        peb.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("peb"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        filb.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("filb"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        nob.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("nob"));} 

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        undo.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("undo"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        redo.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("redo"));} 

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        neF.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("neF"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        exi.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("exi"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        eco.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("eco"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        accp.addMouseListener(new MouseAdapter(){ public void mouseEntered(MouseEvent e){stb.setText(getM("accp"));}

                public void mouseExited(MouseEvent e){stb.setText(getM("pad"));}});
        erb.setToolTipText("Eraser");
        peb.setToolTipText("Pencil");
        txb.setToolTipText("Text");
        brub.setToolTipText("Brush");
        lib.setToolTipText("Line");
        recb.setToolTipText("Rectangle");
        ellb.setToolTipText("Ellipse");
        cbsb.setToolTipText("Brush Size");
        nob.setToolTipText("Draw");
        filb.setToolTipText("Fill");
        panel.add(erb);
        panel.add(peb);
        panel.add(txb);
        panel.add(brub);
        panel.add(lib);
        panel.add(recb);
        panel.add(ellb);
        panel.add(cbsb);
        panel.add(new JLabel(""));
        panel.add(atP);
        panel.add(usb, BorderLayout.SOUTH);
        panel.add(stb, BorderLayout.SOUTH);
        span.add(panel);
        span.add(cP);
        cont.add(span, BorderLayout.SOUTH);
        f.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){pad.checkSave();}
        public void windowClosed(WindowEvent e){System.exit(0);}});
        f.setExtendedState(f.MAXIMIZED_BOTH);
        f.setDefaultLookAndFeelDecorated(true);
        f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }

    public static String getM(String too){
        String msg = "";
        switch(too) {
            case "erb":
            msg = "Eraser : Click or Drag to erase.";
            break;
            case "cbsb":
            msg = "Change Brush Size : Click to select brush size.";
            break;
            case "txb":
            msg = "Text : Click to set text and font. Then click to place the text on the image.";
            break;
            case "recb":
            msg = "Rectangle : Drag to draw a rectangle.";
            break;
            case "brub":
            msg = "Brush : Click or drag to draw something.";
            break;
            case "ellb":
            msg = "Ellipse : Drag to draw a circle.";
            break;
            case "lib":
            msg = "Line : Drag to draw a line.";
            break;
            case "svF":
            msg = "Save File : Click to save the file to a specified location.";
            break;
            case "opF":
            msg = "Open File : Click to open an image.";
            break;
            case "peb":
            msg = "Pencil : Drag to draw something.";
            break;
            case "filb":
            msg = "Fill : Click on it to draw a filled rectangle or circle.";
            break;
            case "nob":
            msg = "Draw : Click on it to draw an empty rectangle or circle.";
            break;
            case "undo":
            msg = "Undo : Click on it to revert the last change.";
            break;
            case "redo":
            msg = "Redo : Click on it to repeat the last undo change.";
            break;
            case "neF":
            msg = "Click on it to create a new empty image.";
            break;
            case "exi":
            msg = "Click on it to exit from Canvas.";
            break;
            case "eco":
            msg = "Click on it to select a color from 279 color swatches and HSV, HSL, RGB and CYMK color combinations.";
            break;
            case "accp":
            msg = "Select a new Color from this.";
            break;
            case "pad":
            msg = "Current Tool => "+getM(tool);
            break;
        }
        return msg;
    }

    public static void sp(boolean wtd){
        atP.setVisible(wtd);
    }

    public static void help(){
        final JFrame hf = new JFrame("Canvas Help");
        JPanel hpan = new JPanel(),fmh = new JPanel(),emh = new JPanel(),cmh = new JPanel(),hbp = new JPanel(),geh = new JPanel();
        JButton okb = new JButton("OK");
        JTabbedPane htp = new JTabbedPane();
        JLabel ge1 = new JLabel("This is a paint program by Priydarshi Singh. It covers\n");
        JLabel ge2 = new JLabel("most of the tools used in a paint program. You can\n");
        JLabel ge3 = new JLabel("make drawings and save them in BMP,JPG,PNG,TIF\n");
        JLabel ge4 = new JLabel("or GIF formats.\n");
        JLabel fm1 = new JLabel("The File Menu gives you an option to create a new\n");
        JLabel fm2 = new JLabel("empty image, save an image, open an image or exit\n");
        JLabel fm3 = new JLabel("from Canvas.");
        JLabel em1 = new JLabel("This menu allows you to revert the last change done\n");
        JLabel em2 = new JLabel("on the image. If you wish, you can redo that change.\n");
        JLabel cm1 = new JLabel("Color menu helps you to choose a color from 279 \n");
        JLabel cm2 = new JLabel("color swatches and Hue-Saturation-Value(HSV), \n");
        JLabel cm3 = new JLabel("Hue-Saturation-Lightness(HSL), Red-Green-Blue(RGB)\n");
        JLabel cm4 = new JLabel("and Cyan-Magenta-Yellow(CMYK) Color Combinations.\n");
        geh.add(ge1); geh.add(ge2); geh.add(ge3); geh.add(ge4);
        fmh.add(fm1); fmh.add(fm2); fmh.add(fm3);
        emh.add(em1); emh.add(em2);
        cmh.add(cm1); cmh.add(cm2); cmh.add(cm3); cmh.add(cm4);
        htp.add("Canvas",geh);
        htp.add("File Menu Help",fmh);
        htp.add("Edit Menu Help",emh);
        htp.add("Color Menu Help",cmh);
        hbp.add(okb);
        okb.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){hf.dispose();}});
        hf.add(htp);
        hf.add(hbp, BorderLayout.SOUTH);
        hf.setSize(400,200);
        hf.setLocationRelativeTo(null);
        hf.setResizable(false);
        hf.setVisible(true);
    }
}
