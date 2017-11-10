
public class Statistic {
    private double[] data;
    private int size;   

    public Statistic(double[] data) {
        this.data = data;
        size = data.length;
    }   

    public double getMean() {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/size;
    }

    public double getVariance() {
        double mean = getMean();
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/(size-1);
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }
}