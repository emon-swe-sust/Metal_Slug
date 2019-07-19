package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Bullet;
import com.mygdx.game.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA=contact.getFixtureA();
        Fixture fixB=contact.getFixtureB();

        System.out.println("Tashfee");

        int  col = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (col){

            case MyGdxGame.BULLET_BIT | MyGdxGame.GROUND_BIT:

                if (fixA.getFilterData().categoryBits == MyGdxGame.BULLET_BIT) {
                    ((Bullet)fixA.getUserData()).hitenemy();
                } else {
                    ((Bullet)fixB.getUserData()).hitenemy();
                }
            break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
