package connectYourParty.businessObjects.service;

import connectYourParty.exception.NoSuchModuleException;
import connectYourParty.services.IService;
import connectYourParty.services.payment.IPaymentService;
import connectYourParty.services.photo.IPhotoService;

public enum Module {
    PHOTO(IPhotoService.class,"photo"),
    MUSIC(IService.class,"music"),
    PAYMENT(IPaymentService.class,"payment");

    private Class modClass;
    private String id;

    Module(Class<? extends IService> modClass,String id){
        this.modClass = modClass;
        this.id = id;
    }

    public Class getModClass() {
        return modClass;
    }

    public static Module getModule(String moduleName) throws NoSuchModuleException {
        for(Module module : Module.values()) {
            if(module.id.equals(moduleName)){
                return module;
            }
        }
        throw new NoSuchModuleException();
    }
}
