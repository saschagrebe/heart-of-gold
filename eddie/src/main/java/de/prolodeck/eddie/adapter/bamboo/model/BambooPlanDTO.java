package de.prolodeck.eddie.adapter.bamboo.model;

/**
 * Created by grebe on 01.11.2016.
 */
public class BambooPlanDTO {

    private String shortName;

    private String shortKey;

    private String type;

    private boolean enabled;

    private BambooLinkDTO link;

    private String key;

    private String name;

    private BambooEntityKeyDTO planKey;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public BambooLinkDTO getLink() {
        return link;
    }

    public void setLink(BambooLinkDTO link) {
        this.link = link;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BambooEntityKeyDTO getPlanKey() {
        return planKey;
    }

    public void setPlanKey(BambooEntityKeyDTO planKey) {
        this.planKey = planKey;
    }
}
