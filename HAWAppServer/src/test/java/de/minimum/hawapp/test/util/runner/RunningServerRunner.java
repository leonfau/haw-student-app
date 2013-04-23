package de.minimum.hawapp.test.util.runner;

import java.io.IOException;

import org.apache.catalina.LifecycleException;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.JUnit4;
import org.junit.runners.model.InitializationError;

public class RunningServerRunner extends Runner {
    private static final Server SERVER = new Server();

    private Runner runner;

    public RunningServerRunner(Class<?> klass) throws InitializationError {
        this.runner = new JUnit4(klass);
    }

    @Override
    public Description getDescription() {
        return this.runner.getDescription();
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            synchronized(RunningServerRunner.SERVER) {
                if (!RunningServerRunner.SERVER.isRunning()) {
                    RunningServerRunner.SERVER.startup();

                }
            }
            this.runner.run(notifier);
        }
        catch(IOException | LifecycleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int testCount() {
        return this.runner.testCount();
    }

    @Override
    public int hashCode() {
        return this.runner.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.runner.equals(obj);
    }

    @Override
    public String toString() {
        return this.runner.toString();
    }
}
