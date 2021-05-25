package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.Card;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
public class GameController implements ActionListener, GameListener {
	Game model;
	GameView view;
	exceptionWindow ew;
	boolean flag;
	Minion hp;
	Hero hp2;
	boolean a;
	int counter;
	JButton ok=new JButton();
	Hero first;
	Hero second;
	boolean flagSpell;
	Hero target;
	String fi;
	String se;
	Minion minion1;
	Minion minion2;
	Card spellc;
	JButton hd= new JButton();
	JButton hd2= new JButton();
	exceptionWindow bn;
	JButton ok2;
	ArrayList<JButton> Handc = new ArrayList<JButton>();
	ArrayList<JButton> HandO = new ArrayList<JButton>();
	ArrayList<JButton> Fieldc = new ArrayList<JButton>();
	ArrayList<JButton> FieldO = new ArrayList<JButton>();
	AudioInputStream audioInputStream;


	public GameController() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		view = new GameView();
		view.getB().addActionListener(this);
		flag = true;
		minion1 = null;
		minion2 = null;
		
		hp = null;
		hp2 = null;
		fi = "";
		se = "";
		view.getEnd().addActionListener(this);
		view.getHeropower().addActionListener(this);
		view.getFi().addActionListener(this);
		view.getSe().addActionListener(this);
		a = true;
		view.getHeropower2().addActionListener(this);
		counter=0;
		spellc=null;
		flagSpell=false;
		ok.setText("OK");
		ok.addActionListener(this);
		ok.setActionCommand("ok");
		ok.setForeground(Color.BLACK);
		ok.setFont(new Font ("TimesNewRoman",Font.BOLD,30));
		ok.setVisible(true);
		ok.setBounds(400,340,80,50);
		ew= new exceptionWindow("odwouwh");
		ew.setVisible(false);
		ew.add(ok,BorderLayout.SOUTH);
		ok2=new JButton();
		ok2.setText("OK");
		ok2.addActionListener(this);
		ok2.setActionCommand("ok2");
		ok2.setForeground(Color.BLACK);
		ok2.setFont(new Font ("TimesNewRoman",Font.BOLD,30));
		ok2.setVisible(true);
		ok2.setBounds(400,340,80,50);
		
		Card s = new Icehowl();
		bn =new exceptionWindow("You cannot attack in your opponent's turn!",s);
		bn.add(ok2,BorderLayout.SOUTH);
		audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/music.wav").getAbsoluteFile());
		Clip clip =AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public String getFi() {
		return fi;
	}

	public String getSe() {
		return se;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/card.wav").getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(e.getActionCommand()=="ok2") {
			bn.dispose();
		}
		
