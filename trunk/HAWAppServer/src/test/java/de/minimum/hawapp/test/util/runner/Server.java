package de.minimum.hawapp.test.util.runner;

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

class Server {
    private static final String APP_ID = "server";
    private static final int PORT = 8080;

    public static final String SERVER_ADDRESS = "http://localhost:" + Server.PORT + "/" + Server.APP_ID;

    /** The tomcat instance. */
    private Tomcat mTomcat;
    /** The temporary directory in which Tomcat and the app are deployed. */
    private String mWorkingDir = System.getProperty("java.io.tmpdir");
    private boolean running = false;

    public synchronized void startup() throws IOException, LifecycleException {
        this.mTomcat = new Tomcat();
        this.mTomcat.setPort(Server.PORT);
        this.mTomcat.setBaseDir(this.mWorkingDir);
        this.mTomcat.getHost().setAppBase(this.mWorkingDir);
        this.mTomcat.getHost().setAutoDeploy(true);
        this.mTomcat.getHost().setDeployOnStartup(true);
        String contextPath = "/" + Server.APP_ID;
        File webApp = new File(this.mWorkingDir, Server.APP_ID);
        File oldWebApp = new File(webApp.getAbsolutePath());
        FileUtils.deleteDirectory(oldWebApp);
        new ZipExporterImpl(createWebArchive()).exportTo(new File(this.mWorkingDir + "/" + Server.APP_ID + ".war"),
                        true);
        this.mTomcat.addWebapp(this.mTomcat.getHost(), contextPath, webApp.getAbsolutePath());
        this.mTomcat.start();
        this.running = true;
    }

    public synchronized boolean isRunning() {
        return this.running;
    }

    private Archive<?> createWebArchive() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");
        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class).importDirectory("src/main/webapp")
                        .as(GenericArchive.class), "/", Filters.includeAll());
        return war;
    }

    public synchronized void shutdown() throws LifecycleException {
        if (this.mTomcat.getServer() != null && this.mTomcat.getServer().getState() != LifecycleState.DESTROYED) {
            if (this.mTomcat.getServer().getState() != LifecycleState.STOPPED) {
                this.mTomcat.stop();
            }
            this.mTomcat.destroy();
        }
        this.running = false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.running)
            this.shutdown();
    }
}
