package google.com.fabquiz.database;

/**
 * Created by user on 02-02-2018.
 */

public class Question2 {
    public String Question2,OptA,OptB,OptC,OptD,CorrectAnswer,IsImageQuestion;

    public Question2() {
    }

    public Question2(String question2, String optA, String optB, String optC, String optD, String correctAnswer, String isImageQuestion) {
        Question2 = question2;
        OptA = optA;
        OptB = optB;
        OptC = optC;
        OptD = optD;
        CorrectAnswer = correctAnswer;
        IsImageQuestion = isImageQuestion;
    }

    public String getQuestion2() {
        return Question2;
    }

    public void setQuestion2(String question2) {
        Question2 = question2;
    }

    public String getOptA() {
        return OptA;
    }

    public void setOptA(String optA) {
        OptA = optA;
    }

    public String getOptB() {
        return OptB;
    }

    public void setOptB(String optB) {
        OptB = optB;
    }

    public String getOptC() {
        return OptC;
    }

    public void setOptC(String optC) {
        OptC = optC;
    }

    public String getOptD() {
        return OptD;
    }

    public void setOptD(String optD) {
        OptD = optD;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        IsImageQuestion = isImageQuestion;
    }
}
