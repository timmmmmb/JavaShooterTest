package main.game.com.shooter;

class Machinegun extends Gun {
    Machinegun(int ammunition){
        super(ammunition, 1, "Machinegun", 10, 5, GunType.MACHINEGUN);
    }
}
