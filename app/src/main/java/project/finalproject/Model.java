package project.finalproject;

public class Model {
    private double Accuracy;
    private double Latency;

    public Model(){}

    public Model(double accuracy, double latency) {
        Accuracy = accuracy;
        Latency = latency;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(double accuracy) {
        Accuracy = accuracy;
    }

    public double getLatency() {
        return Latency;
    }

    public void setLatency(double latency) {
        Latency = latency;
    }
}
