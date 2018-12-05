import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.io.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import java.util.*;
/**
 * @version 1.0
 */
public class DrawPad extends JComponent implements MouseListener, MouseMotionListener{
    ////////////////////////////////////STATIC VARIABLES////////////////////////////////////
    static Image img;
    static Graphics2D graphics2D;
    static Graphics g;
    static Color currently_chosen_color = Color.BLACK;
    static boolean is_shape_to_be_filled = false;
    static boolean saved = false;
    static File rough = new File("CanvasData/rough.png");
    ////////////////////////////////////////UNDO/////////////////////////////////////////////
    static boolean can_undo = false;
    static boolean can_redo = false;
    ////////////////////////////////////////TOOLS////////////////////////////////////////////
    static int PENCIL = 0;
    static int BRUSH = 1;
    static int TEXT = 2;
    static int LINE = 3;
    static int RECTANGLE = 4;
    static int ELLIPSE = 5;
    static int FILLER = 6;
    static int tool = -1;
    ////////////////////////////////////////BRUSH////////////////////////////////////////////
    static int brush_old_X_coordinate = 0, brush_old_Y_coordinate = 0, brush_current_X_coordinate = 0, brush_current_Y_coordinate = 0, brush_size = 20;
    static JFrame change_brush_size_frame = new JFrame("Change Brush Size");
    static JPanel change_brush_size_panel = new JPanel(new GridLayout(1,3));
    static JButton change_brush_size_submit_button = new JButton("Submit");
    static JLabel change_brush_size_label = new JLabel("                            Current Brush Size : "+brush_size);
    static JLabel change_brush_size_minimum = new JLabel("                          5");
    static JLabel change_brush_size_maximum=new JLabel("    500");
    static JSlider change_brush_size_slider= new JSlider(5,500,brush_size);
    /////////////////////////////////////////TEXT////////////////////////////////////////////
    static int text_style = 0, text_size = 0;
    static String text_font;
    static JFrame text_frame = new JFrame("Text");
    static JPanel text_size_panel = new JPanel();
    static JPanel text_style_panel = new JPanel();
    static JPanel text_font_panel = new JPanel();
    static JComboBox text_font_combobox = new JComboBox();
    static JComboBox text_style_combobox = new JComboBox();
    static JComboBox text_size_combobox = new JComboBox();
    static JLabel text_font_label = new JLabel("Font : ");
    static JLabel text_style_label = new JLabel("Style : ");
    static JLabel text_size_label = new JLabel("Size : ");
    static JTextField text_field_for_text =  new JTextField();
    static JButton text_submit_button = new JButton("Submit");
    ////////////////////////////////////////PENCIL////////////////////////////////////////////
    static int pencil_old_X_coordinate = -1, pencil_old_Y_coordinate = -1, pencil_current_X_coordinate = 0, pencil_current_Y_coordinate = 0;
    /////////////////////////////////////////LINE//////////////////////////////////////////////
    static int line_old_X_coordinate = 0, line_old_Y_coordinate = 0, line_current_X_coordinate = 0, line_current_Y_coordinate = 0;
    //////////////////////////////////////RECTANGLE//////////////////////////////////////////
    static int rectangle_old_X_coordinate = 0, rectangle_old_Y_coordinate = 0, rectangle_current_X_coordinate = 0, rectangle_current_Y_coordinate = 0;
    ///////////////////////////////////////ELLIPSE////////////////////////////////////////////
    static int ellipse_old_X_coordinate = 0, ellipse_old_Y_coordinate = 0, ellipse_current_X_coordinate = 0, ellipse_current_Y_coordinate = 0;
    public DrawPad(){
        addMouseListener(this);
        addMouseMotionListener(this);
        try{ ImageIO.write((RenderedImage)img,"PNG", new File("CanvasData/rough.png")); }catch(Exception ex){}
        try{ ImageIO.write((RenderedImage)img,"PNG", new File("CanvasData/undo.png")); }catch(Exception ex){}
        try{ ImageIO.write((RenderedImage)img,"PNG", new File("CanvasData/prev.png")); }catch(Exception ex){}
    }

    public void undo(){
        if(can_undo){
            try{
                Color old_color = graphics2D.getColor();
                img = ImageIO.read(new File("CanvasData/undo.png"));
                graphics2D=(Graphics2D)img.getGraphics();
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D.setColor(old_color);
                repaint();
                can_redo = true;
                can_undo = false;
            }catch(Exception e){}
        }else JOptionPane.showMessageDialog(null,"Cannot Undo","Canvas",JOptionPane.WARNING_MESSAGE);
    }

