package de.prolodeck.eddie;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.prolodeck.eddie.adapter.bamboo.BambooAdapter;
import de.prolodeck.eddie.collector.ScheduledCollector;
import de.prolodeck.eddie.configuration.AdapterConfig;
import de.prolodeck.eddie.configuration.CollectorConfig;
import de.prolodeck.eddie.heartofgold.Eddie;
import de.prolodeck.eddie.heartofgold.StatusLight;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by grebe on 20.10.2016.
 */
public class Bootstrap {

    public static void main(String[] args) throws Exception {
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.loadConfig();
        bootstrap.initializeEddie();
        bootstrap.initializeCollectors();

        // wait until its over
        park();
    }

    private Scheduler scheduler;

    private CollectorConfig config;

    private Eddie eddie;

    private void loadConfig() {
        try (final InputStream inputStream = this.getClass().getResourceAsStream("config.json")) {
            final ObjectMapper mapper = new ObjectMapper();
            this.config = mapper.readValue(inputStream, CollectorConfig.class);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeEddie() {
        eddie = new Eddie(config.getServerIp(), config.getServerPort());
    }

    private void initializeCollectors() {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();

            scheduleCollector(config.getLowerStatusLightAdapter(), eddie.lower());
            scheduleCollector(config.getUpperStatusLightAdapter(), eddie.upper());
        } catch(SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private void scheduleCollector(final AdapterConfig config, final StatusLight statusLight) throws SchedulerException {
        if (!config.isValid()) {
            System.out.println("Configuration is not valid");
            return;
        }

        final JobDataMap map = new JobDataMap();
        map.put("statusLight", statusLight);
        map.put("config", config);

        final JobDetail job = JobBuilder.newJob(ScheduledCollector.class)
                .usingJobData(map)
                .build();

        final Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInSeconds(config.getRefreshRate())
                                .repeatForever())
                .build();

        scheduler.scheduleJob(job, trigger);
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
