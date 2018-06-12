package connectYourParty;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileReader {


    public static byte[] readfile() throws Exception{
        String path = FileReader.class.getClassLoader().getResource("DropboxService.class").getPath();

        InputStream stream = new FileInputStream(new File(path));

        byte[] bin = new byte[stream.available()];

        stream.read(bin);
        return bin;
    }
}
