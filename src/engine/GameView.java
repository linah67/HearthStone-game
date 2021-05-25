package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;

public class GameView extends JFrame {
	private JLabel l;
	private JButton b;
	private JButton heropower2;
	private String pic1;
	private String pic2;
	
	public String getPic1() {
		return pic1;
	}

	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}

	public String getPic2() {
		return pic2;
	}

	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}

	public JButton getHeropower2() {
		return heropower2;
	}

	public void setHeropower2(JButton heropower2) {
		this.heropower2 = heropower2;
	}
	private JPanel heroes;
	private JPanel heroes2;
	private JPanel CurrH;
	//private JPanel info;
	private JButton end;
	private JButton heropower;
	public JButton getHeropower() {
		return heropower;
	}

	public void setHeropower(JButton heropower) {
		this.heropower = heropower;
	}

	public JButton getEnd() {
		return end;
	}

	//public JPanel getInfo() {
		//return info;
	//}

	//public void setInfo(JPanel info) {
		//this.info = info;
	//}
	private JPanel OppH;
	private JPanel CurrHF;
	private JPanel OppHF;
	private JTextArea h2name;
	private JTextArea h1name;
	private JButton fi;
	 private JButton se;
	
	public JTextArea getH2name() {
		return h2name;
	}

	public void setH2name(JTextArea h2name) {
		this.h2name = h2name;
	}

	public JTextArea getH1name() {
		return h1name;
	}

	public void setH1name(JTextArea h1name) {
		this.h1name = h1name;
	}

	public JPanel getCurrH() {
		return CurrH;
	}

	public void setCurrH(JPanel currH) {
		CurrH = currH;
	}

	

	public JButton getFi() {
		return fi;
	}

	public void setFi(JButton fi) {
		this.fi = fi;
	}

	public JButton getSe() {
		return se;
	}

	public void setSe(JButton se) {
		this.se = se;
	}

	public JPanel getOppH() {
		return OppH;
	}

	public void setOppH(JPanel oppH) {
		OppH = oppH;
	}

	public JPanel getCurrHF() {
		return CurrHF;
	}

	public void setCurrHF(JPanel currHF) {
		CurrHF = currHF;
	}

	public JPanel getOppHF() {
		return OppHF;
	}

	public void setOppHF(JPanel oppHF) {
		OppHF = oppHF;
	}
	private JTextArea t;
	public JTextArea getT() {
		return t;
	}

	public void setT(JTextArea t) {
		this.t = t;
	}

	public JPanel getHeroes2() {
		return heroes2;
	}

	public void setHeroes2(JPanel heroes2) {
		this.heroes2 = heroes2;
	}
	ArrayList<JButton> choose;
	public GameView()
	{	pic1="";
		pic2="";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 setBounds(0,0,screenSize.width, screenSize.height);
		setTitle("HEARTH STONE");
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		choose = new ArrayList<JButton>();
		l= new JLabel();
		heroes= new JPanel(); heroes2= new JPanel();
		l.setBounds(0, 0, getWidth(), getHeight());
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/im.jpg").getImage().getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_DEFAULT));
		l.setIcon(imageIcon);
		t=new JTextArea();t.setEditable(false);
		b= new JButton();
		b.setActionCommand("s");
		b.setBounds(600, 600,700,600);
		b.setIcon(new ImageIcon("src/play.png"));
		b.setContentAreaFilled(false);
		b.setBorder(null);
		b.setBorderPainted(false);
		b.setOpaque(false);
		b.setPressedIcon(new ImageIcon("src/play2.png"));
		b.setRolloverEnabled(true);
		b.setRolloverIcon(new ImageIcon("src/play3.png"));
		b.setFocusable(false);
		CurrH=new JPanel();
		OppH=new JPanel();
		CurrHF=new JPanel();
		OppHF=new JPanel();
		 fi= new JButton();
		 se= new JButton();
		 heropower2=new JButton();
		// info=new JPanel();
		 end = new JButton();
		 heropower=new JButton();
		 end.setBackground(Color.RED);
		 heropower.setBackground(Color.CYAN);
		 heropower.setText("USE HERO POWER");
		 heropower.setActionCommand("use hero power");
		 heropower2.setBackground(Color.DARK_GRAY);
		 heropower2.setText("USE HERO POWER");
		 heropower2.setActionCommand("use hero power2");
		add(b);
		add(t);
		add(l);
		 h1name= new JTextArea();  h2name= new JTextArea();
		 
		
		
		revalidate();repaint();
		
	}

	public ArrayList<JButton> getChoose() {
		return choose;
	}
	public void setChoose(ArrayList<JButton> choose) {
		this.choose = choose;
	}
	public ArrayList<JButton> onChoose() throws IOException
	{  
		ArrayList<String> names = new ArrayList<String>();
		names.add("MAGE");
		names.add("PRIEST");
		names.add("WARLOCK");
		names.add("PALADIN");
		names.add("HUNTER");
		heroes.setLayout(new GridLayout(1,5,8,0));
		heroes.setBounds(0, 300, getWidth(), 400);heroes.setVisible(true);
		heroes2.setLayout(new GridLayout(1,5,8,0));
		heroes2.setBounds(0, 700, getWidth(), 90);heroes2.setVisible(true);
		heroes.setOpaque(false);
		heroes2.setOpaque(false);
		for(int i=0;i<5;i++)
		{
			JButton b= new JButton();
			b.setVisible(true);
			b.setSize(200, 300);
			b.setActionCommand(names.get(i));
			b.setText(names.get(i));
			Image image = ImageIO.read(new File("src/"  +names.get(i) + ".png")).getScaledInstance(300, 350, Image.SCALE_DEFAULT);
		    b.setIcon(new ImageIcon(image));
			b.setOpaque(false);
			b.setBorder(null);
			b.setContentAreaFilled(false);
			choose.add(b);
			heroes.add(b);
			}
		add(heroes);
		
		revalidate();repaint();
		return choose;
		
	}
	
	public void versus(String f,String s)
	{  
	   JButton vs = new JButton();
		vs.setSize(200, 300);
		vs.setIcon(new ImageIcon("src/vs.jpg"));
		vs.setVisible(true);

		fi = new JButton();
		fi.setVisible(true);
		fi.setSize(200, 500);
		fi.setText(f);
		fi.setContentAreaFilled(false);
		fi.setBorder(null);
		pic1="src/" + f + ".png";
		try{
		    Image image = ImageIO.read(new File(pic1)).getScaledInstance(250, 250, Image.SCALE_DEFAULT);
		    fi.setIcon(new ImageIcon(image));
		} 
		catch (Exception e) {
		}
		
		
		se=new JButton();
		se.setVisible(true);
		se.setSize(200, 600);
		se.setText(s);
		se.setContentAreaFilled(false);
		se.setBorder(null);
		pic2="src/" + s + ".png";
		try{
		    Image image = ImageIO.read(new File(pic2)).getScaledInstance(250, 250, Image.SCALE_DEFAULT);
		    se.setIcon(new ImageIcon(image));
		} 
		catch (Exception e) {
		}
		//String s3 = "src/" + s + ".png";
		//se.setIcon(new ImageIcon(s3));
		//se.setContentAreaFilled(false);
	  // JTextArea h1name= new JTextArea(); JTextArea h2name= new JTextArea();
	   //h1name.setVisible(true);h2name.setVisible(true);
	   //int i=0;
	   //while(i<2)   
		//{ switch(i==0?f:s)
		  //{case ("MAGE"): (i==0?h1name:h2name).setText(" MAGE/n Jaina Proudmoore");break;
		  //case ("PRIEST"): (i==0?h1name:h2name).setText(" PRIEST/n Anduin Wrynn ");break;
		  //case ("WARLOCK"): (i==0?h1name:h2name).setText(" WARLOCK/n Gul'dan");break;
		  //case ("PALADIN"): (i==0?h1name:h2name).setText(" PALADIN/n Uther Lightbringer");break;
		  //case ("HUNTER"): (i==0?h1name:h2name).setText("HUNTER/n Rexxar");break;}
		//i++;
	   
	   
	  t.setVisible(false);
	  heroes2.setVisible(false);
	  heroes.removeAll(); 
	  heroes.add(fi);heroes.add(vs);heroes.add(se);
	  heroes.setBounds(150,200,900,250);
	  //h1name.setBounds(100, 100, 200, 300);
	  //h2name.setBounds(620,100,200,300);
	  
	  //h1name.setEditable(false);
	  //h2name.setEditable(false);
	  //add(h1name);add(h2name);
	  
	  revalidate();repaint();
	}
	public void Gamestart() {
		CurrH.setBounds(75,getHeight()-300,1500,200);
		OppH.setBounds(75,65,1500,200);
		OppHF.setBounds(400,360,1100,180);
		CurrHF.setBounds(400,540,1100,180);
		CurrH.setVisible(true);
		CurrH.setBackground(Color.BLACK);
		CurrH.setLayout(new GridLayout(0,10,10,0));
		OppH.setLayout(new GridLayout(0,10,10,0));
		OppH.setOpaque(false);
		
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/maingame.jpg").getImage().getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_DEFAULT));
		l.setIcon(imageIcon);
		remove(l);
		end.setText("END TURN");
		end.setVisible(true);
		end.setActionCommand("END");
		add(CurrH);
		add(OppH);
		OppH.setVisible(true);
		CurrHF.setVisible(true);
		OppHF.setVisible(true);
		add(CurrHF);
		add(OppHF);
		revalidate();
		repaint();
	}
	 
	
	public JPanel getHeroes() {
		return heroes;
	}

	public void setHeroes(JPanel heroes) {
		this.heroes = heroes;
	}

	public JLabel getL() {
		return l;
	}
	public void setL(JLabel l) {
		this.l = l;
	}
	public JButton getB() {
		return b;
	}
	public void setB(JButton b) {
		this.b = b;
	}
	
	
	

}
