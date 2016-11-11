package de.prolodeck.eddie.adapter.bamboo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by grebe on 01.11.2016.
 */
public class BambooResultsDTO {

    private int size;

    private String expand;

    @JsonProperty(value = "start-index")
    private int startIndex;

    @JsonProperty(value = "max-result")
    private int maxResult;

    @JsonDeserialize()
    private List<BambooResultDTO> result = new LinkedList<>();

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List<BambooResultDTO> getResult() {
        return result;
    }

    public void setResult(List<BambooResultDTO> result) {
        this.result = result;
    }
}
