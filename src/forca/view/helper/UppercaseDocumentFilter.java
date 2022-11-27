package forca.view.helper;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class UppercaseDocumentFilter extends DocumentFilter {

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {

		// If dont have any value, run the default behavior
		if (fb.getDocument().getLength() == 0) {
			fb.replace(offset, length, text.toUpperCase(), attrs);
			return;
		}

		// With offset = 0 and length = 1 the program will replace just the first letter
		offset = 0;
		length = 1;

		int currentLenght = fb.getDocument().getLength() + text.length();

		if (text.matches("[a-zA-Z]") && currentLenght <= 2) {
			fb.replace(offset, length, text.toUpperCase(), attrs);
		}
	}
}