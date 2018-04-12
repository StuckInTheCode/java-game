package Arcanoid;

import java.util.*;

public class BonusManager {
    private final Map<Integer, BonusEffect> activeBuffs = new HashMap<Integer, BonusEffect>();

    public void addBuff(BonusEffect buff) {

        //Проверка наличия баффа с идентичным идентификатором.
        if (this.isBuffActive(buff.getId())) {

            //Если такой есть, достаем бафф из карты и сверяем уровни.
            //Если уровни совпадают, комбинируем (просто присваеваем активному баффу время действия добовляемого).
            //Если не совпадают, то во избежание сбоев при удалении эффекта (к примеру с атрибутами) по истечению времени
            //действия удаляем активный бафф и добавляем новый другого уровня.

            BonusEffect activeBuff = this.getActiveBuff(buff.getId());

            if (activeBuff.getType() == buff.getType()) {

                activeBuff.combineBuffs(buff);
            } else {

                this.removeBuff(activeBuff.getId());

                this.activeBuffs.put(buff.getId(), buff);

                //Buff.of(buff.getId()).applyBuffEffect(this.livingBase, this.world, buff);
            }
        } else {

            //Если баффа нет, добавляем в карту.
            this.activeBuffs.put(buff.getId(), buff);

            //Применяем эффект баффа.
            // Buff.of(buff.getId()).applyBuffEffect(this.livingBase, this.world, buff);
        }
    }

    private BonusEffect getActiveBuff(int id) {
        BonusEffect effect = new BonusEffect(0, 0);
        return effect;
    }

    //Удаляем бафф и его эффект. Нужен так же для внешнего удаления баффа с флагом isPersistent.
    public void removeBuff(int buffId) {

        if (this.isBuffActive(buffId)) {

            BonusEffect activeBuff = this.getActiveBuff(buffId);

            //Buff.of(buffId).removeBuffEffect(this.livingBase, this.world, activeBuff);

            this.activeBuffs.remove(buffId);
        }
    }

    public void updateBuffs() {

        //Проверка наличия активных баффов.
        if (this.haveActiveBuffs()) {

            //Итератор по идентификаторам.
            Iterator buffsIterator = this.activeBuffsIdSet().iterator();

            while (buffsIterator.hasNext()) {

                int buffId = (Integer) buffsIterator.next();

                //Достаём бафф из карты используя идентификатор.
                BonusEffect buff = this.getActiveBuff(buffId);

                //Вызов метода обновления баффа и одновременно проверка на истечения времени действия.
                if (!buff.updateBuff()) {

                    //Снимаем эффект.
                    //Buff.of(buffId).removeBuffEffect(this.livingBase, this.world, buff);
                    //Удаляем на сервере.
                    buffsIterator.remove();
                }
            }
        }
    }



  /*  //Метод, который применяет эффект баффа если isReady() возвращает true.
    protected void onActive(EntityLivingBase entityLivingBase, World world, ActiveBuff buff) {

        int
                tier = buff.getTier(),
                duration = buff.getDuration();
    }

    //Служебный метод, определяющий возможность применить эффект в onActive().
    protected boolean isReady(BonusEffect buff) {

        int
                tier = buff.getTier(),
                duration = buff.getDuration();

        return false;
    }

    //Добавляем эффект баффа.
    public void applyBuffEffect(EntityLivingBase entityLivingBase, World world, ActiveBuff buff) {

        if (!world.isRemote) {

            int
                    id = buff.getId(),
                    tier = buff.getTier();
        }
    }


    //Удаляем эффект баффа.
    public void removeBuffEffect(EntityLivingBase entityLivingBase, World world, ActiveBuff buff) {

        if (!world.isRemote) {

            int
                    id = buff.getId(),
                    tier = buff.getTier();
        }
    }

    //Сеттеры для описания свойств и геттеры.
    */

    /*protected Buff setIconIndex(int x, int y) {

        this.iconIndex = x + y * 8;

        return this;
    }

    public int getIconIndex() {

        return this.iconIndex;
    }

    public boolean shouldKeepOnDeath() {

        return this.keepOnDeath;
    }*/

    //Проверка наличия эффектов.
    public boolean haveActiveBuffs() {

        return !this.activeBuffs.isEmpty();
    }

    //Быстрая проверка на наличие указанного баффа.
    public boolean isBuffActive(int buffId) {

        return this.activeBuffs.containsKey(buffId);
    }

    //Возвращает сет ключей-идентификаторов.
    public Set<Integer> activeBuffsIdSet() {

        return this.activeBuffs.keySet();
    }

    //Возвращает коллекцию активных баффов (ActiveBuff).
    public Collection<BonusEffect> activeBuffsCollection() {

        return this.activeBuffs.values();
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
