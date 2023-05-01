package kodlamaio.northwind.enums;

public enum Status {
    DONE("done"), WAITING("waiting"), IN_PROGRESS("in_progress"), CANCELLED("cancelled");

    private final String index;

    Status(String i) {
        this.index = i;
    }
    public String getStatus(){
        return this.index;
    }
}
