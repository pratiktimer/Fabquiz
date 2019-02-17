package google.com.fabquiz.database;


public class Question {
    public int id;
    public String QUESTION;
    public String ANSWER;
    public String OptionA;
    public String OptionB;
    public String OptionC;
    public String OptionD;


    public static final String KEY_ID = "id";
    public static final String KEY_QUESION = "question";
    public static final String KEY_ANSWER = "answer"; //correct option
    public static final String KEY_OPTA = "opta"; //option a
    public static final String KEY_OPTB = "optb"; //option b
    public static final String KEY_OPTC = "optc"; //option c
    public static final String KEY_OPTD = "optd"; //option d


    public Question(String quesTion, String ansWer, String opA, String opB, String opC, String opD) {

        QUESTION = quesTion;
        OptionA = opA;
        OptionB = opB;
        OptionC = opC;
        OptionD = opD;
        ANSWER = ansWer;
    }

    public Question(){

        id=0;
        QUESTION="";
        ANSWER="";
        OptionA="";
        OptionB="";
        OptionC="";
        OptionD="";
    }
    public Question(DBAdapter dbAdapter){
        int id;
        String QUESTION;
        String ANSWER;
        String OptionA;
        String OptionB;
        String OptionC;
        String OptionD;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public String getANSWER() {
        return ANSWER;
    }

    public void setANSWER(String ANSWER) {
        this.ANSWER = ANSWER;
    }

    public String getOptionA() {
        return OptionA;
    }

    public void setOptionA(String optionA) {
        OptionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getOptionD() {
        return OptionD;
    }

    public void setOptionD(String optionD) {
        OptionD = optionD;
    }

}
