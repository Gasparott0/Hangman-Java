package forca.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {

	private String content;
	private String tip;
	private int qtdLetters;
	private int hits = 0;
	private int misses = 4;
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

	public void notifyObservers(WordEvent event) {
		observers.forEach(o -> o.eventOcurred(event));
	}

	public String getContent() {
		return content;
	}

	public String getTip() {
		return tip;
	}

	public int getQtdLetters() {
		return qtdLetters;
	}

	public int getHits() {
		return hits;
	}

	public void updateHits(int hits) {
		this.hits += hits;
	}

	public int getMisses() {
		return misses;
	}

	public void decrementMisses() {
		this.misses--;
	}

	public List<Character> getLettersAttempts() {
		return lettersAttempts;
	}

	public Map<Character, List<Integer>> getCorrectAttempts() {
		return correctAttempts;
	}

	public void addToCorrectAttempts(char letter) {
		lettersAttempts.add(letter);
	}

	public boolean contains(char letter) {
		return lettersAttempts.contains(letter);
	}

	public void putCorrectAttempts(char letter, List<Integer> positions) {
		correctAttempts.put(letter, positions);
	}

	public List<WordObserver> getObservers() {
		return observers;
	}

}
