package ru.job4j.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStoreTest {
    @Test
    public void whenTransfer() {
        UserStore store = new UserStore();
        User userFist = new User(0, 100);
        User userSecond = new User(1, 200);
        store.add(userFist);
        store.add(userSecond);
        store.transfer(0, 1, 50);
        assertEquals(50, userFist.getAmount());
        assertEquals(250, userSecond.getAmount());
    }
}