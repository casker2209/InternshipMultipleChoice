package vn.edu.usth.internshipmultiplechoice.object;

public class Question {
    private String name;
    private String A;
    private String B;
    private String C;
    private String D;
    private String questionCorrect;

    public String getName() {
        return name;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }
    public Question(String name,String A,String B,String C,String D,String questionCorrect){
        this.name = name;
        this.questionCorrect = questionCorrect;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
    }
}
