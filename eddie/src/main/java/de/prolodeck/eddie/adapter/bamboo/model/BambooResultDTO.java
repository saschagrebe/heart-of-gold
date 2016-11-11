package de.prolodeck.eddie.adapter.bamboo.model;

/**
 * Created by grebe on 01.11.2016.
 */
public class BambooResultDTO {

    private BambooLinkDTO link;

    private BambooPlanDTO plan;

    private String buildResultKey;

    private String lifeCycleState;

    private int id;

    private String key;

    private BambooPlanResultKeyDTO planResultKey;

    private String state;

    private String buildState;

    private int number;

    private int buildNumber;

    public BambooLinkDTO getLink() {
        return link;
    }

    public void setLink(BambooLinkDTO link) {
        this.link = link;
    }

    public BambooPlanDTO getPlan() {
        return plan;
    }

    public void setPlan(BambooPlanDTO plan) {
        this.plan = plan;
    }

    public String getBuildResultKey() {
        return buildResultKey;
    }

    public void setBuildResultKey(String buildResultKey) {
        this.buildResultKey = buildResultKey;
    }

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(String lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BambooPlanResultKeyDTO getPlanResultKey() {
        return planResultKey;
    }

    public void setPlanResultKey(BambooPlanResultKeyDTO planResultKey) {
        this.planResultKey = planResultKey;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBuildState() {
        return buildState;
    }

    public void setBuildState(String buildState) {
        this.buildState = buildState;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }
}
