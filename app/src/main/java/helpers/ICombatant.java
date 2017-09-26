package helpers;

import java.util.UUID;

/**
 * Created by Ishibori on 19/09/2017.
 */

public interface ICombatant {
    UUID getIdentifier();
    String getName();
    int getInitiative();
    boolean isCharacter();
    boolean isDead();
    int getAttackValue();
    int getDefenceValue();
    int getCurrentHitpoints();
    int getDamage();
    void subtractHitpoints(int hitpoints);
}
