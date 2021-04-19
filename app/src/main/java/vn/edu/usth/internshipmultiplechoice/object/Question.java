package vn.edu.usth.internshipmultiplechoice.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Question implements Serializable {
    @SerializedName("question")
    private String name;
    @SerializedName("a")
    private String A;
    @SerializedName("b")
    private String B;
    @SerializedName("c")
    private String C;
    @SerializedName("d")
    private String D;
    @SerializedName("questionCorrect")
    private String questionCorrect;

    private boolean hasE;
    @SerializedName("e")
    private String E;
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

    public String getQuestionCorrect() {
        return questionCorrect;
    }

    public String getE() {
        return E;
    }

    public boolean isHasE() {
        return hasE;
    }

    public void setE(String e) {
        E = e;
    }

    public Question(String name, String A, String B, String C, String D, String questionCorrect){
        this.name = name;
        this.questionCorrect = questionCorrect;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.hasE = false;
    }
    public Question(String name,String A,String B,String C,String D,String E,String questionCorrect){
        this.name = name;
        this.questionCorrect = questionCorrect;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
        this.hasE = true;
    }

}
