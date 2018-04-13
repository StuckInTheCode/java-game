package Arcanoid;

public class BonusEffect {
    Thread thread;// cоздать поток, общающийся с родительским потоком через класс-менеджер, уничтожающийся по времени
    private int id, duration, type;

    public BonusEffect(int mId, int mType, int mDuration) {
        thread = new Thread();
        this.id = mId;
        this.type = mType;
        this.duration = mDuration;
    }

    public BonusEffect(int buffId, int buffDuration) {
        thread = new Thread();
        this.id = buffId;
        this.type = 0;
        this.duration = buffDuration;
    }

    public void applyEffect(Game game) {
        switch (type) {
            case 1: {
                Game.ball.velocityY *= 0.5;
                Game.ball.velocityX *= 0.5;
                //bonusIV.setVisible(false);
                break;
            }
            case 2: {
                Main.MyGame.Life++;
                break;
            }
            case 3: {
                Game.ball.velocityY += 0.5;
                Game.ball.velocityX += 0.5;
                break;
            }
            case 4: {
                Game.player.setWidth(100);
                break;
            }
            case 0: {
                break;
            }
        }
        thread.start();
        //какие-то действия
    }
    //Геттеры свойств.

    //Метод, обновляющий бафф путём декремента продолжительности действия (duration), возвращает true если время действия не истекло.
    public boolean updateBuff() {

        if (this.duration > 0) {
            this.duration--;
        }

        //true - время действия ещё не истекло, false - истекло, удаляем бафф.
        return this.duration > 0;
    }

    //Комбинирование бафов с одинаковыми уровнями.
    public void combineBuffs(BonusEffect buff) {

        //Обновляем время действия только если новый бафф будет активен дольше.
        if (buff.duration > this.duration && this.type == buff.type) {
            this.duration = buff.duration;
            //buff.stopEffect();
        }
    }

    public int getId() {

        return this.id;
    }

    public int getDuration() {

        return this.duration;
    }

    public int getType() {

        return this.type;
    }

    public void removeEffect(Game game) {
        //какие-то действия
        switch (type) {
            case 1: {
                Game.ball.velocityY /= 0.5;
                Game.ball.velocityX /= 0.5;
                //bonusIV.setVisible(false);
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                Game.ball.velocityY -= 0.5;
                Game.ball.velocityX -= 0.5;
                break;
            }
            case 4: {
                Game.player.setWidth(80);
                break;
            }
            case 0: {
                break;
            }
        }
        thread.interrupt();
    }
}