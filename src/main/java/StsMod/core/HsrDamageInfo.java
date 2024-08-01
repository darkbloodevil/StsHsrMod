package StsMod.core;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * @author darkbloodevil
 * @date 2024/7/25 11:38
 * @description 加入了削韧量和元素类型
 */
public class HsrDamageInfo extends DamageInfo {
    public int toughness_reduction = 1;
    public boolean is_follow_up = false;

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type) {
        super(damageSource, base, type);
    }

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type, int toughness_reduction) {
        super(damageSource, base, type);
        this.toughness_reduction = toughness_reduction;
    }

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type, int toughness_reduction, boolean is_follow_up) {
        super(damageSource, base, type);
        this.toughness_reduction = toughness_reduction;
        this.is_follow_up = true;
    }
}
