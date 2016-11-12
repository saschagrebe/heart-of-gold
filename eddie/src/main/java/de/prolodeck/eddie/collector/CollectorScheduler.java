package de.prolodeck.eddie.collector;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.prolodeck.eddie.configuration.AdapterConfig;
import de.prolodeck.eddie.configuration.CollectorConfig;
import de.prolodeck.eddie.heartofgold.EddieService;
import de.prolodeck.eddie.heartofgold.StatusLight;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by grebe on 12.11.2016.
 */
@Singleton
public class CollectorScheduler {

    private static final Logger log = LoggerFactory.getLogger(CollectorScheduler.class);

    @Inject
    private CollectorConfig config;

    @Inject
    private EddieService eddie;

    private Scheduler scheduler;

    public void start() {
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
            log.info("Configuration is not valid");
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

}
