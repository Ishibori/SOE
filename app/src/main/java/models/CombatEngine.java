package models;

import java.util.ArrayList;

import helpers.ICombatant;
import utils.DiceUtils;
import utils.MonsterUtils;

/**
 * Created by Ishibori on 19/09/2017.
 */

public class CombatEngine {
    public ArrayList<Monster> Monsters;
    public Character User_char;
    public ArrayList<ICombatant> CombatSequence;
    public ArrayList<String> CombatLog;

    public CombatEngine(ArrayList<Monster> monsters, Character user_char){
        this.Monsters = monsters;
        this.User_char = user_char;

        if(this.User_char == null){
            throw new NullPointerException();
        }
    }

    public void initEngine(int monsterSituationBonus, int characterSituationBonus){
        this.CombatSequence = new ArrayList<>();

        if (Monsters != null) {
            for (Monster m: Monsters) {
                m.setInitiative(monsterSituationBonus);
                insertSorted(m);
            }
        }

        if (this.User_char != null) {
            this.User_char.setInitiative(characterSituationBonus);
            insertSorted(this.User_char);
        }
    }

    public void insertSorted(ICombatant c){
        if(CombatSequence == null || CombatSequence.size() == 0){
            CombatSequence = new ArrayList<>();
            CombatSequence.add(c);
        }
        else {
            boolean inserted = false;

            for (int i = 0; i < CombatSequence.size(); i++) {
                ICombatant combatant = CombatSequence.get(i);
                if (combatant.getInitiative() <= c.getInitiative()) {
                    CombatSequence.add(i, c);
                    inserted = true;
                    break;
                }
            }

            if(!inserted){
                CombatSequence.add(c);
            }
        }
    }

    public void removeCombatant(ICombatant c){
        CombatSequence.remove(c);
    }

    public void startCombat(){
        this.CombatLog = new ArrayList<>();
        boolean combatIsRunning = true;
        boolean characterWon = false;

        while (combatIsRunning) {
            for (ICombatant attacker : CombatSequence) {
                ICombatant opponent = getOpponent(attacker);

                if (opponent == null) { //check for end - no opponents left!
                    combatIsRunning = false;

                    if(attacker.isCharacter()) {
                        characterWon = true;
                    }
                    break;
                }

                PerformAttack(attacker, opponent);
            }
        }

        if(characterWon){
            User_char.Xp += collectXp(User_char);
            User_char.LevelCheck();
        }
        else {
            // Todo: handle you died!
        }
    }

    private void PerformAttack(ICombatant attacker, ICombatant defender){
        int attackValue = attacker.getAttackValue() + DiceUtils.getSingleDiceRoll(1,20);
        int defenceValue = defender.getDefenceValue() + DiceUtils.getSingleDiceRoll(1,20);

        CombatLog.add(attacker.getName() + " attacks " + defender.getName());

        if(attackValue >= defenceValue){
            int damage = attacker.getDamage();
            defender.subtractHitpoints(damage);

            CombatLog.add(attacker.getName() + " hits " + defender.getName() + " for " + damage + " hitpoints.");
            CombatLog.add(defender.getName() + " has " + defender.getCurrentHitpoints() + " hitpoints.");

            if(defender.isDead()){
                CombatLog.add(defender.getName() + " is dead!");
            }
        }
        else{
            CombatLog.add(attacker.getName() + " misses " + defender.getName());
        }
    }

    private ICombatant getOpponent(ICombatant attacker){
            for (ICombatant c: CombatSequence) {
                if(attacker.isCharacter())
                {
                    //get monsters
                    if(!c.isCharacter() && !c.isDead()){
                        return c;
                    }
                }
                else{
                    //get characters
                    if(c.isCharacter() && !c.isDead()){
                        return c;
                    }
                }
            }

        return null;
    }

    public int collectXp(Character user_char){
        int xpSum = 0;
        for (Monster m : Monsters) {
             xpSum += MonsterUtils.calculateXp(m,user_char);
        }

        return xpSum;
    }

    public void addEnemy(Monster m){
        Monsters.add(m);
    }

}
