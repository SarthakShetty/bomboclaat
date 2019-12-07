package project.finalproject;

public class Model {
    private double accuracy;
    private double latency;

    public Model(){}

    public Model(double accuracy, double latency) {
        this.accuracy = accuracy;
        this.latency = latency;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getLatency() {
        return latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }
}
