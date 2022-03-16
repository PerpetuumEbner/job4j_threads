package ru.job4j.storage;

public interface Store {
    boolean add(User user);

    void update(User user);

    void delete(User user);

    int findId(User user);

    void transfer(int fromId, int toId, int amount);
}