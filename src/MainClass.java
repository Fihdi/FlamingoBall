import javax.swing.*;
import java.io.File;

public class MainClass {

    public static void main(String[] args){

        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);

        System.out.println("Opened File:");
        System.out.println(fc.getSelectedFile().getAbsolutePath());

        File picture = new File(fc.getSelectedFile().getAbsolutePath());

        Fenster f = new Fenster(picture);


    }
}
