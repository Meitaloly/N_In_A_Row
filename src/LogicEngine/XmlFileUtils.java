package LogicEngine;

import LogicEngine.generatedClasses.GameDescriptor;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.jar.JarException;

public class XmlFileUtils {
    String path;
    File xmlFilePath;
    GameDescriptor desc;
    BigInteger numOfCols;
    int numOfRows;
    BigInteger target;

    public XmlFileUtils(String pathStr)
    {
        path = pathStr;
    }

    public void setFilePath(String newPath)
    {
        path = newPath;
    }
    public int checkXmlFileValidation(GameBoard gameBoard)
    {
        int validationNumber = -1;

        xmlFilePath = new File(path);
        if(!xmlFilePath.exists())
        {
            validationNumber = 0; // file not exist
        }
        else
        {
            if(!checkTypeOfFileExtension())
            {
                validationNumber = 5; // not XML FILE
            }
            else {
                desc = parseXmltoJaxbMachine();

                if (!checkValidNumOfRows()) {
                    validationNumber = 1; // num of rows not good
                } else if (!checkValidNumOfCols()) {
                    validationNumber = 2; // cols not good
                } else if (!checkRowAndColsBiggerThenTarget()) {
                    validationNumber = 3; // target bigger then rows or cols
                } else if (target.longValue() == 0) {
                    validationNumber = 4; // target = 0
                }
            }
        }

        if(validationNumber == -1)
        {
            gameBoard.setGameBoard(numOfRows, numOfCols.longValue(),target.longValue());
        }

        return validationNumber;
    }

    public boolean checkTypeOfFileExtension()
    {
        boolean res = false;
        String fileName = xmlFilePath.getName();
        String upperFileName = fileName.toUpperCase();
        int dotIndex = upperFileName.lastIndexOf('.');
        String typeOfFile= upperFileName.substring(dotIndex+1);
        if(typeOfFile.equals("XML"))
        {
            res = true;
        }

        return res;
    }

    public boolean checkRowAndColsBiggerThenTarget()
    {
        boolean res = false;
        target = desc.getGame().getTarget();
        if(numOfCols.longValue() > target.longValue() && numOfRows > target.longValue())
        {
            res  =true;
        }

        return res;
    }

    private boolean checkValidNumOfCols()
    {
        boolean res = false;
        numOfCols = desc.getGame().getBoard().getColumns();
        if(numOfCols.longValue() >= 6 && numOfCols.longValue() <= 30)
        {
            res= true;
        }
        return res;
    }


    private boolean checkValidNumOfRows()
    {
        boolean res = false;
        numOfRows = desc.getGame().getBoard().getRows();
        if(numOfRows>= 5 && numOfRows<=50)
        {
            res= true;
        }
        return res;
    }

    public GameDescriptor parseXmltoJaxbMachine() {
        Unmarshaller jaxbUnmarshaller = null;
        GameDescriptor jaxbDesc = null;
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            jaxbDesc = (GameDescriptor) jaxbUnmarshaller.unmarshal(xmlFilePath);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return jaxbDesc;
    }
}
