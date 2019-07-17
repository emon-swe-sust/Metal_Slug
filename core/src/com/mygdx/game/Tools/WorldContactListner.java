package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Bullet;

public class WorldContactListner implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch(cdef){

            case MyGdxGame.Enemy_Bit | MyGdxGame.Enemy_Bit:
                break;
            case MyGdxGame.Ground_bit | MyGdxGame.Bullet_bit:

                if(fixA.getFilterData().categoryBits == MyGdxGame.Bullet_bit){
                    ((Bullet)fixA.getUserData()).hitenemy();
                }
                else if(fixB.getFilterData().categoryBits == MyGdxGame.Bullet_bit){
                    ((Bullet)fixB.getUserData()).hitenemy();
                }

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
