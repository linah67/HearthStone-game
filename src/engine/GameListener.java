package engine;

import javax.sound.sampled.LineUnavailableException;

import model.cards.minions.Minion;

public interface GameListener {
	public void onGameOver();
	public void OnPlayMinion(Minion m);
	public void onAttack();
	public void OnUseHeroPower();
	public void OnCastSpell();
	
}
