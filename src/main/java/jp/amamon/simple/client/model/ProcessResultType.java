package jp.amamon.simple.client.model;

public enum ProcessResultType {

    SUCCESS(0),
    ERROR(1),;

    final private int value;

    ProcessResultType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
