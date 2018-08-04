package UI.generats;

import LogicEngine.generatedClasses.GameDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        //String s = "D:/JavaProj/Ex1/ex1-error.xml";
        String str = s.nextLine();
        System.out.println(str);
        // InputStream input = Test.class.getClass().getResourceAsStream(s);
        int i = 0;
       /* try {
            //GameDescriptor desc = parseXmltoJaxbMachine(str);
            //System.out.println(desc);
            //System.out.print(desc.getGame().getBoard().getColumns());
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/
    }

    /*public static GameDescriptor parseXmltoJaxbMachine(String filePath) throws JAXBException {
        Unmarshaller jaxbUnmarshaller = null;
        GameDescriptor jaxbEnigma = null;
        File xmlFilePath = new File(filePath);
        JAXBContext jaxbContext = null;
        jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbEnigma = (GameDescriptor) jaxbUnmarshaller.unmarshal(xmlFilePath);
        return jaxbEnigma;

    }*/
}

