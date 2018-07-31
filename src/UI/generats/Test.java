package UI.generats;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s ="D:/JavaProj/Ex1/ex1-error.xml";
                //scan.nextLine();
        System.out.println(s);
        InputStream input = Test.class.getResourceAsStream(s);
        try {
            GameDescriptor desc = deserializeFrom(input);
            System.out.println(desc);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    private static GameDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("UI.generats");
        Unmarshaller u = jc.createUnmarshaller();
        return (GameDescriptor) u.unmarshal(in);
    }
}
