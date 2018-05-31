package ConnectYourParty;

import ConnectYourParty.exceptions.photo.AddPhotoErrorException;
import ConnectYourParty.exceptions.photo.CannotDeletePhotoException;
import ConnectYourParty.exceptions.photo.RetrievePhotoErrorException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Photos {

    /**
     * Save a new photo in the server directory
     * If a photo already exists with the same path, it will be overwrite
     * @param photo The photo to save
     * @param path The path where to save the photo
     * @throws AddPhotoErrorException {@// TODO: 30/05/18 add exception when wanting to erase file }
     */
    public static void addPicture(byte[] photo, String path) throws AddPhotoErrorException {

        File file = new File(System.getProperty("user.dir") + path);

        try {
            Files.createDirectories(file.getParentFile().toPath());
            file.createNewFile();
            File dir = new File(file.getParentFile(), file.getName());
            FileOutputStream writer = new FileOutputStream(dir);
            writer.write(photo);
            writer.close();
        } catch (IOException e) {
            throw new AddPhotoErrorException("Exception while adding photo : " + e.getMessage() + "\n"+ Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Retrieve one photo that had been saved previously
     * If no photo corresponds to the path, throw an {@link RetrievePhotoErrorException}
     * @param pathToFile the path to the photo we want to retrieve
     * @return a byte array, containing our photo
     * @throws RetrievePhotoErrorException if no photo corresponds to the path
     */
    public static byte[] retrievePhoto(String pathToFile) throws RetrievePhotoErrorException {
        Path path = Paths.get(System.getProperty("user.dir") + pathToFile);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RetrievePhotoErrorException("Error while trying to retrieve photo : " + e.getMessage()
                    + "\n"+ Arrays.toString(e.getStackTrace()));
        }
        return data;
    }


    public static void removePhoto(String pathToFile) throws CannotDeletePhotoException {
        Path path = Paths.get(System.getProperty("user.dir") + pathToFile);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new CannotDeletePhotoException("Error while deleting a photo : " + e.getMessage()
                    + "\n"+ Arrays.toString(e.getStackTrace()));
        }
    }
}
