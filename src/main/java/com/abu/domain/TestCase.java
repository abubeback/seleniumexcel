package com.abu.domain;

import java.util.ArrayList;
import java.util.List;

public class TestCase {

    /**
     * first and last row index for margin cell
     */
    private int firstRow;
    private int lastRow;

    private int index;

    private String title;
    private String component;
    private String subComponent;
    private List<String> summary = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private List<String> result = new ArrayList<>();

    private String assign;

    public TestCase(){}

    public TestCase(int firstRow, int lastRow){
        this.firstRow = firstRow;
        this.lastRow = lastRow;
    }

    public int getIndex() {
        return index;
    }

    public TestCase setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getTitle() {
        return TestCaseConstants.TITLE_PREFIX + title;
    }

    public TestCase setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getSummary() {
        return summary;
    }

    public void setSummary(List<String> summary) {
        this.summary = summary;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public String getAssign() {
        return TestCaseConstants.EMAIL;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSubComponent() {
        return subComponent;
    }

    public void setSubComponent(String subComponent) {
        this.subComponent = subComponent;
    }

    public List<String> getDescriptions(){
        List<String> desc = new ArrayList<>();
        desc.add(TestCaseConstants.HEADER_SUMMARY);
        for(String line : summary){
            desc.add(line);
        }
        desc.add("");

        desc.add(TestCaseConstants.HEADER_STEPS);
        for(String line : steps){
            desc.add(line);
        }
        desc.add("");

        desc.add(TestCaseConstants.HEADER_RESULT);
        for(String line : result){
            desc.add(line);
        }
        desc.add("");
        return desc;
    }
}
