package Arcanoid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BonusManager {
    private final Map<Integer, BonusEffect> activeBuffs = new HashMap<Integer, BonusEffect>();

    public void addBuff(BonusEffect buff) {

        if (this.isBuffActive(buff.getId())) {
            BonusEffect activeBuff = this.getActiveBuff(buff.getId());
                activeBuff.combineBuffs(buff);
        } else {

            this.activeBuffs.put(buff.getId(), buff);
            activeBuffs.get(buff.getId()).applyEffect(Main.MyGame);
        }
    }

    private BonusEffect getActiveBuff(int id) {
        return activeBuffs.get(id);
    }

    public void removeBuff(int buffId) {

        if (this.isBuffActive(buffId)) {

            activeBuffs.get(buffId).removeEffect(Main.MyGame);

            this.activeBuffs.remove(buffId);
        }
    }

    public void removeAll() {

        Iterator buffsIterator = this.activeBuffsIdSet().iterator();
        while (buffsIterator.hasNext()) {
            int buffId = (Integer) buffsIterator.next();
            BonusEffect buff = this.getActiveBuff(buffId);
            buff.removeEffect(Main.MyGame);
            buffsIterator.remove();
        }

        activeBuffs.clear();
    }

    public void updateBuffs() {

        if (this.haveActiveBuffs()) {

            Iterator buffsIterator = this.activeBuffsIdSet().iterator();

            while (buffsIterator.hasNext()) {

                int buffId = (Integer) buffsIterator.next();

                BonusEffect buff = this.getActiveBuff(buffId);
                Main.MyGame.bufftime.setText(this.getDurationForDisplay(buff));
                if (!buff.updateBuff()) {
                    buff.removeEffect(Main.MyGame);
                    buffsIterator.remove();
                }
            }
        } else
            Main.MyGame.bufftime.setText("-:-");
    }




    //Проверка наличия эффектов.
    public boolean haveActiveBuffs() {

        return !this.activeBuffs.isEmpty();
    }

    public boolean isBuffActive(int buffId) {

        return this.activeBuffs.containsKey(buffId);
    }
    public Set<Integer> activeBuffsIdSet() {

        return this.activeBuffs.keySet();
    }

    public String getDurationForDisplay(BonusEffect buff) {

        //Возвращает оставшееся время до истечения действия баффа в формате "мм:сс".
        //Если флаг isPersistent = true, то вернёт строку "-:-", так как бафф постоянен.

        int
                i = buff.getDuration(),
                j = i / 20,
                k = j / 60;

        j %= 60;

        return j < 10 ? String.valueOf(k) + ":0" + String.valueOf(j) : String.valueOf(k) + ":" + String.valueOf(j);
    }

}
