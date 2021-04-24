package forca.model;

import java.util.ArrayList;
import java.util.List;

public class Word {

	private String content;
	private int qtdLetters;
	private String tip;
	private int hit = 0;
	private int miss = 4;

	private List<WordObserver> observers = new ArrayList<>();

	public Word(String word, String tip) {
		this.content = word.toUpperCase();
		this.qtdLetters = word.length();
		this.tip = tip;
	}

	public void registerObserver(WordObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers(WordEvent event) {
		observers.forEach(o -> o.eventOcurred(event));
	}

	public int getQtdLetters() {
		return qtdLetters;
	}

	public String getTip() {
		return tip;
	}

	public int shot(char letter) {

		int index = content.indexOf(letter);

		if (index != -1) {
			hit++;
			if (hit == qtdLetters)
				notifyObservers(WordEvent.WIN);
			else
				notifyObservers(WordEvent.HIT);

		} else if (miss > 0) {
			notifyObservers(WordEvent.MISS);
			miss--;
		} else {
			notifyObservers(WordEvent.LOSE);
		}

		return index;
	}

}
