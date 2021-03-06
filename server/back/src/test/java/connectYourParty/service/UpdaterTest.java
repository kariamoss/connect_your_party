package connectYourParty.service;

import connectYourParty.businessObjects.service.Module;
import connectYourParty.businessObjects.service.ServiceHolder;
import connectYourParty.modulesLogic.service.Subscriber;
import connectYourParty.modulesLogic.service.Updater;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdaterTest {

    private Updater updater;
    private Subscriber subs;

    private int onAddCalled;
    private int onRemoveCalled;
    private int onUnsubscribeCalled;

    @Before
    public void init(){
        this.updater = new Updater();
        this.onAddCalled = 0;
        this.onRemoveCalled = 0;
        this.onUnsubscribeCalled = 0;

        this.subs = new Subscriber() {
            @Override
            public void onAdd(ServiceHolder holder) {
                onAddCalled++;
            }

            @Override
            public void onRemove(ServiceHolder holder) {
                onRemoveCalled++;
            }

            @Override
            public void onUnsubscribe() {
                onUnsubscribeCalled++;
            }
        };
    }

    @Test
    public void subTest(){
        this.updater.subscribe(this.subs, Module.PHOTO);

        this.updater.add(null,Module.PHOTO);
        this.updater.remove(null,Module.PHOTO);

        assertEquals(1,this.onAddCalled);
        assertEquals(1,this.onRemoveCalled);
    }

    @Test
    public void unsubTest(){
        this.updater.subscribe(this.subs,Module.PHOTO);

        this.updater.unsubscribe(this.subs,Module.PHOTO);

        this.updater.add(null,Module.PHOTO);
        this.updater.remove(null,Module.PHOTO);

        assertEquals(0,this.onAddCalled);
        assertEquals(0,this.onRemoveCalled);
    }

    @Test
    public void wrongModule(){
        this.updater.subscribe(this.subs,Module.PHOTO);

        this.updater.add(null,Module.MUSIC);
        this.updater.remove(null,Module.MUSIC);

        assertEquals(0,this.onAddCalled);
        assertEquals(0,this.onRemoveCalled);
    }

    @Test
    public void unsubAll(){
        this.updater.subscribe(this.subs,Module.MUSIC);
        this.updater.subscribe(this.subs,Module.PHOTO);

        this.updater.unSubscribeAll();

        this.updater.add(null,Module.MUSIC);
        this.updater.remove(null,Module.MUSIC);
        this.updater.add(null,Module.PHOTO);
        this.updater.remove(null,Module.PHOTO);

        assertEquals(0,this.onAddCalled);
        assertEquals(0,this.onRemoveCalled);


    }


}
