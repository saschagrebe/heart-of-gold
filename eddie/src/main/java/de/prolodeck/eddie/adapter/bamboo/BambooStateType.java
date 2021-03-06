package de.prolodeck.eddie.adapter.bamboo;

import de.prolodeck.eddie.adapter.SystemStateType;

/**
 * Created by grebe on 28.10.2016.
 */
public enum BambooStateType implements SystemStateType {

    // green
    SUCCESS("Successful", 0x7F00),

    // red
    ERROR("Failed", 0xFF);

    private final String state;

    private final int color;

    private BambooStateType(final String state, final int color) {
        this.state = state;
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    public static BambooStateType valueOfState(final String state) {
        for (BambooStateType nextType : values()) {
            if (nextType.state.equals(state)) {
                return nextType;
            }
        }
        return null;
    }
}
