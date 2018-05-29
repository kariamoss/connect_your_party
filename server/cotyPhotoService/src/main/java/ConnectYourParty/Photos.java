package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Photos {

    /**
     * Save a new photo in the server directory
     * If a photo already exists with the same path, it will be overwrite
     * @param photo The photo to save
     * @param path The path where to save the photo
     * @throws AddPhotoErrorException TODO add exception when wanting to erase file
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
            throw new AddPhotoErrorException("Exception while adding photo : " + e.getMessage());
        }
    }

    public static byte[] retrievePhoto(String pathToFile) throws RetrievePhotoErrorException {
        Path path = Paths.get(System.getProperty("user.dir") + pathToFile);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RetrievePhotoErrorException("Error while trying to retrieve photo : " + e.getMessage());
        }
        return data;
    }
}
