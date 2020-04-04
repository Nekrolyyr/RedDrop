package ch.versusvirus.reddrop.logic.model;

public class Questions {

    public String mQuestions[] = {
            "Are you over 50 kg?",
            "Have you had dental hygiene or dental treatment in the past 72 hours?",
            "Do you currently feel healthy and have no signs of a cold or fever?",
            "Have you taken medication, also without prescription, (e.g. tablets, syringes, suppositories), medical intervention or vaccination in the past 4 weeks?",
            "Have you been outside Switzerland in the past six months?",
            "Have you had sexual contacts with changing partners in the past 12 months or with a new partner in the past 4 months?",
            "Have you had a new piercing / tattoo or acupuncture treatment in the past four months?"
    };

    private String mChoice[][] = {
            {"Yes","No"},
            {"Yes","No"},
            {"Yes","No"},
            {"Yes","No"},
            {"Yes","No"},
            {"Yes","No"},
            {"Yes","No"}
    };

    private String mCorrectAnswer[] = {
            "Yes","No","Yes","No","No","No","No"
    };

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    };

    public String getChoice1(int a){
        String choice = mChoice[a][0];
        return choice;
    };

    public String getChoice2(int a){
        String choice = mChoice[a][1];
        return choice;
    };

    public String getCorretAnswer(int a){
        String answer = mCorrectAnswer[a];
        return answer;
    };

}
