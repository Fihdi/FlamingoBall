import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Scanner;

public class Fenster extends JFrame {

    File f;
    JLabel iconLabel;
    Graphics2D g2;

    int clickcounter;

    Point points[] = new Point[6];
    double wq1;
    double wq2;
    double wp1;
    double wp2;

    double alpha;

    Scanner s = new Scanner(System.in);


    public Fenster(File f) {
        this.f = f;
        iconLabel = new JLabel(new ImageIcon(f.getAbsolutePath()));
        add(iconLabel);

        for (int i = 0; i < points.length; i++) {
            points[i] = new Point();
        }

        System.out.println("Click on first point on the X-axis.");

           MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }


            @Override
            public void mousePressed(MouseEvent e) {

                if (clickcounter == 1) {
                    points[0].x = e.getLocationOnScreen().x - getX();
                    points[0].y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("First point on X-axis: " + points[0].toString());
                    System.out.println("Enter x-value at this point:");
                    wq1 = s.nextDouble();
                    clickcounter++;
                    System.out.println("Click on second point on the X-axis.");
                }
                if (clickcounter == 2) {
                    points[1].x = e.getLocationOnScreen().x - getX();
                    points[1].y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("Second point on X-axis: " + points[1].toString());
                    System.out.println("Enter x-value at this point:");
                    wq2 = s.nextDouble();
                    clickcounter++;
                    System.out.println("Click on first point on the Y-axis.");
                }
                if (clickcounter == 3) {
                    points[2].x = e.getLocationOnScreen().x - getX();
                    points[2].y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("First point on Y-axis: " + points[2].toString());
                    System.out.println("Enter y-value at this point:");
                    wp1 = s.nextDouble();
                    clickcounter++;
                    System.out.println("Click on second point on the Y-axis.");
                }
                if (clickcounter == 4) {
                    points[3].x = e.getLocationOnScreen().x - getX();
                    points[3].y = getHeight() - (e.getLocationOnScreen().y - getY());

                    System.out.println("Second point on Y-axis: " + points[3].toString());
                    System.out.println("Enter y-value at this point:");
                    wp2 = s.nextDouble();
                    clickcounter++;
                    points[4] = calcOrigin();
                    System.out.println("Origin: " + points[4].toString());
                    System.out.println("Click onto a wanted point");
                }

                if (clickcounter > 5) {

                    points[5].x = e.getLocationOnScreen().x - getX();
                    points[5].y = getHeight() - (e.getLocationOnScreen().y - getY());

                    constructCoordinateSystem();
                }

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

        setSize(iconLabel.getIcon().getIconWidth(), iconLabel.getIcon().getIconHeight());
        setTitle("FlamingoBall");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Point calcOrigin() {
        //Function spanning from points[0] to points[1] f = k*x+d
        double k;
        double d;
        //Function spanning from points[2] to points[3] g = m*x+h
        double m;
        double h;

        double x;
        double y;

        Point pointA = points[0];
        Point pointB = points[1];
        Point pointC = points[2];
        Point pointD = points[3];
        
        if (pointA.x == pointB.x) {
            m = (pointC.y - pointD.y) / (pointC.x - pointD.y);
            h = pointC.y - m * pointC.x;
            y = m * pointA.x + h;

            return new Point(pointA.x, (int) y);
        } else if (pointC.x == pointD.x) {
            k = (pointB.y - pointA.y) / (pointB.x - pointA.x);
            d = pointA.y - k * pointA.x;
            y = k * pointC.x + d;
            return new Point(pointC.x, (int) y);
        } else {
            System.out.println("No exceptions");
            k = (pointB.y - pointA.y) / (pointB.x - pointA.x);
            d = pointA.y - k * pointA.x;

            m = (pointC.y - pointD.y) / (pointC.x - pointD.x);
            h = pointC.y - m * pointC.x;

            x = (d - h) / (m - k);
            y = k * x + d;

            return new Point((int) x, (int) y);
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        g2 = (Graphics2D) g;

    }

    private void constructCoordinateSystem() {

        alpha = Math.atan((double) (points[0].y - points[1].y) / (double) (points[0].x - points[1].x));
        for (int i = 0; i < points.length; i++) {
            if (i != 4) {
                points[i].x -= points[4].x;
                points[i].y -= points[4].y;
            }
        }
        points[4].x = 0;
        points[4].y = 0;


        System.out.println("angle alpha: " + Math.toDegrees(alpha));
        //rotate coordinate system with angle alpha (also calculate alpha)

        /*  RotateMatrix
            (cos(a)         sin(a))
            (-sin(alpha)    cos(a))

         */

        rotatePoints();

        int lengthX = points[1].x;
        int lengthY = points[2].y;

        double wantedX = (wq2 * points[5].x) / (lengthX);
        double wantedY = (wp1 * points[5].y) / (lengthY);

        System.out.println("wanted point X: " + wantedX);
        System.out.println("wanted point Y: " + wantedY);
    }

    private void rotatePoints() {


        for (int i = 0; i < points.length; i++) {
            int tempX = points[i].x;
            int tempY = points[i].y;

            points[i].x = (int) (tempX * Math.cos(alpha) + tempY * Math.sin(alpha));
            points[i].y = (int) (tempY * Math.cos(alpha) - tempX * Math.sin(alpha));
        }
    }

}
