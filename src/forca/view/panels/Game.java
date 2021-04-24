package forca.view.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import forca.model.Word;
import forca.model.WordEvent;
import forca.model.WordObserver;
import forca.view.Frame;
import forca.view.helper.FrameHelper;

@SuppressWarnings("serial")
public class Game extends JPanel implements WordObserver {

	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

	// used to scroll through the word list
	private int wordIndex = 0;

	// word being used in the game
	private Word currentWord;

	private List<Word> words;
	private int errors = 0;
	private FrameHelper frameHelper = new FrameHelper();

	private Frame frame;
	private BufferedImage myPicture;
	private JTextField txtLetter;
	private JLabel lblPicture;
	private JLabel lblTip;
	private JLabel lblCurrentWord;

	public Game(Frame frame, List<Word> words) {

		this.frame = frame;
		this.words = words;

		setPreferredSize(new Dimension(500, 300));
		setLayout(null);

		frameHelper.initFrame(this, 500, 300);

		JLabel lblTitle = new JLabel("Jogo da Forca");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 414, 22);
		add(lblTitle);

		JLabel lblWord = new JLabel("Letra:");
		lblWord.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWord.setBounds(65, 232, 31, 13);
		add(lblWord);

		txtLetter = new JTextField();
		txtLetter.setFont(new Font("Arial", Font.PLAIN, 12));
		txtLetter.setBounds(106, 229, 158, 19);
		add(txtLetter);
		txtLetter.setColumns(10);

		JButton btnHit = new JButton("Hit");
		btnHit.addActionListener(e -> btnHitActionPerformed());
		btnHit.setFont(new Font("Arial", Font.PLAIN, 12));
		btnHit.setBounds(274, 227, 150, 23);
		add(btnHit);

		lblPicture = new JLabel();
		lblPicture.setBounds(65, 67, 150, 150);
		add(lblPicture);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(e -> backToHomeScreen());
		btnBack.setFont(new Font("Arial", Font.PLAIN, 12));
		btnBack.setBounds(10, 11, 89, 22);
		add(btnBack);

		lblCurrentWord = new JLabel("_ _ _ _ _ _ _ _ _ _");
		lblCurrentWord.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCurrentWord.setFont(new Font("Arial", Font.PLAIN, 24));
		lblCurrentWord.setBounds(225, 174, 199, 43);
		add(lblCurrentWord);

		lblTip = new JLabel();
		lblTip.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTip.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTip.setBounds(225, 67, 199, 97);
		add(lblTip);

		frameHelper.configFrame(this.frame, this);

		addPicture(errors + "Errors");
		startGame();

	}

	@Override
	public void eventOcurred(WordEvent event) {
		switch (event) {
		case WIN:
			JOptionPane.showMessageDialog(null, "Congratulations, you won this round!");
			wordIndex++;
			errors = 0;
			startGame();
			break;
		case LOSE:
			addPicture("lost");
			JOptionPane.showMessageDialog(null, "Tururu, you lost!");
			backToHomeScreen();
			break;
		case HIT:
			LOGGER.log(Level.INFO, "HIT");
			break;
		case MISS:
			errors++;
			addPicture(errors + "Errors");
			break;
		}
	}

	private void startGame() {
		currentWord = words.get(wordIndex);
		currentWord.registerObserver(this);

		StringBuilder tipLabel = new StringBuilder();
		tipLabel.append("<html>");
		tipLabel.append("<body>");
		tipLabel.append("<p style=\"text-align: justify\">");
		tipLabel.append(currentWord.getTip());
		tipLabel.append("</p>");
		tipLabel.append("</body>");
		tipLabel.append("</html>");

		lblTip.setText(tipLabel.toString());
		
		StringBuilder wordLabel = new StringBuilder();
		
		for(int i = 0; i < currentWord.getQtdLetters(); i++) {
			if(i == (currentWord.getQtdLetters() - 1))
				wordLabel.append("_");
			else
				wordLabel.append("_ ");
		}
		
		lblCurrentWord.setText(wordLabel.toString());
	}

	private void btnHitActionPerformed() {
		String input = txtLetter.getText();
		if (input.matches("[A-Z]{1}")) {
			currentWord.shot(input.charAt(0));
		} else {
			JOptionPane.showMessageDialog(null, "Enter a valid SINGLE UPPERCASE LETTER");
		}
	}

	private void backToHomeScreen() {
		new HomeScreen(frame);
	}

	private void addPicture(String file) {
		try {
			myPicture = ImageIO.read(new File("resources/images/" + file + ".png"));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		lblPicture.setIcon(new ImageIcon(myPicture));
	}
}
