package google.com.fabquiz.database;
/**
 * Created by Pratik on 3/15/2018.
 */
public class Questions {
    String question;
    String optA;
    String optB;
    String optC;
    String optD;
    String correctAnswer;
    String isImageQuestion;
    public Questions() {
    }
    public Questions(String question, String optA, String optB, String optC, String optD, String correctAnswer, String isImageQuestion) {
        this.question = question;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.correctAnswer = correctAnswer;
        this.isImageQuestion = isImageQuestion;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getOptA() {
        return optA;
    }
    public void setOptA(String optA) {
        this.optA = optA;
    }
    public String getOptB() {
        return optB;
    }
    public void setOptB(String optB) {
        this.optB = optB;
    }
    public String getOptC() {
        return optC;
    }
    public void setOptC(String optC) {
        this.optC = optC;
    }
    public String getOptD() {
        return optD;
    }
    public void setOptD(String optD) {
        this.optD = optD;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public String getIsImageQuestion() {
        return isImageQuestion;
    }
    public void setIsImageQuestion(String isImageQuestion) {
        this.isImageQuestion = isImageQuestion;
    }
}