		if(e.getActionCommand()=="ok") {
			ew.dispose();
		}
		if (e.getActionCommand()=="cast2") {
			if (a) {
			try {
				throw new NotYourTurnException("You can not do any action in your opponent's turn");
			} catch (NotYourTurnException e1) {
				exwindow(e1.getMessage());
			}
			}else {
				exwindow("You can not attack a minion that has not been summoned yet");
			}
		}
		if(e.getActionCommand()=="cast") {
			a=true;
			int x= Handc.indexOf(e.getSource());
			spellc=model.getCurrentHero().getHand().get(x);
			if((spellc instanceof FieldSpell)) {
				try {
					model.getCurrentHero().castSpell((FieldSpell)spellc);OnCastSpell();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					exwindow(e1.getMessage());

				}
			}
			if(spellc instanceof AOESpell) {
				try {
					model.getCurrentHero().castSpell((AOESpell)spellc,model.getOpponent().getField());OnCastSpell();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					exwindow(e1.getMessage());

				}
			}
			if(spellc instanceof HeroTargetSpell||spellc instanceof MinionTargetSpell||spellc instanceof LeechingSpell) {
				flagSpell=true;
				if (spellc instanceof HeroTargetSpell) {
				exwindow("please choose a Hero Target!");
				}else {
					exwindow("please choose a Minion Target!");

				}

			}
		
				
			
		}
		if((e.getActionCommand()=="attack")&&(flagSpell)) {	
			int index;
			flagSpell=false;
			Minion m=null;
			if(FieldO.contains(e.getSource())) {
				index=FieldO.indexOf(e.getSource());
				m=model.getOpponent().getField().get(index);
			}
			if(Fieldc.contains(e.getSource())) {
				index=Fieldc.indexOf(e.getSource());
				m=model.getCurrentHero().getField().get(index);
			}
			if(spellc instanceof LeechingSpell ) {
			try {
				model.getCurrentHero().castSpell((LeechingSpell)spellc, m);OnCastSpell();
			
			} catch (NotYourTurnException | NotEnoughManaException e1) {
				
				exwindow(e1.getMessage());
			}
			}
			if (spellc instanceof MinionTargetSpell) {
				try {
					model.getCurrentHero().castSpell((MinionTargetSpell)spellc,m);OnCastSpell();
					
				} catch (NotYourTurnException | NotEnoughManaException | InvalidTargetException e1) {
					exwindow(e1.getMessage());
					}
			}
			return;
		}
		if((e.getActionCommand()=="hero"||e.getActionCommand()=="hero2")&&(flagSpell)) {
			Hero s=null;
			flagSpell=false;
			if(e.getActionCommand()=="hero") {
				try {
					model.getCurrentHero().castSpell((HeroTargetSpell)spellc, model.getCurrentHero());OnCastSpell();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					exwindow(e1.getMessage());

				}
			}else {
				try {
					model.getCurrentHero().castSpell((HeroTargetSpell)spellc, model.getOpponent());OnCastSpell();
				} catch (NotYourTurnException | NotEnoughManaException e1) {
					exwindow(e1.getMessage());
				}
			}
			return;
		}
		if (e.getActionCommand().equals("play")&&a) {
			JButton min = (JButton) e.getSource();
			if (Handc.contains(min)) {
				int x = Handc.indexOf(min);
				Minion nef = (Minion) model.getCurrentHero().getHand().get(x);
				try {
					model.getCurrentHero().playMinion(nef);
				} catch (NotYourTurnException e1) {
					exwindow(e1.getMessage());
				} catch (NotEnoughManaException e1) {
					exwindow(e1.getMessage());

				} catch (FullFieldException e1) {
					exwindow(e1.getMessage());

				}
			} else {
				
				int x = HandO.indexOf(min);
				Minion nef = (Minion) model.getOpponent().getHand().get(x);
				try {
					model.getOpponent().playMinion(nef);
				} catch (NotYourTurnException e1) {
					exwindow(e1.getMessage());
				} catch (NotEnoughManaException e1) {
					exwindow(e1.getMessage());
				} catch (FullFieldException e1) {
					exwindow(e1.getMessage());
				}
				

			}

		}
		if (e.getActionCommand().equals("attack") && a&&counter==0) {
			
			minion1 = null;
			JButton minion = (JButton) e.getSource();
			if (Fieldc.size() != 0 && Fieldc.contains(minion)) {

				minion1 = (Minion) model.getCurrentHero().getField().get(Fieldc.indexOf(minion));
				
			} else if (FieldO.size() != 0 && FieldO.contains(minion)) {

				minion1 = (Minion) model.getOpponent().getField().get(FieldO.indexOf(minion));
			}
			
			a = false;
			exwindow("please choose a Target!");

		} else if ((e.getActionCommand().equals("attack")||e.getActionCommand().equals("play")) &&( !a&&counter==0&&minion1!=null&&!flagSpell)){
			
			minion2 = null;
			JButton minion = (JButton) e.getSource();
			if (Fieldc.size() != 0 && Fieldc.contains(minion)) {
				minion2 = (Minion) model.getCurrentHero().getField().get(Fieldc.indexOf(minion));

			} else if (FieldO.size() != 0 && FieldO.contains(minion)) {

				minion2 = (Minion) model.getOpponent().getField().get(FieldO.indexOf(minion));
			}
		

			try {
				if (model.getCurrentHero().getField().contains(minion1)) {
					model.getCurrentHero().attackWithMinion(minion1, minion2);
				} else {
					model.getOpponent().attackWithMinion(minion1, minion2);
				}
				onAttack();
			} catch (CannotAttackException | NotYourTurnException | TauntBypassException | InvalidTargetException
					| NotSummonedException e1) {
				a=true;
				if(e1 instanceof NotSummonedException && Handc.contains(minion)) {
					exwindow("You are trying to attack your own hand");
				}else {
					exwindow(e1.getMessage());
				}
			}
		}

