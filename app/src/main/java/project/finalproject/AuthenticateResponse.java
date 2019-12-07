package project.finalproject;

public class AuthenticateResponse {
    private NB NB;
    private KNN KNN;
    private SVM SVM;

    public AuthenticateResponse() {
    }

    public NB getNB() {
        return NB;
    }

    public void setNB(NB NB) {
        this.NB = NB;
    }

    public KNN getKNN() {
        return KNN;
    }

    public void setKNN(KNN KNN) {
        this.KNN = KNN;
    }

    public SVM getSVM() {
        return SVM;
    }

    public void setSVM(SVM SVM) {
        this.SVM = SVM;
    }
}
