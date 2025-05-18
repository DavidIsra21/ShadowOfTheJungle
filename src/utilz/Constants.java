package utilz;

import Main.Game;
import gamestates.State;

public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANI_SPEED = 25;

    public static class Numbers {
        public static final int NUMBER_WIDTH_DEFAULT = 30;
        public static final int NUMBER_HEIGHT_DEFAULT = 30;
        public static final int NUMBER_WIDTH = (int)(NUMBER_WIDTH_DEFAULT * Game.SCALE);
        public static final int NUMBER_HEIGHT = (int)(NUMBER_HEIGHT_DEFAULT * Game.SCALE);
    }

    public static class Projectiles {
        public static final int E2_BALL_DEFAULT_WIDTH = 48;
        public static final int E2_BALL_DEFAULT_HEIGHT = 48;

        public static final int E2_BALL_WIDTH = (int) (Game.SCALE * E2_BALL_DEFAULT_WIDTH);
        public static final int E2_BALL_HEIGHT = (int) (Game.SCALE * E2_BALL_DEFAULT_HEIGHT);

        public static final float SPEED = 0.8f * Game.SCALE;
    }


    public static class TrapConstants {

        public static final int SPIKE = 0;
        public static final int SPEAR = 1;

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);

        public static final int SPEAR_WIDTH_DEFAULT = 32;
        public static final int SPEAR_HEIGHT_DEFAULT = 32;
        public static final int SPEAR_WIDTH = (int) (Game.SCALE * SPEAR_WIDTH_DEFAULT);
        public static final int SPEAR_HEIGHT = (int) (Game.SCALE * SPEAR_HEIGHT_DEFAULT);

        public static int GetSpriteAmount(int trap_type) {
            switch (trap_type) {
                case SPIKE:
                    return 10;
                    case SPEAR:
                        return 8;
            }

            return 0;
        }
    }

    public static class EnemyConstants {
        public static final int ENEMY1 = 0;
        public static final int ENEMY2 = 2;
        public static final int CHEST = 3;

        public static final int CLOSE_CHEST = 1;
        public static final int OPEN_CHEST = 0;
        public static final int HIT_CHEST = 2;

        public static final int IDLE_E2 = 5;
        public static final int ATTACK1_E2 = 2;
        public static final int ATTACK2_E2 = 3;

        public static final int IDLE = 5;
        public static final int RUNNING = 4;
        public static final int ATTACK = 3;
        public static final int HIT = 6;
        public static final int DEAD = 2;

        public static final int ENEMY1_WIDTH_DEFAULT = 80; //nuevo 80//72
        public static final int ENEMY1_HEIGHT_DEFAULT = 64; //nuevo 64//32

        public static final int ENEMY1_WIDTH = (int)(ENEMY1_WIDTH_DEFAULT * Game.SCALE);
        public static final int ENEMY1_HEIGHT = (int)(ENEMY1_HEIGHT_DEFAULT * Game.SCALE);

        public static final int ENEMY1_DRAWOFFSET_X = (int)(26 * Game.SCALE);
        public static final int ENEMY1_DRAWOFFSET_Y = (int)(35 * Game.SCALE);

        public static final int ENEMY2_WIDTH_DEFAULT = 64; //nuevo 80//72
        public static final int ENEMY2_HEIGHT_DEFAULT = 64; //nuevo 64//32

        public static final int ENEMY2_WIDTH = (int)(ENEMY1_WIDTH_DEFAULT * Game.SCALE);
        public static final int ENEMY2_HEIGHT = (int)(ENEMY1_HEIGHT_DEFAULT * Game.SCALE);

        public static final int ENEMY2_DRAWOFFSET_X = (int)(10 * Game.SCALE);
        public static final int ENEMY2_DRAWOFFSET_Y = (int)(10 * Game.SCALE);

        public static final int CHEST_WIDTH_DEFAULT = 64; //nuevo 80//72
        public static final int CHEST_HEIGHT_DEFAULT = 32; //nuevo 64//32

        public static final int CHEST_WIDTH = (int)(CHEST_WIDTH_DEFAULT * Game.SCALE);
        public static final int CHEST_HEIGHT = (int)(CHEST_HEIGHT_DEFAULT * Game.SCALE);

        public static final int CHEST_DRAWOFFSET_X = (int)(22 * Game.SCALE);
        public static final int CHEST_DRAWOFFSET_Y = (int)(12 * Game.SCALE);

        public static int GetSpriteAmount(int enemy_type, int enemy_state) {

            switch(enemy_type) {
                case ENEMY1:
                    switch(enemy_state) {
                        case IDLE:
                            return 6;
                            case RUNNING:
                                return 7;
                        case ATTACK:
                            return 9;
                            case HIT:
                                return 4;
                                case DEAD:
                                    return 14;
                    }
                case ENEMY2:
                    switch(enemy_state) {
                        case IDLE_E2:
                            return 8;
                        case ATTACK1_E2:
                            return 14;
                        case ATTACK2_E2:
                            return 14;
                    }
                case CHEST:
                    switch(enemy_state) {
                        case CLOSE_CHEST:
                            return 6;
                        case OPEN_CHEST:
                            return 16;
                        case HIT_CHEST:
                            return 1;
                    }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
                case ENEMY1:
                    return 10;
                case CHEST:
                    return 20;
                    default:
                        return 0;
            }
        }

        public static int GetEnemyDmg(int enemy_type) {
            switch (enemy_type) {
                case ENEMY1:
                    return 2; //damage to player
                default:
                    return 0;
            }
        }
    }

    public static class Enviroment{
        public static final int BG_LIANAS_WIDTH_DEFAULT = 470;
        public static final int BG_LIANAS_HEIGHT_DEFAULT = 218;
        public static final int BG_LEJANO_WIDTH_DEFAULT = 1536;
        public static final int BG_LEJANO_HEIGHT_DEFAULT = 1024;

        public static final int BG_LIANAS_WIDTH = (int)(BG_LIANAS_WIDTH_DEFAULT * Game.SCALE);
        public static final int BG_LIANAS_HEIGHT = (int)(BG_LIANAS_HEIGHT_DEFAULT * Game.SCALE);
        public static final int BG_LEJANO_WIDTH = (int)(BG_LEJANO_WIDTH_DEFAULT * Game.SCALE);
        public static final int BG_LEJANO_HEIGHT = (int)(BG_LEJANO_HEIGHT_DEFAULT * Game.SCALE);

    }

    public static class UI {
        public static class Buttons {
//            public static final int B_WIDTH_DEFAULT = 140; //600
//            public static final int B_HEIGHT_DEFAULT = 56; //200
            public static final int B_WIDTH_DEFAULT = 600; //600
            public static final int B_HEIGHT_DEFAULT = 200; //200
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 200;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE * .2);
        }

        public static class URMButtons {
            public static final int URM_DEFAULT_SIZE = 200;
            public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE * .2);
        }
    }

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
        public static final int HIT = 10;
        public static final int ATTACK_1 = 0;
        public static final int ATTACK_2 = 1;
        public static final int ATTACK_JUMP_1 = 6; //ATTACK_DISTANCE_1
        public static final int ATTACK_JUMP_2 = 7;
        public static final int DEAD = 9;

        public static int GetSpriteAmount (int player_action) {

            switch(player_action) {
                case DEAD:
                    return 4;
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
                    return 3; //2
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
