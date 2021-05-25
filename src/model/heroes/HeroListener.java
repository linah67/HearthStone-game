package model.heroes;

import javax.sound.sampled.LineUnavailableException;

import exceptions.FullHandException;
import model.cards.minions.Minion;

public interface HeroListener {
public void onHeroDeath();
public void damageOpponent(int amount);
public void endTurn() throws FullHandException, CloneNotSupportedException;
public void onAttack();
public void OnPlayMinion(Minion m);
public void OnUseHeroPower();
public void OnCastSpell();



}
