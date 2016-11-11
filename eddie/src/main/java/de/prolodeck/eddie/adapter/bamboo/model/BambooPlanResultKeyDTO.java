package de.prolodeck.eddie.adapter.bamboo.model;

/**
 * Created by grebe on 01.11.2016.
 */
public class BambooPlanResultKeyDTO extends BambooEntityKeyDTO {

    private BambooEntityKeyDTO entityKey;

    private int resultNumber;

    public BambooEntityKeyDTO getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(BambooEntityKeyDTO entityKey) {
        this.entityKey = entityKey;
    }

    public int getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }
}
