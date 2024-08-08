package HsrMod.cards.attacks;

import HsrMod.cards.BaseCard;
import HsrMod.interfaces.ToughnessReductionInterface;
import HsrMod.util.CardStats;

/**
 * @author darkbloodevil
 * @date 2024/8/8 15:27
 * @description 主要用于实现韧性条
 */
public abstract class BaseAttack extends BaseCard implements ToughnessReductionInterface {
    public int toughness_reduction = 4;

    public BaseAttack(String ID, CardStats info) {
        super(ID, info);
    }

    @Override
    public int get_toughness_reduction() {
        return toughness_reduction;
    }
}