    public void redo(){
        if(can_redo){
            try{
                Color old_color = graphics2D.getColor();
                img = ImageIO.read(new File("CanvasData/rough.png"));
                graphics2D=(Graphics2D)img.getGraphics();
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D.setColor(old_color);
                repaint();
                can_redo = false;
                can_undo = true;
            }catch(Exception e){}
        }else JOptionPane.showMessageDialog(null,"Cannot Redo","Canvas",JOptionPane.WARNING_MESSAGE);
    }

    public void setCol(Color c){
        graphics2D.setColor(c);
        currently_chosen_color = c;
    }

    public void isShapeToBeFilled(boolean fill){
        is_shape_to_be_filled = fill;
    }

    public void mouseDragged(MouseEvent e){
        if(graphics2D != null && tool == BRUSH){
            brush_current_X_coordinate = e.getX();
            brush_current_Y_coordinate = e.getY();
            graphics2D.fillOval(brush_old_X_coordinate-(brush_size/2), brush_old_Y_coordinate-(brush_size/2), brush_size, brush_size);
            repaint();
            brush_old_X_coordinate = brush_current_X_coordinate;
            brush_old_Y_coordinate = brush_current_Y_coordinate;
        }

        if(graphics2D != null && tool == PENCIL){
            pencil_current_X_coordinate = e.getX();
            pencil_current_Y_coordinate = e.getY();
            graphics2D.drawLine(pencil_old_X_coordinate, pencil_old_Y_coordinate, pencil_current_X_coordinate, pencil_current_Y_coordinate);
            repaint();
            pencil_old_X_coordinate = pencil_current_X_coordinate;
            pencil_old_Y_coordinate = pencil_current_Y_coordinate;
        }

        if(graphics2D != null && tool == LINE){
            line_current_X_coordinate = e.getX();
            line_current_Y_coordinate = e.getY();
            Color old_color = graphics2D.getColor();
            try{ img = ImageIO.read(new File("CanvasData/rough.png"));}catch(Exception ex){}
            graphics2D=(Graphics2D)img.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(old_color);
            graphics2D.drawLine(line_old_X_coordinate, line_old_Y_coordinate, line_current_X_coordinate, line_current_Y_coordinate);
            repaint();
        }

        if(graphics2D != null && tool == RECTANGLE){
            rectangle_current_X_coordinate = e.getX();
            rectangle_current_Y_coordinate = e.getY();
            Color old_color = graphics2D.getColor();
            try{ img = ImageIO.read(new File("CanvasData/rough.png"));}catch(Exception ex){}
            graphics2D=(Graphics2D)img.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(old_color);
            int t_w = rectangle_old_X_coordinate - rectangle_current_X_coordinate;
            int t_h = rectangle_old_Y_coordinate - rectangle_current_Y_coordinate;
            int wid = Math.abs(t_w);
            int hei = Math.abs(t_h);
            int rx = t_w < 0 ? rectangle_old_X_coordinate : rectangle_current_X_coordinate;
            int ry = t_h < 0 ? rectangle_old_Y_coordinate : rectangle_current_Y_coordinate; 
            if(is_shape_to_be_filled) graphics2D.fillRect(rx, ry, wid, hei); else graphics2D.drawRect(rx, ry, wid, hei);
            repaint();
        }

        if(graphics2D != null && tool == ELLIPSE){
            ellipse_current_X_coordinate = e.getX();
            ellipse_current_Y_coordinate = e.getY();
            Color old_color = graphics2D.getColor();
            try{ img = ImageIO.read(new File("CanvasData/rough.png"));}catch(Exception ex){}
            graphics2D=(Graphics2D)img.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(old_color);
            int t_w = ellipse_old_X_coordinate - ellipse_current_X_coordinate;
            int t_h = ellipse_old_Y_coordinate - ellipse_current_Y_coordinate;
            int rx = t_w < 0 ? ellipse_old_X_coordinate : ellipse_current_X_coordinate;
            int ry = t_h < 0 ? ellipse_old_Y_coordinate : ellipse_current_Y_coordinate; 
            int w = Math.abs(t_w);
            int h = Math.abs(t_h);
            if(is_shape_to_be_filled) graphics2D.fillOval(rx,ry,w, h); else graphics2D.drawOval(rx,ry,w, h);
            repaint();
        }

        if(graphics2D != null && tool == FILLER){
        }
    }

