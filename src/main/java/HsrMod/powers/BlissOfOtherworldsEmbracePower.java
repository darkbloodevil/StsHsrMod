package HsrMod.powers;

import HsrMod.cards.attacks.DecadenceFalseTwilight;
import HsrMod.cards.skills.LoomOfFatesCaprice;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static HsrMod.HsrMod.makeID;

/**
 * @author darkbloodevil
 * @date 2024/8/25 9:37
 * @description
 */
public class BlissOfOtherworldsEmbracePower  extends BasePower {
    public static final String POWER_ID = makeID(BlissOfOtherworldsEmbracePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    boolean upgraded;
    public BlissOfOtherworldsEmbracePower(AbstractCreature owner, AbstractCreature source, int amount,boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.amount=amount;
        this.upgraded=upgraded;
    }


    @Override
    public void updateDescription() {
        String target="Loom Of Fate's Caprice";
        if (this.upgraded){
            target="Loom Of Fate's Caprice+";
        }
        this.description = String.format(DESCRIPTIONS[0],target);
    }

    @Override
    public void atStartOfTurn() {
        this.amount--;
        LoomOfFatesCaprice lfc=new LoomOfFatesCaprice();
        if (upgraded){
            lfc.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(lfc));

        if (this.amount <= 0) {

            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}