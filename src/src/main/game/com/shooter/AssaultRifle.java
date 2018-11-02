package main.game.com.shooter;

public class AssaultRifle extends Gun {
    private int repeatshot = 0;
    double mouseposx = 0;
    double mouseposy = 0;
    int counter = 0;
    AssaultRifle(int ammunition) {
        super(ammunition, 2, "AssaultRifle", 8, 90, GunType.ASSAULTRIFLE);
    }

    @Override
    void shoot(double startposx, double startposy, double mouseposx, double mouseposy){
        if(ammunition==0 || cooldown>0){
            return;
        }
        Bullet bullet = new Bullet(mouseposx, mouseposy,startposx,startposy,speed);
        this.getChildren().add(bullet);
        cooldown = cooldownbetweenshots;
        ammunition--;

        this.mouseposx = mouseposx;
        this.mouseposy = mouseposy;
        repeatshot = 2;
        counter = 0;
    }

    @Override
    void update(){
        counter++;
        Character gunuser = (Character)getParent().getParent();
        if(counter%4==0&&repeatshot>0){
            shoot(gunuser.getCharacterCenterX(),gunuser.getCharacterCenterY());
        }
        super.update();
    }

    private void shoot(double startposx, double startposy) {
        if(ammunition==0){
            repeatshot = 0;
            return;
        }
        Bullet bullet = new Bullet(mouseposx, mouseposy,startposx,startposy,speed);
        this.getChildren().add(bullet);
        ammunition--;
        repeatshot--;
    }
}
