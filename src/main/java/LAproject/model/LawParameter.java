package LAproject.model;

public class LawParameter<T> {
    private String lawName;
    private long lawID;
    private T lawParameter;

    public LawParameter(String lawName, long lawID, T lawParameter) {
        this.lawName = lawName;
        this.lawID = lawID;
        this.lawParameter = lawParameter;
    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public long getLawID() {
        return lawID;
    }

    public void setLawID(long lawID) {
        this.lawID = lawID;
    }

    public T getLawParameter() {
        return lawParameter;
    }

    public void setLawParameter(T lawParameter) {
        this.lawParameter = lawParameter;
    }
}
