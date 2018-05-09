package com.logic.client.bean;

import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/24
 * @desc
 */

public class GankData {

    private boolean error;
    private List<Results> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
