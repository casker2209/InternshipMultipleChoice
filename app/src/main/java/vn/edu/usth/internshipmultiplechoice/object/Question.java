package vn.edu.usth.internshipmultiplechoice.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    @SerializedName("question")
    private String name;
    @SerializedName("answer")
    private List<String> answer;

    @SerializedName("questionCorrect")
    private List<String> questionCorrect;

    public String getName() {
        return name;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public List<String> getQuestionCorrect() {
        return questionCorrect;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public void setQuestionCorrect(List<String> questionCorrect) {
        this.questionCorrect = questionCorrect;
    }

    public Question(String name, List<String> answer, List<String> questionCorrect){
        this.name = name;
        this.questionCorrect = questionCorrect;
        this.answer = answer;
    }
}
