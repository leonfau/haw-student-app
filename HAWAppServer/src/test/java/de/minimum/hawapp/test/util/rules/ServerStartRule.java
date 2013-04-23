package de.minimum.hawapp.test.util.rules;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.FileUtils;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ServerStartRule implements TestRule {

    private static final String APP_ID = "server";
    private static final int PORT = 8080;

    public static final String SERVER_ADDRESS = "http://localhost:" + ServerStartRule.PORT + "/"
                    + ServerStartRule.APP_ID;

    @Override
    public Statement apply(Statement arg0, Description arg1) {
        return new MyStatement(arg0);
    }

    private class MyStatement extends Statement {
        private Statement base;
        // InstalledLocalContainer container;
        /** The tomcat instance. */
        private Tomcat mTomcat;
        /** The temporary directory in which Tomcat and the app are deployed. */
        private String mWorkingDir = System.getProperty("java.io.tmpdir");

        MyStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            startServer();
            this.base.evaluate();
            stopServer();

        }

        private void startServer() throws IOException, LifecycleException {
            this.mTomcat = new Tomcat();
            this.mTomcat.setPort(ServerStartRule.PORT);
            this.mTomcat.setBaseDir(this.mWorkingDir);
            this.mTomcat.getHost().setAppBase(this.mWorkingDir);
            this.mTomcat.getHost().setAutoDeploy(true);
            this.mTomcat.getHost().setDeployOnStartup(true);
            String contextPath = "/" + ServerStartRule.APP_ID;
            File webApp = new File(this.mWorkingDir, ServerStartRule.APP_ID);
            File oldWebApp = new File(webApp.getAbsolutePath());
            FileUtils.deleteDirectory(oldWebApp);
            new ZipExporterImpl(createWebArchive()).exportTo(new File(this.mWorkingDir + "/" + ServerStartRule.APP_ID
                            + ".war"), true);
            this.mTomcat.addWebapp(this.mTomcat.getHost(), contextPath, webApp.getAbsolutePath());
            this.mTomcat.start();
        }

        private Archive<?> createWebArchive() {
            WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
            war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                            .importDirectory("src/main/webapp").as(GenericArchive.class), "/", Filters.includeAll());
            return war;
        }

        private void stopServer() throws LifecycleException {
            if (this.mTomcat.getServer() != null && this.mTomcat.getServer().getState() != LifecycleState.DESTROYED) {
                if (this.mTomcat.getServer().getState() != LifecycleState.STOPPED) {
                    this.mTomcat.stop();
                }
                this.mTomcat.destroy();
            }
        }
    }

}
