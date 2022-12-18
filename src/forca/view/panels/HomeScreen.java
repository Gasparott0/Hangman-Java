package forca.view.panels;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import forca.domain.model.Word;
import forca.domain.service.ShotService;
import forca.view.Frame;
import forca.view.helper.FrameHelper;

@SuppressWarnings("serial")
public class HomeScreen extends JPanel {

	private static final Logger LOGGER = Logger.getLogger(HomeScreen.class.getName());

	private FrameHelper frameHelper = new FrameHelper();

	private Frame frame;
	
	private ShotService shotService;

	public HomeScreen(Frame frame) {

		this.frame = frame;

		this.frameHelper.initFrame(this, 450, 200);
		
		shotService = new ShotService();

		JLabel lblTitle = new JLabel("Hangman Game");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 24, 430, 22);
		this.add(lblTitle);

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(e -> btnPlayActionPerfomed());
		btnPlay.setFont(new Font("Arial", Font.PLAIN, 14));
		btnPlay.setBounds(10, 83, 430, 23);
		this.add(btnPlay);

		JButton btnRegisterWord = new JButton("Add a new word!");
		// TODO create the Register Word panel
		btnRegisterWord.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRegisterWord.setBounds(10, 117, 430, 23);
		this.add(btnRegisterWord);

		this.frameHelper.configFrame(this.frame, this);

	}

	private void btnPlayActionPerfomed() {

		List<Word> words = this.getWords();

		if (words.isEmpty())
			JOptionPane.showMessageDialog(null, "You have no registered words");
		else
			new Game(this.frame, this.getWords(), shotService);

	}

	private List<Word> getWords() {

		List<Word> words = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File("resources/words.txt"), StandardCharsets.UTF_8);) {

			while (scanner.hasNextLine()) {
				String linha = scanner.nextLine();

				Scanner linhaScanner = new Scanner(linha);
				linhaScanner.useLocale(Locale.US);
				linhaScanner.useDelimiter(";");

				String tipInFile = linhaScanner.next();
				String wordInFile = linhaScanner.next();

				linhaScanner.close();

				words.add(new Word(wordInFile, tipInFile));

			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} catch (IOException e1) {
			LOGGER.log(Level.SEVERE, e1.getMessage());
		}

		return words;

	}

}
