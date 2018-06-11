package ConnectYourParty.database;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.User;
import ConnectYourParty.businessObjects.service.UserStatus;

public class DbMock {
    public static Event event = new Event(0, "La_grande_soirée_costumée", 65, "12, route de Virey 70700 Charcenne");
    public static User user = new User("Jehan","salut", UserStatus.ADMIN);
    public static User user1 = new User("Lucas","hello",UserStatus.DEFAULT);
    public static User user2 = new User("Josue","oui",UserStatus.DEFAULT);
    public static User user3 = new User("Marc","bonjour",UserStatus.ADMIN);
}