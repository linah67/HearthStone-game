package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.cards.Card;

public class exceptionWindow extends JFrame {
	String s;
	JButton cd;
	
	Card b;
	JLabel m=new JLabel();
	public exceptionWindow(String l) {
		s=l;
		setBounds(600,450,700,200);
		setLayout(new BorderLayout());
		m.setSize(this.getWidth(), this.getHeight());
		
		m.setVisible(true);
		m.setText(s);
		m.setFont(new Font ("TimesNewRoman", Font.BOLD,30));
		
		add(m);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		repaint();
}
	public exceptionWindow(String l,Card n) {
		cd = new JButton();
		cd.setSize(30,60);
		cd.setText(n.toString());
		s=l;
		b=n;
		setBounds(600,400,700,300);
		m.setFont(new Font ("TimesNewRoman", Font.BOLD,30));
		setLayout(new BorderLayout());
		m.setSize(this.getWidth(), this.getHeight());
		
		m.setText(s);
		cd.setBorder(null);
		cd.setOpaque(false);
		add(cd,BorderLayout.CENTER);
		add(m,BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		revalidate();
		repaint();
} 
	//public static void main(String [] args) {
		//Card s = new Icehowl();
		//exceptionWindow bn =new exceptionWindow("You cannot attack in your opponent's turn!",s);
	//}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
		m.setText(s);
	}
	public void setM(JLabel m) {
		this.m = m;
	}
	public JButton getCd() {
		return cd;
	}
	public void setCd(JButton cd) {
		this.cd = cd;
	}
	
		
	
	
	
}
