package org.knoesis.health.dataHolders;

import java.net.URI;
import java.util.ArrayList;

public class QuestionDataHolder {
	private String question;
	private int answerIndex;
	private URI qualityType;
	private String[] answers;
	private URI[] qualities;
	private boolean answered;
	
	public QuestionDataHolder(String question, String[] answers, URI qualityType, URI[] qualities) {
		this.question = question;
		this.answers = answers;
		this.qualities = qualities;
		this.qualityType = qualityType;
		this.answered = false;
	}
	
	public QuestionDataHolder(String question, ArrayList<String> answers, URI qualityType, ArrayList<URI> qualities) {
		int len = answers.size();
		this.answers = new String[len];
		for(int i = 0; i < len; i++) {
			this.answers[i] = answers.get(i);
		}
		len = qualities.size();
		this.qualities = new URI[len];
		for(int i = 0; i < len; i++) {
			this.qualities[i] = qualities.get(i);
		}
		this.question = question;
		this.qualityType = qualityType;
		this.answered = false;
	}
	
	public int getIndexOfAnswer(String answer) {
		int len = answers.length;
		int index = -1;
		for(int i = 0; i < len; i++) {
			if(answers[i].equalsIgnoreCase(answer))
				index = i;
		}
		return index;
	}
	
	public URI[] getQualities() {
		return qualities;
	}

	public String[] getAnswers() {
		return answers;
	}
	
	public void answer(int answerIndex) {
		this.answered = true;
		this.answerIndex = answerIndex;
	}
	
	public boolean isAnswered() {
		return answered;
	}
	
	public void changeAnswer() {
		this.answered = false;
		this.answerIndex = -1;
	}
	
	public String getAnswer() {
		if(answered == true)
			return answers[answerIndex];
		else
			return null;
	}
	
	public URI getQualityType() {
		return qualityType;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	

	
}
