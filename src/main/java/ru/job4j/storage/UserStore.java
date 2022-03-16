package ru.job4j.storage;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.ArrayList;
import java.util.List;
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
     * Все операции с пользователями происходят хранилище в ArrayList.
     */
    private final List<User> storage = new ArrayList<>();
    private final String WARNING = "Пользователь не найден!";
    /**
     * @param user Добавление пользователя.
     * @return Возвращает добавленного пользователя.
     */
    @Override
    public synchronized boolean add(User user) {
        return storage.add(user);
    }
    /**
     * @param user Обновление данных о пользователе.
     */
    @Override
    public synchronized void update(User user) {
        int index = findId(user);
        if (index != -1) {
            storage.add(index, user);
        } else {
            throw new IllegalArgumentException(WARNING);
        }
    }
    /**
     * @param user Удаление пользователя.
     */
    @Override
    public synchronized void delete(User user) {
        int index = findId(user);
        if (index != -1) {
            storage.remove(findId(user));
        } else {
            throw new IllegalArgumentException(WARNING);
        }
    }
    /**
     * @param user Поиск id пользователя в хранилище ArrayList.
     * @return id пользователя.
     */
    @Override
    public synchronized int findId(User user) {
        int result = -1;
        for (int index = 0; index < storage.size(); index++) {
            if (storage.get(index).getId() == user.getId()) {
                result = index;
            } else {
                throw new IllegalArgumentException(WARNING);
            }
        }
        return result;
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
        if (userFist != null && userSecond != null) {
            userFist.setAmount(userFist.getAmount() - amount);
            userSecond.setAmount(userSecond.getAmount() + amount);
        } else {
            throw new IllegalArgumentException("Операция не выполнена.");
        }
    }
}