    public void mouseMoved(MouseEvent e){
    }

    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}

    public void mousePressed(MouseEvent e){
        if(saved)saved = false;
        if(tool == BRUSH){
            brush_old_X_coordinate = e.getX();
            brush_old_Y_coordinate = e.getY();
        }

        if(tool == PENCIL){
            pencil_old_X_coordinate = e.getX();
            pencil_old_Y_coordinate = e.getY();
        }

        if(tool == LINE){
            line_old_X_coordinate = e.getX();
            line_old_Y_coordinate = e.getY();
        }

        if(tool == RECTANGLE){
            rectangle_old_X_coordinate = e.getX();
            rectangle_old_Y_coordinate = e.getY();
        }

        if(tool == ELLIPSE){
            ellipse_old_X_coordinate = e.getX();
            ellipse_old_Y_coordinate = e.getY();
        }

        try{ 
            RenderedImage i1 = (RenderedImage)img, i2 = (RenderedImage)ImageIO.read(new File("CanvasData/prev.png"));
            ImageIO.write(i1,"PNG", new File("CanvasData/rough.png"));
            ImageIO.write(i2,"PNG", new File("CanvasData/undo.png"));
            ImageIO.write(i1,"PNG", new File("CanvasData/prev.png"));
        }catch(Exception ex){}
        can_undo = true;
    }

    public void mouseReleased(MouseEvent e){
        try{ 
            RenderedImage i1 = (RenderedImage)img, i2 = (RenderedImage)ImageIO.read(new File("CanvasData/prev.png"));
            ImageIO.write(i1,"PNG", new File("CanvasData/rough.png"));
            ImageIO.write(i2,"PNG", new File("CanvasData/undo.png"));
            ImageIO.write(i1,"PNG", new File("CanvasData/prev.png"));
        }catch(Exception ex){}
        can_undo = true;
        repaint();
    }

    public void mouseClicked(MouseEvent e){ 
        if(saved)saved = false;

        try{ 
            RenderedImage i1 = (RenderedImage)img, i2 = (RenderedImage)ImageIO.read(new File("CanvasData/prev.png"));
            ImageIO.write(i1,"PNG", new File("CanvasData/rough.png"));
            ImageIO.write(i2,"PNG", new File("CanvasData/undo.png"));
            ImageIO.write(i1,"PNG", new File("CanvasData/prev.png"));
        }catch(Exception ex){}
        can_undo = true;
        repaint();
        if(tool == TEXT){
            String text = text_field_for_text.getText();
            graphics2D.setFont(new Font(text_font, text_style, text_size));
            graphics2D.drawString(text,e.getX(),e.getY());
            repaint();
        }

        if(graphics2D != null && tool == PENCIL){
            pencil_current_X_coordinate = e.getX();
            pencil_current_Y_coordinate = e.getY();
            graphics2D.drawLine(pencil_old_X_coordinate, pencil_old_Y_coordinate, pencil_current_X_coordinate, pencil_current_Y_coordinate);
            repaint();
            pencil_old_X_coordinate = pencil_current_X_coordinate;
            pencil_old_Y_coordinate = pencil_current_Y_coordinate;
        }

        if(graphics2D != null && tool == BRUSH){
            brush_current_X_coordinate = e.getX();
            brush_current_Y_coordinate = e.getY();
            graphics2D.fillOval(brush_old_X_coordinate-(brush_size/2), brush_old_Y_coordinate-(brush_size/2), brush_size, brush_size);
            repaint();
            brush_old_X_coordinate = brush_current_X_coordinate;
            brush_old_Y_coordinate = brush_current_Y_coordinate;
        }

        if(graphics2D != null && tool == FILLER){
        }
    }

    public void paintComponent(Graphics g){
        if(img == null){
            img = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D)img.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
            mousePressed(new MouseEvent(this,3,8L,5,4,5,6,7,8,false,1));
            pencil();
        }
        g.drawImage(img, 0, 0, null);
    }

    public void clear(){
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setColor(currently_chosen_color);
        repaint();
    }

    public void color(){
        graphics2D.setColor(JColorChooser.showDialog(null,"Choose Color:",graphics2D.getColor()));
        currently_chosen_color = graphics2D.getColor();
    }

    public void eraser(){
        currently_chosen_color = graphics2D.getColor();
        graphics2D.setColor(Color.white);
        Canvas.sp(false);
        try{ setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("CanvasData/erac.png")),new Point(16,0),"Eraser")); }catch(Exception e){e.printStackTrace();}
        tool = BRUSH;
    }

    public void changeb(){
        change_brush_size_frame.dispose();
        change_brush_size_frame = new JFrame("Choose new Brush Size");
        change_brush_size_panel.add(change_brush_size_minimum);
        change_brush_size_panel.add(change_brush_size_slider);
        change_brush_size_panel.add(change_brush_size_maximum);
        change_brush_size_slider.addChangeListener(new ChangeListener(){public void stateChanged(ChangeEvent e){ change_brush_size_label.setText("                            Current Brush Size :  "+change_brush_size_slider.getValue()); }});
        change_brush_size_submit_button.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){brush_size = change_brush_size_slider.getValue(); change_brush_size_frame.setVisible(false); brush();}});
        change_brush_size_frame.setDefaultCloseOperation(change_brush_size_frame.HIDE_ON_CLOSE);
        change_brush_size_frame.setLayout(new GridLayout(3,1));
        change_brush_size_frame.add(change_brush_size_label);
        change_brush_size_frame.add(change_brush_size_panel);
        change_brush_size_frame.add(change_brush_size_submit_button);
        change_brush_size_frame.setLocation(500,300);
        change_brush_size_frame.setSize(300,200);
        change_brush_size_frame.setDefaultLookAndFeelDecorated(true);
        change_brush_size_frame.setVisible(true);
    }

    public void text() {
        graphics2D.setColor(currently_chosen_color);
        tool = TEXT;
        text_frame = new JFrame("Set Text");
        text_font_panel = new JPanel(new GridLayout(1,2));
        text_size_panel = new JPanel(new GridLayout(1,2));
        text_style_panel = new JPanel(new GridLayout(1,2));
        text_frame.setLayout(new GridLayout(5,1));
        text_font_label.setLabelFor(text_font_combobox);
        text_style_label.setLabelFor(text_style_combobox);
        text_size_label.setLabelFor(text_size_combobox);
        text_font_combobox = new JComboBox();
        text_style_combobox = new JComboBox();
        text_size_combobox = new JComboBox();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] allFonts = ge.getAllFonts();
        for (Font font : allFonts) {text_font_combobox.addItem(font.getFontName(Locale.US));}
        String styles[] = {"Plain","Bold","Italic","Bold Italic"};
        for(int as=0; as<4; as++){ text_style_combobox.addItem(styles[as]); }
        for(int si = 20; si <= 300;si=si+10){ text_size_combobox.addItem(si+"");}
        text_font_panel.add(text_font_label);text_font_panel.add(text_font_combobox);
        text_style_panel.add(text_style_label);text_style_panel.add(text_style_combobox);
        text_size_panel.add(text_size_label);text_size_panel.add(text_size_combobox);
        text_frame.add(text_font_panel);text_frame.add(text_style_panel);text_frame.add(text_size_panel);text_frame.add(text_field_for_text);text_frame.add(text_submit_button);
        text_frame.setSize(280,200);
        text_frame.setLocation(500,300);
        text_frame.setDefaultCloseOperation(text_frame.HIDE_ON_CLOSE);
        text_frame.setDefaultLookAndFeelDecorated(true);
        text_frame.setVisible(true);
        text_submit_button.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){
                    String sgsl=(String)text_style_combobox.getSelectedItem();
                    if(sgsl.equals("Plain")){text_style = Font.PLAIN;
                    }else if(sgsl.equals("Bold")){text_style = Font.BOLD;
                    }else if(sgsl.equals("Italic")){text_style = Font.ITALIC;
                    }else if(sgsl.equals("Bold Italic")) {text_style = Font.BOLD+Font.ITALIC; }
                    else {text_style = Font.PLAIN;  }
                    text_size =Integer.parseInt((String)text_size_combobox.getSelectedItem());
                    text_font = (String)text_font_combobox.getSelectedItem();
                    try{ setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("CanvasData/textc.png")),new Point(30,0),"Text")); }catch(Exception ex){ex.printStackTrace();}
                    text_frame.setVisible(false);
                }});
        String text = text_field_for_text.getText();
        graphics2D.setFont(new Font(text_font, text_style, text_size));
        repaint();
    }

    public void rect(){ 
        graphics2D.setColor(currently_chosen_color);
        tool = RECTANGLE;
        try{ setCursor(new Cursor(1)); }catch(Exception e){e.printStackTrace();}
        Canvas.sp(true);
    }

    public void pencil(){
        graphics2D.setColor(currently_chosen_color);
        tool = PENCIL;
        try{ setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("CanvasData/pencc.png")),new Point(16,0),"Pencil")); }catch(Exception e){e.printStackTrace();}
        Canvas.sp(false);
    }

    public void brush(){
        graphics2D.setColor(currently_chosen_color);
        tool = BRUSH;
        try{ setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(new File("CanvasData/brusc.png")),new Point(16,0),"Brush")); }catch(Exception e){e.printStackTrace();}
        Canvas.sp(false);
    }

    public void ellipse(){
        graphics2D.setColor(currently_chosen_color);
        tool = ELLIPSE;
        try{ setCursor(new Cursor(1)); }catch(Exception e){e.printStackTrace();}
        Canvas.sp(true);
    }

    public void line(){
        graphics2D.setColor(currently_chosen_color);
        tool = LINE;
        try{ setCursor(new Cursor(1)); }catch(Exception e){e.printStackTrace();}
        Canvas.sp(false);
    }

    public void fill(){
        tool = FILLER;
    }

    public void save() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter pencil_F = new FileNameExtensionFilter("PNG", "PNG");
        FileNameExtensionFilter pencil_J = new FileNameExtensionFilter("JPG", "JPEG", "JFIF", "JPG");
        FileNameExtensionFilter pencil_B = new FileNameExtensionFilter("BMP", "BMP", "DIB");
        FileNameExtensionFilter pencil_T = new FileNameExtensionFilter("TIF", "TIFF", "TIF");
        FileNameExtensionFilter pencil_G = new FileNameExtensionFilter("GIF", "GIF");
        chooser.addChoosableFileFilter(pencil_B);
        chooser.addChoosableFileFilter(pencil_J);
        chooser.addChoosableFileFilter(pencil_F);
        chooser.addChoosableFileFilter(pencil_T);
        chooser.addChoosableFileFilter(pencil_G);
        int returnVal = chooser.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try{
                String disc = "PNG";
                if(chooser.getFileFilter().getDescription().equals("All Files")){} else {disc = chooser.getFileFilter().getDescription();}
                ImageIO.write((RenderedImage)img,disc, new File(chooser.getCurrentDirectory()+"/"+chooser.getSelectedFile().getName()+"."+disc));
                saved = true;
            }catch(Exception e){}
        }
    }

    public void open(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter pencil_F = new FileNameExtensionFilter("PNG", "PNG");
        FileNameExtensionFilter pencil_J = new FileNameExtensionFilter("JPG", "JPEG", "JFIF", "JPG");
        FileNameExtensionFilter pencil_B = new FileNameExtensionFilter("BMP", "BMP", "DIB");
        FileNameExtensionFilter pencil_T = new FileNameExtensionFilter("TIF", "TIFF", "TIF");
        FileNameExtensionFilter pencil_G = new FileNameExtensionFilter("GIF", "GIF");
        chooser.addChoosableFileFilter(pencil_B);
        chooser.addChoosableFileFilter(pencil_J);
        chooser.addChoosableFileFilter(pencil_F);
        chooser.addChoosableFileFilter(pencil_T);
        chooser.addChoosableFileFilter(pencil_G);
        int returnVal = chooser.showOpenDialog(null);
        Color old_color = graphics2D.getColor();
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try{
                img = ImageIO.read(new File(chooser.getCurrentDirectory()+"/" +chooser.getSelectedFile().getName()));
            }catch(Exception e){}
        }
        graphics2D=(Graphics2D)img.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(old_color);
        repaint();
    }

    public void checkSave(){
        if(!saved){
            int opt = JOptionPane.showConfirmDialog(null,"The image is not saved. Do you wish to save it?","Canvas",JOptionPane.YES_NO_OPTION);
            if(opt == JOptionPane.YES_OPTION)save();
        }
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,getWidth(),getHeight());
        try{ ImageIO.write((RenderedImage)img,"PNG", new File("CanvasData/rough.png")); }catch(Exception ex){}
        try{ ImageIO.write((RenderedImage)img,"PNG", new File("CanvasData/undo.png")); }catch(Exception ex){}
        try{ ImageIO.write((RenderedImage)img,"PNG", new File("CanvasData/prev.png")); }catch(Exception ex){}
    }
}
