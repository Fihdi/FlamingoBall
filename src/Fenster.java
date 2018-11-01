import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Scanner;

public class Fenster extends JFrame {

    private File f;
    private JLabel iconLabel;
    private Graphics2D g2;

    private Point points[] = new Point[6];
    private Point axisPoints[] = new Point[4];
    private Point origin = new Point();

    private Point mousePos = new Point();
    private boolean moving[] = new boolean[4];

    private double pointValues[] = new double[4];
    private double wq1;
    private double wq2;
    private double wp1;
    private double wp2;

    private double alpha;

    Scanner s = new Scanner(System.in);


    public Fenster(File f) {

        this.f = f;
        iconLabel = new JLabel(new ImageIcon(f.getAbsolutePath()));
        add(iconLabel);

        for (int i = 0; i < axisPoints.length; i++) {
            axisPoints[i] = new Point();
        }

        axisPoints[0] = new Point(150, 200); //X1
        axisPoints[1] = new Point(200, 200); //X2
        axisPoints[2] = new Point(175, 250); //Y1
        axisPoints[3] = new Point(175, 150); //Y2


        MouseMotionListener mml = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (!moving[0] && !moving[1] && !moving[2] && !moving[3]) return;
                mousePos = e.getPoint();
                if (moving[0]) axisPoints[0] = e.getPoint();
                if (moving[1]) axisPoints[1] = e.getPoint();
                if (moving[2]) axisPoints[2] = e.getPoint();
                if (moving[3]) axisPoints[3] = e.getPoint();
                repaint();
            }
        };

        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePos.setLocation(e.getLocationOnScreen().x - getX(), e.getLocationOnScreen().y - getY());
                for (int i = 0; i < axisPoints.length; i++) {
                    if (clickedaroundpoint(mousePos) == axisPoints[i]) {
                        moving[i] = !moving[i];
                        System.out.println("moving[" + i + "] has changed to " + moving[i]);
                    }
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
        this.addMouseMotionListener(mml);
        setSize(iconLabel.getIcon().getIconWidth(), iconLabel.getIcon().getIconHeight());
        setTitle("FlamingoBall");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Point calcOrigin() {
        //Function spanning from axisPoints[0] to axisPoints[1] f = k*x+d
        double k;
        double d;
        //Function spanning from axisPoints[2] to axisPoints[3] g = m*x+h
        double m;
        double h;

        double x;
        double y;

        if (axisPoints[0].x == axisPoints[1].x) {
            m = ((double) axisPoints[2].y - (double) axisPoints[3].y) / ((double) axisPoints[2].x - (double) axisPoints[3].y);
            h = axisPoints[2].y - m * axisPoints[2].x;
            y = m * axisPoints[0].x + h;

            return new Point(axisPoints[0].x, (int) y);
        } else if (axisPoints[2].x == axisPoints[3].x) {
            k = ((double) axisPoints[1].y - axisPoints[0].y) / ((double) axisPoints[1].x - (double) axisPoints[0].x);
            d = axisPoints[0].y - k * axisPoints[0].x;
            y = k * axisPoints[2].x + d;
            return new Point(axisPoints[2].x, (int) y);

        } else if (axisPoints[2].x == axisPoints[3].x && axisPoints[0].y == axisPoints[1].y) {

            return new Point(axisPoints[2].x, axisPoints[0].y);
        } else {
            System.out.println("No exceptions");
            k = ((double) axisPoints[1].y - (double) axisPoints[0].y) / ((double) axisPoints[1].x - (double) axisPoints[0].x);
            d = axisPoints[0].y - k * axisPoints[0].x;

            m = ((double) axisPoints[2].y - (double) axisPoints[3].y) / ((double) axisPoints[2].x - (double) axisPoints[3].x);
            h = axisPoints[2].y - m * axisPoints[2].x;

            x = (d - h) / (m - k);
            y = k * x + d;

            return new Point((int) x, (int) y);
        }
    }


    public void paint(Graphics g) {
        super.paintComponents(g);
        g2 = (Graphics2D) g;
        g2.setColor(Color.RED);

        g2.fillOval(axisPoints[0].x, axisPoints[0].y, 7, 7);
        g2.fillOval(axisPoints[1].x, axisPoints[1].y, 7, 7);

        g2.fillOval(axisPoints[2].x, axisPoints[2].y, 7, 7);
        g2.fillOval(axisPoints[3].x, axisPoints[3].y, 7, 7);

        drawAxis(axisPoints[0], axisPoints[1]);
        drawAxis(axisPoints[2],axisPoints[3]);
        for (int i = 0; i < axisPoints.length; i++) {
            if (moving[i]) {
                g2.fillOval(mousePos.x, mousePos.y, 7, 7);
            }
        }

    }

    private Point clickedaroundpoint(Point clicked) {
        for (int i = 0; i < axisPoints.length; i++) {
            if (Point.distance(axisPoints[i].x, axisPoints[i].y, clicked.x, clicked.y) <= 5) {
                System.out.println(axisPoints[i].toString());
                return axisPoints[i];
            }
        }
        return null;
//if (Point.distance(axisPoints[0].x, axisPoints[0].y, clicked.x, clicked.y) <= 5)
    }

    void drawAxis(Point x1, Point x2) {

        if (x1.x != x2.x && x1.y != x2.y) {
            double k = ((double) x2.y - (double) x1.y) / ((double) x2.x - (double) x1.x);
            double d = x1.y - k * x1.x;

            int s1y = (int) (k * getX() + d);


            g2.drawLine(0, (int) d + 2, getX(), s1y + 2);
        } else if (x1.y == x2.y) {
            g2.drawLine(0, x1.y + 2, getX(), x1.y + 2);
        } else if (x1.x == x2.x) {
            g2.drawLine(x1.x, 0, x1.x, this.getHeight());
        }
        origin = calcOrigin();
        g2.fillOval(origin.x-2,origin.y-2,7,7);

    }

    private void constructCoordinateSystem() {

        alpha = Math.atan((double) (axisPoints[0].y - axisPoints[1].y) / (double) (axisPoints[0].x - axisPoints[1].x));
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

        int lengthX = axisPoints[1].x;
        int lengthY = axisPoints[2].y;

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
