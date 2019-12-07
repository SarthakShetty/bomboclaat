package project.finalproject;

public class AuthenticateResponse {
    private NB nb;
    private KNN knn;
    private SVM svm;

    public AuthenticateResponse() {
    }

    public NB getNb() {
        return nb;
    }

    public void setNb(NB nb) {
        this.nb = nb;
    }

    public KNN getKnn() {
        return knn;
    }

    public void setKnn(KNN knn) {
        this.knn = knn;
    }

    public SVM getSvm() {
        return svm;
    }

    public void setSvm(SVM svm) {
        this.svm = svm;
    }
}
