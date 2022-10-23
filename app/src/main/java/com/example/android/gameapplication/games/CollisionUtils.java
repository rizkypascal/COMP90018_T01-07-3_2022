package com.example.android.gameapplication.games;

/**
 * @author Tony Shu
 * @date 09/10/2022
 * @desc detect all kind of collisions, ideally this is follows the singleton design pattern
 */
public final class CollisionUtils {

    /**
     *
     * @param jumper
     * @param board
     * @return true if the jumper falls onto the given board, false otherwise.
     */
    public static Boolean jumperBoardCollision(Jumper jumper,Board board)
    {
        Integer jumperPosX = jumper.getPosX();
        Integer jumperPosY = jumper.getPosY();
        Integer boardPosY = board.getPosY() - board.getBoardHeight()/2;
        if (jumperPosY >= boardPosY - 10 && jumperPosY <= boardPosY + 10)
        {
            if(jumperPosX>= board.getXLeft() && jumperPosX<= board.getXRight())
            {
                return true;
            }
        }
        return false;

    }

    /**
     *
     * @param bullet
     * @param monster
     * @return true if the monster got shot, false otherwise
     */
    public static Boolean bulletMonsterCollision(Bullet bullet,Monster monster)
    {
        Integer bulletPosX = bullet.getPosX();
        Integer bulletPosY = bullet.getPosY();
        Integer monsterPosX = monster.getPosX();
        Integer monsterPosY = monster.getPosY()+monster.getSize();
        if(bulletPosX>=monsterPosX-monster.getSize()/2 && bulletPosX<=monsterPosX+monster.getSize() && bulletPosY<=monsterPosY)
        {
            return true;
        }
        return false;
    }
}
