package main.game.com.shooter;

class Pistol extends Gun {
    Pistol(int ammunition){
        super(ammunition, 2, "Pistol", 5, 60, GunType.PISTOL);
    }

    @Override
    void shoot(double startposx, double startposy, double mouseposx, double mouseposy){
        ammunition++;
        super.shoot(startposx,startposy,mouseposx,mouseposy);
    }
}
