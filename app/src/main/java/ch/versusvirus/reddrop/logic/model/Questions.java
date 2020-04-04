package ch.versusvirus.reddrop.logic.model;

public class Questions {

    public String mQuestions[] = {
            "Sind Sie über 50 kg schwer?",
            "Hatten Sie in den letzten 72 Stunden eine dentalhygienische oder zahnärztliche Behandlung?",
            "Fühlen Sie sich zurzeit gesund und haben Sie keine Anzeichen einer Erkältung oder Fieber?",
            "Haben Sie in den letzten 4 Wochen Medikamente – auch rezeptfreie – eingenommen (z.B Tabletten, Spritzen, Zäpfchen), einen medizinischen Eingriff oder eine Impfung vorgenommen?",
            "Waren Sie in den letzten sechs Monaten ausserhalb der Schweiz?",
            "Hatten Sie sexuelle Kontakte mit wechselnden Partnerinnen oder Partnern in den vergangenen 12 Monaten oder mit einer neuen Partnerin/einem neuen Partner in den letzten 4 Monaten?",
            "Hatten Sie in den letzten vier Monaten ein neues Piercing/ eine neue Tätowierung oder eine Akupunktur-Behandlung?"
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
