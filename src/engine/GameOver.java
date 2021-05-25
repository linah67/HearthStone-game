package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import model.heroes.Hero;
public class GameOver extends JFrame {
	Hero winner;
	JLabel l =new JLabel();
	JButton n =new JButton();
	ImageIcon imageIcon;
	
	public GameOver(JButton b,String s ) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/gameover.wav").getAbsoluteFile());
		Clip clip =AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setBounds(0,0,screenSize.width, screenSize.height);
	    setVisible(true);
		l.setSize(this.getWidth(), this.getHeight());
		l.setVisible(true);
		setLayout(null);
		n=b;
		n.setBounds(720,420,500,450);
		n.setFocusable(false);
		n.setOpaque(false);
		n.setContentAreaFilled(false);
		n.setBorder(null);
		n.setVisible(true);
		if(s.equals("first")) {
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/gameover.jpg").getImage().getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_DEFAULT));
		l.setIcon(imageIcon);
		l.setVisible(true);
		}else {
			ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/gameover2.png").getImage().getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_DEFAULT));
			l.setIcon(imageIcon);
			l.setVisible(true);
		}
		add(n,BorderLayout.CENTER);
		setVisible(true);
		add(l);
		revalidate();
		repaint();
		
	}
	//public static void main(String[]args) {
		//JButton B=new JButton();
		//B.setSize(500,250);
		//ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/PALADIN.png").getImage().getScaledInstance(500,450, Image.SCALE_DEFAULT));
		//B.setIcon(imageIcon);
		//B.setVisible(false);
		//try {
			//GameOver gm=new GameOver(B,"areej");
		//} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	//}

}
