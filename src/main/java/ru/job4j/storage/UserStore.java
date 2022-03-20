package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * В классе происходит добавление, изменение, поиск пользователей, а также перевод денежных средств.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
public class UserStore implements Store {
    @GuardedBy("this")
    /**
     * Все операции с пользователями происходят в хранилище HashMap.
     */
    private final Map<Integer, User> storage = new HashMap<>();

    /**
     * @param user Добавление пользователя.
     * @return Возвращает добавленного пользователя.
     */
    @Override
    public synchronized boolean add(User user) {
        return storage.putIfAbsent(user.getId(), user) == null;
    }

    /**
     * @param user Обновление данных о пользователе.
     * @return Возвращает изменённого пользователя.
     */
    @Override
    public synchronized boolean update(User user) {
        return storage.replace(user.getId(), user) != null;
    }

    /**
     * @param user Удаление пользователя.
     * @return Возвращает удалённого пользователя.
     */
    @Override
    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    /**
     * @param fromId id пользователя, который переводить деньги.
     * @param toId   id пользователя, которому переводят деньги.
     * @param amount сумма перевода.
     */
    @Override
    public synchronized void transfer(int fromId, int toId, int amount) {
        User userFist = storage.get(fromId);
        User userSecond = storage.get(toId);
        if (userFist != null && userSecond != null && userFist.getAmount() >= amount) {
            userFist.setAmount(userFist.getAmount() - amount);
            userSecond.setAmount(userSecond.getAmount() + amount);
        } else {
            throw new IllegalArgumentException("Операция не выполнена.");
        }
    }
}