package forca.domain.service;

import java.util.ArrayList;
import java.util.List;

import forca.domain.model.Word;
import forca.domain.model.WordEvent;

public class ShotService {

	public void shot(Word word, char letter) {

		if (isAlreadyTried(word, letter)) {
			word.notifyObservers(WordEvent.LETTER_ALREADY_TRIED);
		} else {

			List<Integer> positions = new ArrayList<>();

			for (int i = 0; i < word.getQtdLetters(); i++) {
				if (letter == word.getContent().charAt(i))
					positions.add(i);
			}

			if (positions.size() > 0) {
				word.updateHits(positions.size());
				word.putCorrectAttempts(letter, positions);
				word.notifyObservers(WordEvent.HIT);
				if (word.getHits() == word.getQtdLetters())
					word.notifyObservers(WordEvent.WIN);

			} else if (word.getMisses() > 0) {
				word.notifyObservers(WordEvent.MISS);
				word.decrementMisses();
			} else {
				word.notifyObservers(WordEvent.LOSE);
			}
		}

	}

	private boolean isAlreadyTried(Word word, char letter) {
		if (word.contains(letter)) {
			return true;
		}
		word.getLettersAttempts().add(letter);
		return false;
	}

}
