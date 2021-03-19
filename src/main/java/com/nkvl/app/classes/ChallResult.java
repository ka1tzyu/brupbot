package com.nkvl.app.classes;

import java.util.ArrayList;
import java.util.List;

public final class ChallResult {
    private final long start;
    private long end;
    private int expPos;
    private final List<Integer> resultsList;

    public ChallResult(long start) {
        this.start = start;
        this.end = 0;
        this.expPos = 0;
        this.resultsList = new ArrayList<>();
    }

    public int getExpPos() { return expPos; }

    public void setEnd(long end) {
        this.end = end;
    }

    public int geTotalTime() {
        return ((int) ((end - start) / 1000000000));
    }

    public void newResult(int n) {
        this.expPos++;
        this.resultsList.add(n);
    }

    public List<Integer> getResultsList() { return resultsList; }
}
