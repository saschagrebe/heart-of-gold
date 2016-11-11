package de.prolodeck.eddie;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.prolodeck.eddie.collector.CollectorScheduler;

/**
 * Created by grebe on 20.10.2016.
 */
public class Bootstrap {

    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(new Module());

        // schedule collectors
        final CollectorScheduler scheduler = injector.getInstance(CollectorScheduler.class);
        scheduler.start();

        // wait until its over
        park();
    }

    private static void park() {
        try {
            final Object lock = new Object();
            synchronized (lock) {
                lock.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
