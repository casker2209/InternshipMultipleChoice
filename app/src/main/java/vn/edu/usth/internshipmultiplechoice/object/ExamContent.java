package vn.edu.usth.internshipmultiplechoice.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamContent {
    @SerializedName("id")
    private String id;
    @SerializedName("questionList")
    private List<Question> questionList;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public String getId() {
        return id;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public void setId(String id) {
        this.id = id;
    }
    public ExamContent(String id, List<Question> questionList){
        this.id = id;
        this.questionList = questionList;
    }
}
