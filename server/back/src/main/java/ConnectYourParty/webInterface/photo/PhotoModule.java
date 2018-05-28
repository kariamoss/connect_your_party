package ConnectYourParty.webInterface.photo;

import ConnectYourParty.chooser.PhotoChooser;
import ConnectYourParty.services.photo.IPhotoService;

public class PhotoModule implements IPhotoModule {

    @Override
    public String addPhoto() {
        PhotoChooser photoChooser = new PhotoChooser();
        IPhotoService photoService = photoChooser.getService();
        //photoService.addPhoto(url);
        return "salut";
    }

    @Override
    public String getPhotos() {
        PhotoChooser photoChooser = new PhotoChooser();
        IPhotoService photoService = photoChooser.getService();
        //photoService.getPhotos(url);
        return "Vla tes photos";
    }
}
