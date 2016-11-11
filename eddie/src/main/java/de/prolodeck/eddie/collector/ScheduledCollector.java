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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grebe on 02.11.2016.
 */
public class ScheduledCollector implements Job {

    private StatusLight statusLight;

    private AdapterConfig config;

    private SystemAdapter adapter;

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        init(jobExecutionContext);
        final List<CurrentState> states = this.adapter.getStates();
        final Map<SystemStateType, Integer> statusCountMap = getStatusCountMap(states);
        pushToStatusLight(states, statusCountMap);
    }

    private void init(final JobExecutionContext jobExecutionContext) {
        this.statusLight = (StatusLight) jobExecutionContext.get("statusLight");
        this.config = (AdapterConfig) jobExecutionContext.get("config");
        this.adapter = SystemAdapterFactory.getInstance(config);
    }

    private Map<SystemStateType, Integer> getStatusCountMap(final List<CurrentState> states) {
        final Map<SystemStateType, Integer> statusCountMap = new HashMap<>();
        for (CurrentState nextState : states) {
            final SystemStateType state = nextState.getState();
            Integer colorCount = statusCountMap.get(state);
            if (colorCount == null) {
                colorCount = 0;
            }
            statusCountMap.put(state, colorCount + 1);
        }
        return statusCountMap;
    }

    private void pushToStatusLight(final List<CurrentState> states, final Map<SystemStateType, Integer> statusCountMap) {
        final int lightCount = this.config.getLightCount();
        int ledOffset = 0;
        for (Map.Entry<SystemStateType, Integer> nextStateEntry : statusCountMap.entrySet()) {
            final int ledsToSwitch = nextStateEntry.getValue() / states.size() * lightCount;
            for (int i = 0; i < ledOffset + ledsToSwitch && i < lightCount; i++) {
                this.statusLight.switchLight(i, nextStateEntry.getKey().getColor());
            }
            ledOffset = ledOffset + ledsToSwitch;
        }
    }

}
