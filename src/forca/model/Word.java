package forca.model;

import java.util.ArrayList;
import java.util.List;

public class Word {

	private final List<Character> word = new ArrayList<>();
	private final int qtdLetters;
	private final String tip;
	private int hit = 0;
	private int miss = 4;

	private List<WordObserver> observers = new ArrayList<>();

	public Word(String word, String tip) {
		word = word.toUpperCase();
		for (int i = 0; i < word.length(); i++) {
			this.word.add(word.charAt(i));
		}
		this.qtdLetters = word.length();
		this.tip = tip;
	}

	public void registerObserver(WordObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers(WordEvent event) {
		observers.forEach(o -> o.eventOcurred(this, event));
	}

	public int getQtdLetters() {
		return qtdLetters;
	}

	public String getTip() {
		return tip;
	}

	public int shot(char letter) {
		if (word.contains(letter)) {
			int index = 0;
			index = word.indexOf(letter);
			hit++;
			if (hit == qtdLetters)
				notifyObservers(WordEvent.WIN);
			else
				notifyObservers(WordEvent.HIT);

			return index;
		} else if (miss > 0) {
			notifyObservers(WordEvent.MISS);
			miss--;
		} else
			notifyObservers(WordEvent.LOSE);

		return -1;
	}

}
