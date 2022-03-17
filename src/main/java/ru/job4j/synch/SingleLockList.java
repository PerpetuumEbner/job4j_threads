package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * В классе создаётся копия коллекции для работы в многопоточной среде.
 *
 * @param <T>
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    /**
     * @param value Значение добавленное в коллекцию.
     */
    public synchronized void add(T value) {
        list.add(value);
    }

    /**
     * @param index Индекс объекта, который нужно вернуть.
     * @return Объект из списка.
     */
    public synchronized T get(int index) {
        return list.get(index);
    }

    /**
     * @return Проходится по копии коллекции
     */
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

    /**
     * @param list Коллекция, которой нужно сделать копию.
     * @return Копия коллекции.
     */
    public List<T> copy(List<T> list) {
        return new ArrayList<>(list);
    }
}