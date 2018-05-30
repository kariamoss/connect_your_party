package ConnectYourParty;

import ConnectYourParty.database.DbMock;
import ConnectYourParty.database.businessObjects.Photo;
import ConnectYourParty.database.businessObjects.User;
import ConnectYourParty.exception.PhotoAlreadyExistException;
import ConnectYourParty.modulesLogic.interpreter.PhotoInterpreter;
import ConnectYourParty.requestObjects.photo.PhotoHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ListPhotoTest {

    @Before
    public void init(){
        DbMock.clean();
    }

    @Test
    public void addPhotoAndGetList() throws PhotoAlreadyExistException {
        DbMock.addPhoto(new Photo("salut", "name", new User("milleret", "jehan"), "Dropbox"));

        PhotoInterpreter photoInterpreter = new PhotoInterpreter();
        List<PhotoHolder> photoHolderList = photoInterpreter.getPhotoList();

        Assert.assertEquals(1, photoHolderList.size());

        PhotoHolder photoHolder = photoHolderList.get(0);

        Assert.assertEquals("salut", photoHolder.photoPath);
        Assert.assertEquals("name", photoHolder.name);
        Assert.assertEquals("jehan milleret", photoHolder.user);
        Assert.assertEquals("Dropbox", photoHolder.serviceHost);
    }
}
