package HsrMod.variables;

import HsrMod.interfaces.DurationInterface;
import HsrMod.interfaces.MultiDamageInterface;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

/**
 * @author darkbloodevil
 * @date 2024/8/8 22:40
 * @description
 */
public class MultiDamageVar1 extends DynamicVariable {
    @Override
    public String key() {
        return "D1";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
        // Set to true if the value is modified from the base value.
    }


    @Override
    public int value(AbstractCard card) {
        if (card instanceof MultiDamageInterface) {
            int[] multi_damage = ((MultiDamageInterface) card).get_multi_damage();
            if (multi_damage.length > 0)
                return multi_damage[0];
        }
        return 0;
        // What the dynamic variable will be set to on your card. Usually uses some kind of int you store on your card.
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return value(abstractCard);
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return abstractCard.upgraded;
    }

}