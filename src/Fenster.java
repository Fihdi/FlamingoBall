import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;

public class Fenster extends JFrame {

    File f;
    JLabel iconLabel;
    Graphics2D g2;

    int clickcounter;
    Point origin = new Point();
    //Two points to determine the X-axis
    Point q1 = new Point();
    Point q2= new Point();
    //Two points to determine the Y-axis
    Point p1= new Point();
    Point p2= new Point();

    double wq1;
    double wq2;
    double wp1;
    double wp2;

    Scanner s = new Scanner(System.in);


    public Fenster(File f){
        this.f = f;

        iconLabel = new JLabel(new ImageIcon(f.getAbsolutePath()));

        add(iconLabel);

        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickcounter++;
                if(clickcounter==1) {
                    q1.x = e.getLocationOnScreen().x - getX();
                    q1.y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("First point on X-axis: " + q1.toString());
                    System.out.println("Enter x-value at this point:");
                    wq1 = s.nextDouble();
                }
                if(clickcounter==2){
                    q2.x = e.getLocationOnScreen().x - getX();
                    q2.y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("Second point on X-axis: " + q2.toString());
                    System.out.println("Enter x-value at this point:");
                    wq2 = s.nextDouble();
                }
                if(clickcounter==3){
                    p1.x = e.getLocationOnScreen().x - getX();
                    p1.y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("First point on Y-axis: " + p1.toString());
                    System.out.println("Enter y-value at this point:");
                    wp1 = s.nextDouble();
                }
                if(clickcounter==4){
                    p2.x = e.getLocationOnScreen().x - getX();
                    p2.y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("Second point on Y-axis: " + p2.toString());
                    System.out.println("Enter y-value at this point:");
                    wp2 = s.nextDouble();
                }

                if(clickcounter==5){
                    System.out.println("Origin:");
                    origin.x = e.getLocationOnScreen().x - getX();
                    origin.y =  getHeight() - (e.getLocationOnScreen().y - getY());
                    constructCoordinateSystem();
                }
            }



            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };

        this.addMouseListener(ml);

        setSize(iconLabel.getIcon().getIconWidth(),iconLabel.getIcon().getIconHeight());
        setTitle("FlamingoBall");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        g2 = (Graphics2D) g;
    }

    private void constructCoordinateSystem() {

        //move coordinate system origin to 0,0
        //codecodecodecodecodecode

        //rotate coordinate system with angle alpha (also calculate alpha)

    }

}
