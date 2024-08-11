package HsrMod.core;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

/**
 * @author darkbloodevil
 * @date 2024/7/25 11:38
 * @description 加入了削韧量和元素类型
 */
public class HsrDamageInfo extends DamageInfo {
    // 默认4削韧
    public int toughness_reduction = 4;
    public boolean is_follow_up = false;
    public AbstractCreature damageSource;
    public int base;
    public DamageType type;

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type) {
        super(damageSource, base, type);
        this.damageSource=damageSource;
        this.base=base;
        this.type=type;
    }

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type, int toughness_reduction) {
        super(damageSource, base, type);
        this.damageSource=damageSource;
        this.base=base;
        this.type=type;
        this.toughness_reduction = toughness_reduction;
    }

    public HsrDamageInfo(AbstractCreature damageSource, int base, DamageType type, int toughness_reduction, boolean is_follow_up) {
        super(damageSource, base, type);
        this.damageSource=damageSource;
        this.base=base;
        this.type=type;
        this.toughness_reduction = toughness_reduction;
        this.is_follow_up = is_follow_up;
    }
    public HsrDamageInfo clone(){
        return new HsrDamageInfo(damageSource,base,type,toughness_reduction,is_follow_up);
    }
}
