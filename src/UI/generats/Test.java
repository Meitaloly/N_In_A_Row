package UI.generats;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Test {


    public static void main(String[] args) {
        char [][] array = new char [2][4];
        Arrays.fill(array[0], '0');
        Arrays.fill(array[1], '0');
        for(int i = 0 ; i<2 ; i++){
            for (int j=0; j<4;j++){
                System.out.print (array[i][j]);
            }
            System.out.println ();
        }
    }

    public static GameDescriptor parseXmltoJaxbMachine(String filePath) throws JAXBException {
        Unmarshaller jaxbUnmarshaller = null;
        GameDescriptor jaxbEnigma = null;
        File xmlFilePath = new File(filePath);
        System.out.println(xmlFilePath.exists());
        JAXBContext jaxbContext = null;
        jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbEnigma = (GameDescriptor) jaxbUnmarshaller.unmarshal(xmlFilePath);
        return jaxbEnigma;

    }
}

