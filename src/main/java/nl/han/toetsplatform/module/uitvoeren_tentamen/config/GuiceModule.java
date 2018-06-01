package nl.han.toetsplatform.module.uitvoeren_tentamen.config;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.CacheDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.ToetsDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.VraagDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.VraagDaoSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub.CacheDaoStub;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub.ToetsDaoStub;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CacheDao.class).to(CacheDaoStub.class);
        bind(ToetsDao.class).to(ToetsDaoStub.class);
        bind(VraagDao.class).to(VraagDaoSqlite.class);
    }

}
