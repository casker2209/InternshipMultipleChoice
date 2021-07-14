package vn.edu.usth.internshipmultiplechoice.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bank implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("questionList")
    private List<Question> questionList;

    public void setName(String name) {
        name = name;
    }

    public void setId(String id) {
        id = id;
    }

    public void setDescription(String description) {
        description = description;
    }

    public void setQuestionList(List<Question> question) {
        this.questionList= question;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public String getId() {
        return id;
    }
    public Bank(String id,String name,String description,List<Question> questionList){
        this.id = id;
        this.description = description;
        this.name = name;
        this.questionList = questionList;
    }
}
