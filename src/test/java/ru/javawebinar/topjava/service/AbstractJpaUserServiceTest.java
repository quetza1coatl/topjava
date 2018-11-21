package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractJpaUserServiceTest extends AbstractUserServiceTest{
    @Autowired
    protected JpaUtil jpaUtil;
    @Autowired
    private CacheManager cacheManager;

    @Override
    @Before
    public void setUp() throws Exception {
       jpaUtil.clear2ndLevelHibernateCache();
       cacheManager.getCache("users").clear();
    }

}
