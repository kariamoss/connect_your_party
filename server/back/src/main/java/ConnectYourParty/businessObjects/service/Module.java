package ConnectYourParty.businessObjects.service;

import ConnectYourParty.services.IService;
import ConnectYourParty.services.photo.IPhotoService;

public enum Module {
    PHOTO(IPhotoService.class),
    MUSIC(IService.class);

    private Class modClass;

    Module(Class<? extends IService> modClass){
        this.modClass = modClass;
    }

    public Class getModClass() {
        return modClass;
    }
}
