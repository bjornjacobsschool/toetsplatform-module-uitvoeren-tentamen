package nl.han.toetsplatform.module.uitvoeren_tentamen.config;

import com.google.inject.AbstractModule;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.cache.CacheDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.StorageSetupSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.sqlite.VraagDaoSqlite;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.storage.StorageSetupDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub.CacheDaoStub;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.stub.ToetsDaoStub;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.toets.ToetsDao;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen.IUploadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.uploaden_tentamen.UploadenTentamenDAO;
import nl.han.toetsplatform.module.uitvoeren_tentamen.dao.vraag.VraagDao;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CacheDao.class).to(CacheDaoStub.class);
        bind(ToetsDao.class).to(ToetsDaoStub.class);
        bind(VraagDao.class).to(VraagDaoSqlite.class);
        bind(StorageSetupDao.class).to(StorageSetupSqlite.class);
        bind(IUploadenTentamenDAO.class).to(UploadenTentamenDAO.class);

        //TODO: Remove before committing
//        bind(StorageDao.class).to(StorageSqlite.class);
    }

}
