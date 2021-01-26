import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import sun.audio.*;

/**
 *
 * @author Viktória Ševčíková AIBC09
 */
public class SpecialnyBalon extends Balon{
    protected int pocet; // pocet toho ze kolky specialny balon vyleti
    
    public SpecialnyBalon(JFrame okno,int delay){
        super(okno,delay);
        pocet =1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    
       casovacB.start();
            
            if (getY()-posun >= -getHeight()) {
                setBounds(getX(), getY()-posun, getWidth(), getHeight());
               } else {
                casovacB.stop();
                setDelay();
                setBounds((int)(1+Math.random()*640), 611, getWidth(), getHeight());
            } 
           
        }        
   
    @Override
    public void mousePressed(MouseEvent e) {
        //ak je každý druhý tak je balón bonusový inak je nulovací
        if(pocet%2==0){   
           //ak je to bonusový balón a klikne sa naň skóre sa zdvojnásobí a vydá zvončekový zvuk
                try {
                    in = new FileInputStream("bell.wav");
                    aS = new AudioStream(in);
                    AudioPlayer.player.start(aS);
                } catch (IOException ex) {
                    System.out.println("Nenasla sa");
                }
                 
            bonus();                  
        }else{
            //ak je to nulovací balón a klikne sa naň vynuluje sa skóre a vydá škaredší zvuk
                try {
                    in = new FileInputStream("null.wav");
                    aS = new AudioStream(in);
                    AudioPlayer.player.start(aS); 
                } catch (IOException ex) {
                    System.out.println("Nenasla sa");
                }
                
            setSkore();
        }  
        
        pocet++;
            i = (int)(Math.random()*8);
            farba = farby[i];
            setDelay();
            setBounds((int)(1+Math.random()*640), okno.getHeight(), getWidth(), getHeight());
            casovacB.stop();

    } 
    
    //vlastná metóda zdvojnasobí sa skóre
    public void bonus(){
        skore *= 2; 
    }
    
}
