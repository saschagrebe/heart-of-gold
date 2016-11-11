package de.prolodeck.eddie.adapter.bamboo.model;

/**
 * Created by grebe on 01.11.2016.
 */
public class BambooResponseDTO {

    private BambooResultsDTO results;

    private String expand;

    private BambooLinkDTO link;

    public BambooResultsDTO getResults() {
        return results;
    }

    public void setResults(BambooResultsDTO results) {
        this.results = results;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public BambooLinkDTO getLink() {
        return link;
    }

    public void setLink(BambooLinkDTO link) {
        this.link = link;
    }
}
