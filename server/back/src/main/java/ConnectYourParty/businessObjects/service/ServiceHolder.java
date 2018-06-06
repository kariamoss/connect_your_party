package ConnectYourParty.businessObjects.service;

import ConnectYourParty.modulesLogic.service.ByteClassLoader;
import ConnectYourParty.services.IService;

public class ServiceHolder {
    private Module module;

    private byte[] classBin;

    public ServiceHolder(Module module, byte[] classBin) {
        this.module = module;
        this.classBin = classBin;
    }

    public IService getService() throws Exception{
        IService service;

        ByteClassLoader byteLoader = new ByteClassLoader(this.getClass().getClassLoader());

        service = (IService) byteLoader.getClassFromByte(this.classBin).newInstance();

        return service;
    }
}
