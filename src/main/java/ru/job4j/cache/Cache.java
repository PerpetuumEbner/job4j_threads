package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * В классе используются потокобезопасные методы для работы с кэшем, проверяя версии объектов.
 *
 * @author yustas
 * @version 1.0
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    /**
     * Добавляется объект в кэш, если его ещё не существовало.
     *
     * @param model Объект модели который нужно добавить.
     * @return Объект добавленный в кэш.
     */
    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    /**
     * Обновляется объект в кэше, если версии совпадают, при этом версия инкрементируется.
     *
     * @param model Объект модели который нужно обновить.
     * @return Обновлённый объект.
     */
    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base result = new Base(model.getId(), model.getVersion() + 1);
            result.setName(model.getName());
            return result;
        }) != null;
    }

    /**
     * Удаление объекта из кэша.
     *
     * @param model Объект модели который нужно удалить.
     */
    public void delete(Base model) {
        memory.remove(model.getId());
    }

    /**
     * @return Коллекция, в которой хранятся модели объектов.
     */
    public Map<Integer, Base> getCache() {
        return memory;
    }
}