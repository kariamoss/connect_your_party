package ConnectYourParty.database;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.photo.Photo;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.exception.NoSuchPhotoException;
import ConnectYourParty.exception.PhotoAlreadyExistException;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

public class DbMock {
    public static Event event = new Event(0, "La_grande_soirée_costumée", 65, "12, route de Virey 70700 Charcenne");
    public static User user = new User("Milleret", "Jehan");
}