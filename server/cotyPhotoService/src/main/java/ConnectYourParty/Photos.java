package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;

import java.io.*;

public class Photos {

    /**
     * Save a new photo in the server directory
     * If a photo already exists with the same path, it will be overwrite
     * @param photo The photo to save
     * @param path The path where to save the photo
     * @throws AddPhotoErrorException TODO add exception
     */
    public static void addPicture(byte[] photo, String path) throws AddPhotoErrorException {

        File file = new File(System.getProperty("user.dir") + path);
        file.getParentFile().mkdir();

        try {
            file.createNewFile();
            File dir = new File(file.getParentFile(), file.getName());
            FileOutputStream writer = new FileOutputStream(dir);
            writer.write(photo);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] retrievePhoto(String path) throws RetrievePhotoErrorException {
        return null;
    }
}