		if ((e.getActionCommand().equals("hero")|| e.getActionCommand().equals("hero2"))&& minion1 != null&&!a) {
			if (model.getCurrentHero().getField().contains(minion1)){
				if (e.getActionCommand().equals("hero")){
			
					try {
						model.getCurrentHero().attackWithMinion(minion1, model.getCurrentHero());onAttack();
					
					} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
						| InvalidTargetException e1) {
						a=true;
						exwindow(e1.getMessage());
					}
				}else {
					try {
						model.getCurrentHero().attackWithMinion(minion1, model.getOpponent());onAttack();
					} catch (CannotAttackException | NotYourTurnException | TauntBypassException | NotSummonedException
							| InvalidTargetException e1) {
						a=true;
						exwindow(e1.getMessage());
					}

				}
			} else {
				exwindow("You can not do any action in your opponent's turn");
					a=true;
				
			}
		
		
		}

		if (e.getActionCommand().equals("s")) {

			try {
				ListenerHeroes(view.onChoose());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			if (e.getActionCommand().equals("END")) {

				try {
					model.endTurn();
					Onswitchturn();
				} catch (FullHandException e1) {
					Onswitchturn();
					ex2window(e1.getMessage(),e1.getBurned());
				} catch (CloneNotSupportedException e1) {
					exwindow(e1.getMessage());
				}
			}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (e.getActionCommand().equals("MAGE") || e.getActionCommand().equals("PALADIN")
				|| e.getActionCommand().equals("HUNTER") || e.getActionCommand().equals("PRIEST")
				|| e.getActionCommand().equals("WARLOCK")) {
			try {
				ChooseHeroes(e);
			} catch (IOException e1) {
				exwindow(e1.getMessage());
			} catch (CloneNotSupportedException e1) {
				exwindow(e1.getMessage());
			} catch (FullHandException e1) {
				ex2window(e1.getMessage(),e1.getBurned());
			}

		}
		if (e.getActionCommand() == "use hero power2") {
			try {
				throw new NotYourTurnException("You can not do any action in your opponent's turn");
			} catch (NotYourTurnException e1) {
				exwindow(e1.getMessage());
			}
		}
		if (e.getActionCommand() == "use hero power" && counter>=0&&a) {
			String n = model.getCurrentHero().getName();
			if(counter==0) {
			switch (n) {
			case ("Jaina Proudmoore"):
			case ("Anduin Wrynn"):
				exwindow("please choose a Target");counter++;
				
				break;
			case ("Gul'dan"):
			case ("Uther Lightbringer"):
			case ("Rexxar"):
				try {
					model.getCurrentHero().useHeroPower();OnUseHeroPower();counter++;
					
					
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
						| FullHandException | FullFieldException | CloneNotSupportedException e1) {
					if(e1 instanceof  FullHandException) {
						exwindow(e1.getMessage());
					}else {
					counter=0;
					exwindow(e1.getMessage());

				}
				}
				
				break;
			}
			}else {
				try {
					model.validateUsingHeroPower(model.getCurrentHero());
					
				} catch (NotEnoughManaException | HeroPowerAlreadyUsedException  e1) {
					counter=0;
					exwindow(e1.getMessage());
				}
			}
			
			
		}
		if ((e.getActionCommand() == "attack" || e.getActionCommand() == "hero" || e.getActionCommand() == "hero2")&&(counter!=0&&!flagSpell&&a))
				 
		{
			if ((e.getActionCommand() == "attack")) {
				if (Fieldc.size() != 0 && Fieldc.contains((JButton) e.getSource())) {
					hp = (Minion) model.getCurrentHero().getField().get(Fieldc.indexOf((JButton) e.getSource()));
				} else if (FieldO.size() != 0 && FieldO.contains((JButton) e.getSource())) {
					hp = (Minion) model.getOpponent().getField().get(FieldO.indexOf((JButton) e.getSource()));
				}
				if (model.getCurrentHero() instanceof Mage) {
					try {
						((Mage) model.getCurrentHero()).useHeroPower(hp);
						OnUseHeroPower();
						
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						if(e1 instanceof  FullHandException) {
							exwindow(e1.getMessage());

						}else {
							exwindow(e1.getMessage());
						}
						}
				} else {
					try {
						((Priest) model.getCurrentHero()).useHeroPower(hp);
						OnUseHeroPower();
						
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						if(e1 instanceof  FullHandException) {
							exwindow(e1.getMessage());

						}else {
						exwindow(e1.getMessage());

					}
				}
				}
				
			} else {

				if (e.getActionCommand() == "hero") {
					hp2 = model.getCurrentHero();
				} else {
					hp2 = model.getOpponent();
				}
				if (model.getCurrentHero() instanceof Mage) {
					try {
						((Mage) model.getCurrentHero()).useHeroPower(hp2);
						OnUseHeroPower();
						

					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						if(e1 instanceof  FullHandException) {
							ex2window(e1.getMessage(),((FullHandException) e1).getBurned());

						}else {
						exwindow(e1.getMessage());

					}
					}
				} else {
					try {
						((Priest) model.getCurrentHero()).useHeroPower(hp2);
						OnUseHeroPower();
						
					} catch (NotEnoughManaException | HeroPowerAlreadyUsedException | NotYourTurnException
							| FullHandException | FullFieldException | CloneNotSupportedException e1) {
						if(e1 instanceof  FullHandException) {
							ex2window(e1.getMessage(),((FullHandException) e1).getBurned());

						}else {
						exwindow(e1.getMessage());

					}
					}
				}
				
			}}
			


	}

	public void ListenerHeroes(ArrayList<JButton> a) {
		view.remove(view.getB());
		view.remove(view.getL());
		

		for (int i = 0; i < 5; i++) {
			JButton b2 = new JButton();
			b2.addActionListener(this);
			b2.setVisible(true);
			b2.setText(a.get(i).getText());
			b2.setFont(new Font("Memphis",Font.BOLD,40));
			b2.setBackground(Color.darkGray);
			b2.setForeground(Color.white);
			view.getHeroes2().add(b2);
		}
		view.add(view.getHeroes2());
		view.add(view.getL());
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/choose1.png").getImage().getScaledInstance(view.getWidth(),view.getHeight(), Image.SCALE_DEFAULT));
		view.getL().setIcon(imageIcon);
		view.revalidate();
		view.repaint();

	}

	public void ChooseHeroes(ActionEvent e) throws IOException, CloneNotSupportedException, FullHandException {
		Hero count = null;
		switch (e.getActionCommand()) {
		case ("MAGE"):
			count = new Mage();
			break;
		case ("PRIEST"):
			count = new Priest();
			break;
		case ("WARLOCK"):
			count = new Warlock();
			break;
		case ("PALADIN"):
			count = new Paladin();
			break;
		case ("HUNTER"):
			count = new Hunter();
			break;
		}
		if (flag) {
			first = count;
			ImageIcon imageIcon = new ImageIcon(new ImageIcon("src/choose2.png").getImage().getScaledInstance(view.getWidth(),view.getHeight(), Image.SCALE_DEFAULT));
			view.getL().setIcon(imageIcon);
			fi = e.getActionCommand();
			flag = false;
		} else {
			second = count;
			se = e.getActionCommand();
		}
		if (second != null) {
			view.versus(fi, se);
			model = new Game(first, second);
			model.setListener(this);
			try {
				onGameStart();
			} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public void onGameStart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		view.remove(view.getHeroes());
		view.revalidate();
		view.repaint();
		view.Gamestart();
		onHandUpdate();
		view.getFi().addActionListener(this);
		view.getSe().addActionListener(this);
		hd.setBounds(view.getWidth()-300,view.getHeight()-150,240,240);
		hd2.setBounds(view.getWidth()-300,view.getHeight(),240,240);
		hd.setText(model.getCurrentHero().toString());
		hd.setIcon(new ImageIcon("src/background details.png"));
		hd.setText(model.getCurrentHero().toString());
		hd.setIconTextGap(-465);
		hd.setForeground(Color.BLACK);
		hd.setText(model.getCurrentHero().toString());
		hd.setFont(new Font("Memphis",Font.BOLD,20));
		hd2.setIcon(new ImageIcon("src/background details.png"));
		hd2.setText(model.getCurrentHero().toString());
		hd2.setForeground(Color.BLACK);
		hd2.setFont(new Font("Memphis",Font.BOLD,20));
		hd2.setIconTextGap(-465);
		view.revalidate();
		view.repaint();
		view.getEnd().setFocusable(false);
	
		HeroUpdate();
		view.getEnd().setBounds(view.getWidth()-445,view.getHeight()-620,150,100);
		view.getEnd().setContentAreaFilled(false);
		view.getEnd().setBorder(null);
		view.getEnd().setBorderPainted(false);
		view.getEnd().setOpaque(false);
		view.getEnd().setBorder(null);
		view.getEnd().setBorderPainted(false);
		view.getEnd().setContentAreaFilled(false);
		 Image image = ImageIO.read(new File("src/End Turn.png")).getScaledInstance(130, 90, Image.SCALE_DEFAULT);
		 view.getEnd().setIcon(new ImageIcon(image));
		 Image image2 = ImageIO.read(new File("src/End Turn 3.png")).getScaledInstance(130, 90, Image.SCALE_DEFAULT);
		 view.getEnd().setPressedIcon(new ImageIcon(image2));
		view.getEnd().setRolloverEnabled(true);
		Image image3 = ImageIO.read(new File("src/End Turn 2.png")).getScaledInstance(130, 90, Image.SCALE_DEFAULT);
	    view.getEnd().setRolloverIcon(new ImageIcon(image3));
		view.getHeropower().setBounds(view.getWidth()-450,view.getHeight()-500,150,170);
		view.getHeropower2().setBounds(view.getWidth()-450,view.getHeight()-810,150,170);
		view.getHeropower().setOpaque(false);
		view.getHeropower().setBorder(null);
		view.getHeropower2().setOpaque(false);
		view.getHeropower2().setBorder(null);
		view.getHeropower().setContentAreaFilled(false);
		view.getHeropower2().setContentAreaFilled(false);
		buttonImage ();
		if (model.getCurrentHero().getName().equals(first.getName())) {
			view.add(view.getFi());
			view.getFi().setBounds(view.getWidth()-300, view.getHeight()-300, 300, 240);
			view.getSe().setBounds(view.getWidth()-300,40, 300, 240);
			view.getFi().setActionCommand("hero");
			view.add(view.getHeropower2());
			view.add(view.getEnd());
			view.add(view.getHeropower());
			view.add(view.getSe());
			view.add(hd);
			view.add(hd2);
			view.getSe().setActionCommand("hero2");

		} else {
			view.add(view.getSe());
			view.getSe().setActionCommand("hero");
			view.getFi().setBounds(view.getWidth()-300, 40, 300, 240);
			view.getSe().setBounds(view.getWidth()-300, view.getHeight()-300, 300, 240);
			view.add(view.getHeropower2());
			view.add(view.getEnd());
			view.add(view.getHeropower());
			view.add(view.getFi());
			view.getFi().setActionCommand("hero2");

		}
		
		view.getCurrH().setOpaque(false);
		view.getCurrHF().setOpaque(false);
		view.getOppHF().setOpaque(false);
		
		view.add(view.getL());

	}

	public void onFieldUpdate() {
		ArrayList<Minion> cr = model.getCurrentHero().getField();
		ArrayList<Minion> op = model.getOpponent().getField();
		if (view.getCurrHF().getComponentCount() > 0) {
			for (int i = 0; i < view.getCurrHF().getComponentCount(); i++) {
				view.getCurrHF().remove(view.getCurrHF().getComponent(i));
				i--;
			}

		}
		if (view.getOppHF().getComponentCount() > 0) {
			for (int i = 0; i < view.getOppHF().getComponentCount(); i++) {
				view.getOppHF().remove(view.getOppHF().getComponent(i));
				i--;
			}

		}
		if (!Fieldc.isEmpty()) {
			Fieldc.clear();
		}
		if (!FieldO.isEmpty()) {
			FieldO.clear();
		}
		for (int i = 0; i < cr.size(); i++) {

			JButton r = new JButton();
			r.setActionCommand("attack");
			r.addActionListener(this);
			String y = cr.get(i).toString();
			y += "Sleeping:"+cr.get(i).isSleeping();
			r.setText(y);
			r.setVisible(true);
			r.setBorderPainted(false);
			r.setOpaque(false);
			view.getCurrHF().add(r);
			Fieldc.add(r);

		}
		for (int i = 0; i < op.size(); i++) {
			JButton r = new JButton();
			r.addActionListener(this);
			r.setText(op.get(i).toString());
			r.setActionCommand("attack");
			r.setVisible(true);
			r.setBorderPainted(false);
			r.setOpaque(false);
			view.getOppHF().add(r);
			FieldO.add(r);

		}
		HeroUpdate();
		view.revalidate();
		view.repaint();
	}

	public void onHandUpdate() {
		ArrayList<Card> ln = model.getCurrentHero().getHand();
		ArrayList<Card> ln2 = model.getOpponent().getHand();
		int x = view.getCurrH().getComponentCount();
		if (x != 0) {
			for (int i = 0; i < x; i++) {
				view.getCurrH().remove(i);
				x--;
				i--;
			}
		}
		int y = view.getOppH().getComponentCount();
		if (y != 0) {
			for (int i = 0; i < y; i++) {
				view.getOppH().remove(i);
				y--;
				i--;
			}

		}
		if (!Handc.isEmpty()) {
			Handc.clear();
		}
		if (!HandO.isEmpty()) {
			HandO.clear();
		}
		for (int i = 0; i < ln.size(); i++) {
			Card b = ln.get(i);
			JButton r = new JButton();
			r.addActionListener(this);
			
			if (ln.get(i) instanceof Minion) {
				r.setActionCommand("play");
			} else {
				r.setActionCommand("cast");
			}
			r.setText(b.toString());
			r.setVisible(true);
			view.getCurrH().add(r);
			Handc.add(r);
		}
		for (int i = 0; i < ln2.size(); i++) {
			Card b = ln2.get(i);
			JButton r = new JButton();
			r.addActionListener(this);
			if (ln2.get(i) instanceof Minion) {
				r.setActionCommand("play");
			} else {
				r.setActionCommand("cast2");
			}
			
			r.setVisible(true);
			try{
			    Image image = ImageIO.read(new File("src/cardback.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
			    r.setIcon(new ImageIcon(image));
			} 
			catch (Exception e) {
			}
			r.setContentAreaFilled(false);
			r.setOpaque(false);
			r.setBorder(null);
			HandO.add(r);
			view.getOppH().add(r);
		}
		HeroUpdate();
		view.revalidate();
		view.repaint();
	}

	public void onAttack() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/Attack.wav").getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		onFieldUpdate();
		HeroUpdate();
		a=true;
		view.revalidate();
		view.repaint();

	}

	public void Onswitchturn() {
		view.remove(view.getFi());
		view.remove(view.getSe());
		if (model.getCurrentHero().getName().equals(first.getName())) {
			view.getFi().setBounds(view.getWidth()-300, view.getHeight()-300, 300, 240);
			view.getSe().setBounds(view.getWidth()-300, 40, 300, 240);
			view.getFi().setActionCommand("hero");
			view.getSe().setActionCommand("hero2");
			view.add(view.getFi());
			view.add(view.getSe());
			view.add(view.getL());
		} else {
			view.getFi().setBounds(view.getWidth()-300, 40, 300, 240);
			view.getSe().setBounds(view.getWidth()-300, view.getHeight()-300,300, 240);
			view.getSe().setActionCommand("hero");
			view.getFi().setActionCommand("hero2");
			view.add(view.getSe());
			view.add(view.getFi());
			view.add(view.getL());


		}
		onHandUpdate();
		onFieldUpdate();
		view.revalidate();
		view.repaint();
		counter=0;
		flagSpell=false;
		a=true;
		HeroUpdate();
		try {
			buttonImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void OnPlayMinion(Minion m) {

		onFieldUpdate();
		onHandUpdate();
		HeroUpdate();
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		GameController g = new GameController();
	}

	@Override
	public void onGameOver()  {
		JButton m=new JButton();
		if(model.getFirstHero()==model.getCurrentHero()) {
			view.setVisible(false);
			view.getFi().setSize(400,600);
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(view.getPic1()).getImage().getScaledInstance(500,450, Image.SCALE_DEFAULT));
			view.getFi().setIcon(imageIcon);
			  try {
				GameOver g=new GameOver(view.getFi(),"first");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			view.setVisible(false);
			view.getSe().setSize(400,600);
			ImageIcon imageIcon = new ImageIcon(new ImageIcon(view.getPic2()).getImage().getScaledInstance(500,450, Image.SCALE_DEFAULT));
			view.getSe().setIcon(imageIcon);
			try {
				GameOver g=new GameOver(view.getSe(),"Second");
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

	public void OnUseHeroPower() {
		onFieldUpdate();
		counter=0;
		HeroUpdate();

	}

	@Override
	public void OnCastSpell() {

		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/spell.wav").getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Clip clip;
		try {
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		onFieldUpdate();
		onHandUpdate();
		HeroUpdate();
	}
	public void HeroUpdate() {
		hd.setBounds(view.getWidth()-300,view.getHeight()-580,300,240);
		hd2.setBounds(view.getWidth()-300,view.getHeight()-800,300,240);
		hd.setText(model.getCurrentHero().toString());
		hd2.setText(model.getOpponent().toString());
		hd.setForeground(Color.BLACK);
		view.remove(view.getL());
		view.add(hd);
		view.add(hd2);
		view.add(view.getL());
	}
	public void exwindow(String l) {
		ew.setS(l);
		ew.setVisible(true);
	}
	public void ex2window(String m,Card b) {
		bn.getCd().setText(b.toString());
		bn.setS(m);
		bn.setVisible(true);
		
	}
	public void buttonImage () throws IOException {
		String n =model.getCurrentHero().getName();
		switch(n){
		case ("Jaina Proudmoore"):
			  Image image = ImageIO.read(new File("src/FIREBLAST.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower().setIcon(new ImageIcon(image));
			break;
		case ("Anduin Wrynn"):
			  Image image2 = ImageIO.read(new File("src/heal.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    	view.getHeropower().setIcon(new ImageIcon(image2));
			break;
		case ("Gul'dan"):
			  Image image3 = ImageIO.read(new File("src/LIFE TAP.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower().setIcon(new ImageIcon(image3));
			break;
		case ("Uther Lightbringer"):
			  Image image4 = ImageIO.read(new File("src/SILVER HAND RECRUIT.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower().setIcon(new ImageIcon(image4));
			break;
		case ("Rexxar"):
			  Image image5 = ImageIO.read(new File("src/STEADY SHOT.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower().setIcon(new ImageIcon(image5));
			break;
		}
		String m =model.getOpponent().getName();
		switch(m){
		case ("Jaina Proudmoore"):
			  Image image = ImageIO.read(new File("src/FIREBLAST.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower2().setIcon(new ImageIcon(image));
			break;
		case ("Anduin Wrynn"):
			  Image image2 = ImageIO.read(new File("src/heal.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    	view.getHeropower2().setIcon(new ImageIcon(image2));
			break;
		case ("Gul'dan"):
			  Image image3 = ImageIO.read(new File("src/LIFE TAP.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower2().setIcon(new ImageIcon(image3));
			break;
		case ("Uther Lightbringer"):
			  Image image4 = ImageIO.read(new File("src/SILVER HAND RECRUIT.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower2().setIcon(new ImageIcon(image4));
			break;
		case ("Rexxar"):
			  Image image5 = ImageIO.read(new File("src/STEADY SHOT.png")).getScaledInstance(150, 180, Image.SCALE_DEFAULT);
	    view.getHeropower2().setIcon(new ImageIcon(image5));
			break;
		}
		}
	
	
	
}
