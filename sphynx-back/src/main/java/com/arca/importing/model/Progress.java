package com.arca.importing.model;

public class Progress {
    private final long total;
    private final long value;

    public Progress(long total, long value) {
        this.total = total;
        this.value = value;
    }

    public long getTotal() {
        return total;
    }

    public long getValue() {
        return value;
    }
}
