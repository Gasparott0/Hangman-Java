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
import javax.swing.JTextArea;
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

	private FrameHelper frameHelper = new FrameHelper();

	private Word word;
	private int errors = 0;

	// used to scroll through the word list
	private int wordIndex = 0;

	private List<Word> words;

	private Frame frame;
	private BufferedImage myPicture;
	private JLabel lblPicture;
	private JTextArea txtTip;
	private JTextField txtLetter;

	public Game(Frame frame, List<Word> words) {

		this.frame = frame;

		this.setPreferredSize(new Dimension(500, 300));
		this.setLayout(null);

		this.frameHelper.initFrame(this, 500, 300);

		JLabel lblTitle = new JLabel("Jogo da Forca");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 11, 414, 22);
		this.add(lblTitle);

		JLabel lblWord = new JLabel("Letra:");
		lblWord.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWord.setBounds(68, 60, 31, 13);
		this.add(lblWord);

		this.txtLetter = new JTextField();
		this.txtLetter.setFont(new Font("Arial", Font.PLAIN, 12));
		this.txtLetter.setBounds(109, 57, 158, 19);
		this.add(txtLetter);
		txtLetter.setColumns(10);

		JButton btnHit = new JButton("Hit");
		btnHit.addActionListener(e -> btnHitActionPerformed());
		btnHit.setFont(new Font("Arial", Font.PLAIN, 12));
		btnHit.setBounds(277, 55, 150, 23);
		this.add(btnHit);

		this.lblPicture = new JLabel();
		this.lblPicture.setBounds(277, 87, 150, 150);
		this.add(this.lblPicture);

		this.txtTip = new JTextArea();
		this.txtTip.setWrapStyleWord(true);
		this.txtTip.setLineWrap(true);
		this.txtTip.setEditable(false);
		this.txtTip.setFont(new Font("Arial", Font.PLAIN, 12));
		this.txtTip.setBounds(68, 132, 199, 105);
		this.add(this.txtTip);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(e -> this.btnBackActionPerformed());
		btnBack.setFont(new Font("Arial", Font.PLAIN, 12));
		btnBack.setBounds(10, 11, 89, 22);
		this.add(btnBack);

		this.words = words;

		this.startGame();

		this.frameHelper.configFrame(this.frame, this);

	}

	private void startGame() {
		this.addPicture(this.errors + "Errors");
		this.word = this.words.get(this.wordIndex);
		this.word.registerObserver(this);
		this.txtTip.setText("Tip: " + this.word.getTip());
	}

	private void btnHitActionPerformed() {
		String input = this.txtLetter.getText();
		if (input.matches("[A-Z]{1}")) {
			this.word.shot(input.charAt(0));
		} else {
			JOptionPane.showMessageDialog(null, "Enter a valid SINGLE UPPERCASE LETTER");
		}
	}

	private void btnBackActionPerformed() {
		new HomeScreen(this.frame);
	}

	@Override
	public void eventOcurred(Word word, WordEvent event) {
		switch (event) {
		case WIN:
			JOptionPane.showMessageDialog(null, "Congratulations, you won this round!");
			this.wordIndex++;
			this.errors = 0;
			this.startGame();
			break;
		case LOSE:
			this.addPicture("lost");
			JOptionPane.showMessageDialog(null, "Tururu, you lost!");
			this.btnBackActionPerformed();
			break;
		case HIT:
			LOGGER.log(Level.INFO, "HIT");
			break;
		case MISS:
			this.errors++;
			this.addPicture(this.errors + "Errors");
			break;
		}
	}

	private void addPicture(String file) {
		try {
			this.myPicture = ImageIO.read(new File("resources/images/" + file + ".png"));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		this.lblPicture.setIcon(new ImageIcon(this.myPicture));
	}
}
