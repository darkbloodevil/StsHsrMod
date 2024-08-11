package HsrMod.interfaces;

import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * @author darkbloodevil
 * @date 2024/8/9 17:54
 * @description
 */
public interface AtDepletingToughness {
    public void at_depleting_toughness(AbstractCreature target,int toughness_reduction);

}
