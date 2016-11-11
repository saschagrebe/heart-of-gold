package de.prolodeck.eddie.adapter;

/**
 * Created by grebe on 28.10.2016.
 */
public final class CurrentState {

    private final String name;

    private final SystemStateType state;

    public CurrentState(final String name, final SystemStateType state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public SystemStateType getState() {
        return state;
    }
}
