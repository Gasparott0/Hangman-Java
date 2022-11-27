package forca.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {

	private String content;
	private String tip;
	private int qtdLetters;
	private int hit = 0;
	private int miss = 4;
	private List<Character> lettersAttempts = new ArrayList<>();
	private Map<Character, List<Integer>> correctAttempts = new HashMap<>();

	private List<WordObserver> observers = new ArrayList<>();

	public Word(String word, String tip) {
		this.content = word.toUpperCase();
		this.tip = tip;
		this.qtdLetters = word.length();
	}

	public void registerObserver(WordObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers(WordEvent event) {
		observers.forEach(o -> o.eventOcurred(event));
	}

	public String getTip() {
		return tip;
	}

	public int getQtdLetters() {
		return qtdLetters;
	}

	public Map<Character, List<Integer>> getLastTry() {
		return correctAttempts;
	}

	public void shot(char letter) {

		if (isAlreadyTried(letter)) {
			notifyObservers(WordEvent.LETTER_ALREADY_TRIED);
		} else {

			List<Integer> positions = new ArrayList<>();

			for (int i = 0; i < qtdLetters; i++) {
				if (letter == content.charAt(i))
					positions.add(i);
			}

			if (positions.size() > 0) {
				hit += positions.size();
				correctAttempts.put(letter, positions);
				notifyObservers(WordEvent.HIT);
				if (hit == qtdLetters)
					notifyObservers(WordEvent.WIN);

			} else if (miss > 0) {
				notifyObservers(WordEvent.MISS);
				miss--;
			} else {
				notifyObservers(WordEvent.LOSE);
			}
		}

	}

	private boolean isAlreadyTried(char letter) {
		if (lettersAttempts.contains(letter)) {
			return true;
		}
		lettersAttempts.add(letter);
		return false;
	}

}
