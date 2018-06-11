package ConnectYourParty.database;

import ConnectYourParty.businessObjects.Event;
import ConnectYourParty.businessObjects.User;

public class DbMock {
    public static Event event = new Event(0, "La_grande_soirée_costumée", 65, "12, route de Virey 70700 Charcenne");
    public static User user = new User("Jehan","salut");
    public static User user1 = new User("Lucas","hello");
    public static User user2 = new User("Josue","oui");
    public static User user3 = new User("Marc","bonjour");
}