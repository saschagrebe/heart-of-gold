package de.prolodeck.eddie.collector;

import de.prolodeck.eddie.adapter.CurrentState;
import de.prolodeck.eddie.adapter.SystemAdapter;
import de.prolodeck.eddie.adapter.SystemAdapterFactory;
import de.prolodeck.eddie.adapter.SystemStateType;
import de.prolodeck.eddie.configuration.AdapterConfig;
import de.prolodeck.eddie.heartofgold.StatusLight;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by grebe on 02.11.2016.
 */
public class ScheduledCollector implements Job {

    private static final Logger log = LoggerFactory.getLogger(ScheduledCollector.class);

    private StatusLight statusLight;

    private AdapterConfig config;

    private SystemAdapter adapter;

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        init(jobExecutionContext);
        try {
            final List<CurrentState> states = this.adapter.getStates();
            final Map<SystemStateType, Integer> statusCountMap = getStatusCountMap(states);
            pushToStatusLight(states, statusCountMap);
        } catch(Exception e) {
            log.error("Failed collection of data.", e);
        }
    }

    private void init(final JobExecutionContext jobExecutionContext) {
        this.statusLight = (StatusLight) jobExecutionContext.getMergedJobDataMap().get("statusLight");
        this.config = (AdapterConfig) jobExecutionContext.getMergedJobDataMap().get("config");
        this.adapter = SystemAdapterFactory.getInstance(config);
    }

    private Map<SystemStateType, Integer> getStatusCountMap(final List<CurrentState> states) {
        // use tree map to have a sorted result
        final Map<SystemStateType, Integer> statusCountMap = new TreeMap<>();
        for (CurrentState nextState : states) {
            final SystemStateType state = nextState.getState();
            Integer colorCount = statusCountMap.get(state);
            if (colorCount == null) {
                colorCount = 0;
            }
            statusCountMap.put(state, colorCount + 1);
        }
        if (log.isInfoEnabled()) {
            log.info("Found " + statusCountMap + " different states.");
        }

        return statusCountMap;
    }

    private void pushToStatusLight(final List<CurrentState> states, final Map<SystemStateType, Integer> statusCountMap) {
        final int lightCount = this.config.getLightCount();
        int ledOffset = 0;
        for (Map.Entry<SystemStateType, Integer> nextStateEntry : statusCountMap.entrySet()) {
            final int ledsToSwitch = BigDecimal.valueOf(nextStateEntry.getValue())
                    .divide(BigDecimal.valueOf(states.size()))
                    .multiply(BigDecimal.valueOf(lightCount))
                    .setScale(0, RoundingMode.UP)
                    .intValue();
            final SystemStateType currentState = nextStateEntry.getKey();
            if (log.isInfoEnabled()) {
                log.info("Switch " + ledsToSwitch + " LEDs to " + currentState);
            }

            for (int i = ledOffset; i < ledOffset + ledsToSwitch && i < lightCount; i++) {
                pushLedState(i, currentState);
            }
            ledOffset = ledOffset + ledsToSwitch;
        }
    }

    private void pushLedState(final int ledIndex, final SystemStateType currentState) {
        this.statusLight.switchLight(ledIndex, currentState.getColor());
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
