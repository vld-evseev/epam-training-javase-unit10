package training.tasks.unit10.web.listener;


import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import training.tasks.unit10.dao.UserDao;
import training.tasks.unit10.dao.pg.PgUserDao;
import training.tasks.unit10.db.*;
import training.tasks.unit10.service.SecurityService;
import training.tasks.unit10.service.UserService;
import training.tasks.unit10.service.impl.SecurityServiceImpl;
import training.tasks.unit10.service.impl.UserServiceImpl;
import training.tasks.unit10.web.servlet.*;

import javax.inject.Singleton;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class GuiceConfig extends GuiceServletContextListener {

    private static class PostgresDbModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(PGConfig.class).toProvider(PgConfigProvider.class).in(Singleton.class);
            bind(DataSource.class).toProvider(PostgresDataSourceProvider.class).in(Singleton.class);
            bind(DbManager.class).to(PostgresManager.class).in(Singleton.class);
        }
    }

    private static class DependencyModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(UserDao.class).to(PgUserDao.class).in(Singleton.class);
            bind(SecurityService.class).to(SecurityServiceImpl.class).in(Singleton.class);
            bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
        }
    }

    private static class ServletConfigModule extends ServletModule {
        @Override
        protected void configureServlets() {
            serve("/login").with(LoginController.class);
            serve("/locale").with(LocaleController.class);
            serve("/user/profile").with(ProfileController.class);
            serve("/registration").with(RegistrationController.class);
            serve("/").with(RootController.class);
        }
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new DependencyModule(),
                new ServletConfigModule(),
                new PostgresDbModule()
        );
    }
}
