package com.mygdx.game.Sprites.Boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.EnemyBullet;

import java.util.ArrayList;

public class FirstBoss extends Sprite {  //Each frame size 99 x 61

    enum State {idle, charge, shoot}

    private Texture laser_fire;
    private Texture canon_fire;
    private Texture canon_charge;

    private Animation<TextureRegion> laser;
    private Animation<TextureRegion> shooting;
    private Animation<TextureRegion> charging;

    boolean isDead;

    State currentState;
    State previousState;


    float shootDistance;
    float animationChangeTime;
    float elapsedTime;
    float hitPoint;

    public World world;
    public Body sniper_body;
    private PlayScreen screen;

    public FirstBoss(World world, PlayScreen screen) {

        this.world = world;
        this.screen = screen;

        isDead = false;

        shootDistance = 320f;
        animationChangeTime = 20;
        elapsedTime = 0f;
        hitPoint = 30;

        currentState = State.idle;
        previousState = State.shoot;

        laser_fire = new Texture("Sprites/Enemies/Tetsuyuki/laser3.png");
        canon_fire = new Texture("Sprites/Enemies/Tetsuyuki/canon_fire2.png");
        canon_charge = new Texture("Sprites/Enemies/Tetsuyuki/canon_charge2.png");

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=0; i<9; i++)
            frames.add(new TextureRegion(laser_fire, i*99, 0, 99, 61));
        laser = new Animation<TextureRegion>(1f/15f, frames);
        frames.clear();

        for(int i=6; i>=0; i--)
            frames.add(new TextureRegion(canon_fire, i*99, 0, 99, 61));
        shooting = new Animation<TextureRegion>(1f/10f, frames);
        frames.clear();

        for(int i=18; i>=0; i--)
            frames.add(new TextureRegion(canon_charge, i*99, 0, 99, 61));
        charging = new Animation<TextureRegion>(1f/15f, frames);
        frames.clear();

        setBounds(99, 0, 99, 61);
        setRegion(new TextureRegion(canon_fire, 99, 0, 99, 61));
        setPosition(3900, 167); //130 - 167

    }

    public void update(float dt) {
        if(!isDead) {
            if (currentState == State.idle) {
                if (getY() < 90) {
                    setPosition(getX(), getY() + 10f);
                } else if (getY() > 200)
                    setPosition(getX(), getY() - 10f);
                setRegion(new TextureRegion(canon_fire, 99, 0, 99, 61));
                if (animationChangeTime > 4) {
                    animationChangeTime = 0;
                    currentState = State.charge;
                    elapsedTime = 0;
                }

                animationChangeTime += dt;
            } else if (currentState == State.charge) {
                //System.out.println((charging.getKeyFrameIndex(elapsedTime)));
                if (charging.getKeyFrameIndex(elapsedTime) == 18) {
                    currentState = State.shoot;
                    elapsedTime = 0f;
                }
                setRegion(charging.getKeyFrame(elapsedTime, false));
            } else if (currentState == State.shoot) {
                //System.out.println((shooting.getKeyFrameIndex(elapsedTime)));
                if (shooting.getKeyFrameIndex(elapsedTime) == 6) {
                    currentState = State.idle;
                    elapsedTime = 0f;
                }
                setRegion(shooting.getKeyFrame(elapsedTime, false));
            }
        } else {
            //dead
        }

        System.out.println(currentState + " >< " + getY());

        elapsedTime += dt;

    }

    /*public void update(float dt) {
        if(animationChangeTime > 4) {

            animationChangeTime = 0;
            if(currentState == State.idle) {

                currentState = State.charge;
                *//*if(getY() < 150)
                    translateY(30);
                else
                    translateY(-30);*//*
            }
            else if(currentState == State.charge)
                currentState = State.shoot;
            else if(currentState == State.shoot)
                currentState = State.idle;
        }
        else {

            *//*switch (currentState) {
                case idle: {
                    setRegion(new TextureRegion(canon_fire, 0, 0, 99, 61));
                    if(getY() < 150)
                        translateY(1f);
                    else
                        translateY(-1f);
                    break;
                }
                case charge:
                    setRegion(charging.getKeyFrame(1f/15f, false));
                    break;
                case shoot: {
                    setRegion(shooting.getKeyFrame(1f / 6f, false));
                    break;
                }*//*
            animationChangeTime += dt;
            }
        }
    }*/
}
