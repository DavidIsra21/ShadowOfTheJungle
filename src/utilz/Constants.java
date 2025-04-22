package utilz;

public class Constants {

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int RUNNING = 4;
        public static final int IDLE = 8;
        public static final int JUMP = 13;
        public static final int FALLING = 11;
        public static final int GROUND = 12;
        public static final int HIT = 2;
        public static final int ATTACK_1 = 0;
        public static final int ATTACK_2 = 1;
        public static final int ATTACK_JUMP_1 = 6; //ATTACK_DISTANCE_1
        public static final int ATTACK_JUMP_2 = 7;

        public static int GetSpriteAmount (int player_action) {

            switch(player_action) {

                case RUNNING:
                    return 6; //4
                case IDLE:
                    return 5; //8
                case JUMP:
                    return 2; //13
                case FALLING:
                    return 2; //11
                case GROUND:
                    return 2; //12
                case HIT:
                    return 6; //2
                case ATTACK_1:
                    return 6; //0
                case ATTACK_2:
                    return 6; //1
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                default:
                    return 1;
            }

        }
    }
}
