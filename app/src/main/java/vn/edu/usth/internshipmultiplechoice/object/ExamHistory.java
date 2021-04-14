package vn.edu.usth.internshipmultiplechoice.object;


import java.io.Serializable;
import java.util.List;

public class ExamHistory implements Serializable {
    private Exam exam;
    private String score;
    List<Question> questionRight;
    List<Question> questionWrong;
    public ExamHistory(Exam exam,String score){

    }
}
