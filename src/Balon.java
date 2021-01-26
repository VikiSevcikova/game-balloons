import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import sun.audio.*;

/**
 *
 * @author Viktória Ševčíková AIBC09
 */
public class Balon extends JComponent implements ActionListener, MouseListener{
    protected static final Color[] farby = {Color.YELLOW, Color.ORANGE, Color.RED, Color.MAGENTA, Color.PINK,     
                                         Color.CYAN, Color.BLUE, Color.GREEN};
    protected static final int[] body = {2,3,6,7,9,10,12,15}; //body pre určité farby
    
    protected static int skore;
    protected int i; //aby sme vedeli, ktorá farba má koľko bodov
    protected int posun = 2; //posun - o koľko sa má posunúť balón
    protected int delay;
    
    protected JFrame okno;
    protected Color farba; 
    protected Timer casovacB;
    
    protected InputStream in;
    protected AudioStream aS;  
    
    public Balon(JFrame okno,int delay){
        //nastavenie kurzora na krížik
        Cursor cursor = Cursor.getDefaultCursor();
        cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);    
        setCursor(cursor);
      
        this.okno = okno;
        this.delay = delay;
        casovacB = new Timer(delay,this);
        skore = 0; 
        i = (int)(Math.random()*8); // pre nahodné nastavenie farby
        farba = farby[i];

        setBounds((int)(1+Math.random()*640),611,40,80);
        addMouseListener(this); //aby reagoval na kliknutie
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  
            //balón
            g.setColor(farba);
            g.fillOval(0, 0, 40, 40); 
            
            int[] xSur= {20,15,25,20}; 
            int[] ySur= {35,45,45,35};
            g.fillPolygon(xSur, ySur, 4);
            //šnúrka
            g.setColor(Color.BLACK);
            g.drawLine(20, 45, 20, 80);
            
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //zapne sa časovač balóna a balón sa pobyhuje zdola nahor, ale ak sa dostane po okraj tak sa objaví 
        //znovu dole na rôznej X pozícii
        casovacB.start();
        
        if (getY()-posun >= -getHeight()) {
            setBounds(getX(), getY()-posun, getWidth(), getHeight());
        } else {
            setDelay();
            setBounds((int)(1+Math.random()*640), okno.getHeight(), getWidth(), getHeight());
        }
     
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
           //pri kliknutí na balón sa vydá zvuk
                    try {
                        in = new FileInputStream("pop.wav");
                        aS = new AudioStream(in);
                        AudioPlayer.player.start(aS); 
                    } catch (IOException ex) {
                        System.out.println("Nenasla sa");
                    }
        
        skore += body[i]; //zvýši sa skóre o počet bodov, akú má určitá farba
        //objaví znovu dole na roznej X pozicii a zmeni sa aj farba, zmeni sa aj rýchlosť balóna
        setDelay();
        setBounds((int)(1+Math.random()*640), okno.getHeight(), getWidth(), getHeight()); 
        i = (int)(Math.random()*8);
        farba = farby[i];
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {} 
      
    
    public static int getSkore() {
        return skore;
    }
    //metóda ktorá vynuluje skóre, použijem ju aj pri špeciálnom balóne
    public static void setSkore() {
        skore = 0;
    }
    //nastavenie rýchlosti
    public void setDelay() {
        delay = (int)(10+Math.random()*100);
    }
    
    public void stop(){
        casovacB.stop();
    }
   
}
