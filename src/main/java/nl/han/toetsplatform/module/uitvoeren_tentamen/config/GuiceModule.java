package nl.han.toetsplatform.module.uitvoeren_tentamen.config;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.CacheDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.CacheDaoMock;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CacheDao.class).to(CacheDaoMock.class);
    }

}
