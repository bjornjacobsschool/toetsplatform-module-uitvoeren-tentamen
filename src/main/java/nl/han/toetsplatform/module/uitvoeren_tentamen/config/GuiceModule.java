package nl.han.toetsplatform.module.uitvoeren_tentamen.config;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.cache.CacheDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.StorageSetupSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.TentamenDAOSQLite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.VraagDaoSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub.CacheDaoStub;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.tentamen.TentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CacheDao.class).to(CacheDaoStub.class);
        bind(TentamenDAO.class).to(TentamenDAOSQLite.class);
        bind(VraagDao.class).to(VraagDaoSqlite.class);
        bind(StorageSetupDao.class).to(StorageSetupSqlite.class);
        bind(VraagDao.class).to(VraagDaoSqlite.class);

        //TODO: Remove before committing
//        bind(StorageDao.class).to(StorageSqlite.class);
    }

}
