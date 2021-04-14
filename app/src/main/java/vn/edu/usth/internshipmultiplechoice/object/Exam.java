package vn.edu.usth.internshipmultiplechoice.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Exam implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("questionList")
    private List<Question> QuestionList;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestionList() {
        return QuestionList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestionList(List<Question> questionList) {
        QuestionList = questionList;
    }
    public Exam(String id,String name,String description,List<Question> questionList){
        this.id = id;
        this.name = name;
        this.description = description;
        this.QuestionList = questionList;
    }
}
