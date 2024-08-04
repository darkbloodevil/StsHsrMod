package HsrMod.variables;

import HsrMod.interfaces.ToughnessReductionInterface;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

/**
 * @author darkbloodevil
 * @date 2024/8/2 11:39
 * @description
 * from https://github.com/daviscook477/BaseMod/wiki/Dynamic-Variables#dynamic-variables
 */
public class ReductionVar extends DynamicVariable {
    @Override
    public String key() {
        return "reduction";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
        // Set to true if the value is modified from the base value.
    }


    @Override
    public int value(AbstractCard card) {
        if (card instanceof ToughnessReductionInterface) {
            return ((ToughnessReductionInterface) card).get_toughness_reduction();
        }
        return 0;
        // What the dynamic variable will be set to on your card. Usually uses some kind of int you store on your card.
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        if (abstractCard instanceof ToughnessReductionInterface) {
            return ((ToughnessReductionInterface) abstractCard).get_toughness_reduction();
        }
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard abstractCard) {
        return abstractCard.upgraded;
    }


}