package fr.dut.ptut2021;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    // FOR DATA
    private CreateDatabase database;

    // DATA SET FOR TEST
    private static User USER1 = new User("Léon", 1);
    private static User USER2 = new User( "Philippe", 1);
    private static User USER3 = new User( "Rémi", 1);
    private static User USER4 = new User( "Robin", 1);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.databaseBuilder(InstrumentationRegistry.getContext(),
                CreateDatabase.class,
                "database.db")
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.userDao().createUser(new User("Léon",1));
        this.database.userDao().createUser(new User("Axel",1));

        // TEST
        User user1 = getDb.getValue(this.database.userDao().getUser(1));
        User user2 = getDb.getValue(this.database.userDao().getUser(2));
        //assertTrue(user.getName().equals(USER1.getName()) && user.getId() == USER_ID);
        System.out.println(user1.getName());
        System.out.println(user2.getName());

        //this.database.userDao().deleteAllUsers();
        //user1 = getDb.getValue(this.database.userDao().getUser(1));
        //System.out.println(user1.getName());
    }

    @Test
    public void insertAndUpdateUser() throws InterruptedException {
        // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
        this.database.userDao().createUser(new User("William",1));
        this.database.userDao().createUser(new User("Axel",1));
        this.database.userDao().createUser(new User("Alban",1));
        User useradd = getDb.getValue(this.database.userDao().getUser(2));
        useradd.setName("Patrick");
        this.database.userDao().updateUser(useradd);

        //TEST
        User user1 = getDb.getValue(this.database.userDao().getUser(1));
        User user2 = getDb.getValue(this.database.userDao().getUser(2));
        //assertTrue(users.get(0).getSelected());
        System.out.println(user1.getName());
        System.out.println(user2.getName());
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        this.database.userDao().createUser(new User("Léon",1));
        this.database.userDao().createUser(new User("Axel",1));
        this.database.userDao().deleteUser(1);

        //TEST
        User user1 = getDb.getValue(this.database.userDao().getUser(1));
        User user2 = getDb.getValue(this.database.userDao().getUser(2));

        //System.out.println(user1.getName());
        System.out.println(user2.getName());
    }

    @Test
    public void getAllUsers() throws InterruptedException {
        this.database.userDao().createUser(new User("William",1));
        this.database.userDao().createUser(new User("Axel",1));
        this.database.userDao().createUser(new User("Alban",1));

        List<User> test = getDb.getValue(this.database.userDao().getAllUsers());
        for (User user : test) {
            System.out.println(user.getName());
        }

    }

}