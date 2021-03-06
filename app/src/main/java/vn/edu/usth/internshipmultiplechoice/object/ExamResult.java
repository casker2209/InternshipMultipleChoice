package vn.edu.usth.internshipmultiplechoice.object;


import java.io.Serializable;
import java.util.List;

public class ExamResult implements Serializable {
    private Exam exam;
    private String score;
    List<Question> questionRight,questionWrong,questionNotChosen; 
    List<List<String>> incorrectChosen;
    public ExamResult(Exam exam, String score, List<Question> questionRight, List<Question> questionWrong, List<List<String>> IncorrectChosen, List<Question> questionNotChosen){
        this.exam = exam;
        this.score = score;
        this.questionRight = questionRight;
        this.questionWrong = questionWrong;
        this.incorrectChosen = IncorrectChosen;
        this.questionNotChosen = questionNotChosen;
    }

    public List<Question> getQuestionNotChosen() {
        return questionNotChosen;
    }

    public Exam getExam() {
        return exam;
    }

    public List<List<String>> getIncorrectChosen() {
        return incorrectChosen;
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

    public void setIncorrectChosen(List<String> incorrectChosen) {
        incorrectChosen = incorrectChosen;
    }

    public void setQuestionRight(List<Question> questionRight) {
        this.questionRight = questionRight;
    }

    public void setQuestionWrong(List<Question> questionWrong) {
        this.questionWrong = questionWrong;
    }

    public void setQuestionNotChosen(List<Question> questionNotChosen) {
        this.questionNotChosen = questionNotChosen;
    }
}
