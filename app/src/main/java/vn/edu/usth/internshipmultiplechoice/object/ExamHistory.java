package vn.edu.usth.internshipmultiplechoice.object;


import java.io.Serializable;
import java.util.List;

public class ExamHistory implements Serializable {
    private Exam exam;
    private String score;
    List<Question> questionRight;
    List<Question> questionWrong;
    public ExamHistory(Exam exam,String score,List<Question> questionRight,List<Question> questionWrong){
        this.exam = exam;
        this.score = score;
        this.questionRight = questionRight;
        this.questionWrong = questionWrong;
    }

    public Exam getExam() {
        return exam;
    }

    public List<Question> getQuestionRight() {
        return questionRight;
    }

    public List<Question> getQuestionWrong() {
        return questionWrong;
    }

    public String getScore() {
        return score;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setQuestionRight(List<Question> questionRight) {
        this.questionRight = questionRight;
    }

    public void setQuestionWrong(List<Question> questionWrong) {
        this.questionWrong = questionWrong;
    }

}
