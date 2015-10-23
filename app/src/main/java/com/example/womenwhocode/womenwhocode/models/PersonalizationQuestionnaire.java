package com.example.womenwhocode.womenwhocode.models;

import java.util.HashMap;

/**
 * Created by zassmin on 10/22/15.
 */
public class PersonalizationQuestionnaire {
    public HashMap<String, String[]> questionnaire;

    public PersonalizationQuestionnaire() {
        this.questionnaire = new HashMap<>();
    }

    public HashMap<String, String[]> getQuestionnaire() {
        return this.questionnaire;
    }

    public HashMap<String, String[]> build() {
        String question1 = "engineering area of interest";
        String[] answersForQuestion1 = new String[]{"architecture", "security", "data science",
                "analytics", "open source", "devops", "web", "mobile", "enterprise"};
        questionnaire.put(question1, answersForQuestion1);
        String question2 = "What do you hope to get out of WWCode?";
        String[] answersForQuestion2 = new String[]{"enhancing your technical skills",
                "leadership skills", "career development (e.g. negotiation, interviewing)", "professional network",
                "enhancing your technical profile (e.g. speaking opportunities, technical blogging)"};
        questionnaire.put(question2, answersForQuestion2);
        String question3 = "What's your current technical skill-set level?";
        String[] answersForQuestion3 = new String[]{"singularity (10+ years)", "advanced (5+ years)",
                "intermediate (2-5 years)", "beginner (2 year)", "newbie (1 year)"};
        questionnaire.put(question3, answersForQuestion3);
        return questionnaire;
    }
}
