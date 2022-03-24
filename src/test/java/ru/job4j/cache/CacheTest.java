package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddModel() {
        Cache cache = new Cache();
        Base base = (new Base(1, 1));
        base.setName("Base");
        cache.add(base);
        assertThat(cache.getCache().get(1).getId(), is(base.getId()));
        assertThat(cache.getCache().get(1).getName(), is(base.getName()));
        assertThat(cache.getCache().get(1).getVersion(), is(base.getVersion()));
    }

    @Test
    public void whenUpdateModel() {
        Cache cache = new Cache();
        Base base = (new Base(1, 1));
        base.setName("Base");
        cache.add(base);
        Base newBase = (new Base(1, 1));
        base.setName("newBase");
        cache.update(newBase);
        assertThat(cache.getCache().get(1).getName(), is(newBase.getName()));
        assertThat(cache.getCache().get(1).getVersion(), is(2));
    }

    @Test
    public void whenDeleteModel() {
        Cache cache = new Cache();
        Base base = (new Base(1, 1));
        base.setName("Base");
        cache.add(base);
        cache.delete(base);
        assertTrue(cache.getCache().isEmpty());
    }

    @Test(expected = OptimisticException.class)
    public void whenFailedToUpdate() {
        Cache cache = new Cache();
        Base base = (new Base(1, 1));
        base.setName("Base");
        cache.add(base);
        Base newBase = (new Base(1, 2));
        base.setName("newBase");
        cache.update(newBase);
    }
}