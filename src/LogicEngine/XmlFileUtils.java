package LogicEngine;

import java.io.File;

public class XmlFileUtils {
    String path;
    File file;

    public XmlFileUtils(String pathStr)
    {
        path = pathStr;
    }

    public boolean checkXmlFileValidation()
    {
        boolean isValid = false;

        File xmlFilePath = new File(path);


        return isValid;

    }


}
