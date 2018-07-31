package UI.generats;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = "D:/JavaProj/Ex1/ex1-error.xml";
        //scan.nextLine();
        System.out.println(s);
        // InputStream input = Test.class.getClass().getResourceAsStream(s);
        try {
            GameDescriptor desc = parseXmltoJaxbMachine("D:/JavaProj/Ex1/ex1-error.xml");
            System.out.println(desc);
            System.out.print(desc.getGame().getBoard().getColumns());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static GameDescriptor parseXmltoJaxbMachine(String filePath) throws JAXBException {
        Unmarshaller jaxbUnmarshaller = null;
        GameDescriptor jaxbEnigma = null;
        File xmlFilePath = new File("D:/JavaProj/Ex1/ex1-error.xml");
        JAXBContext jaxbContext = null;
        jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbEnigma = (GameDescriptor) jaxbUnmarshaller.unmarshal(xmlFilePath);
        return jaxbEnigma;

    }
}

