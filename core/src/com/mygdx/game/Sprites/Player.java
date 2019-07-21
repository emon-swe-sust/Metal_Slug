package com.mygdx.game.Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Sprite {

    public enum State {Walk, Shoot, Throw, Jump, Fall, Idle, Crouch}
    public State currentState;
    public State previousState;

    public float elspsedTime;

    public World world;
    public Body b2body;
    private PlayScreen screen;
    public int fight = 0;

    private Texture idle;
    private Texture walking;
    private Texture shooting;
    private Texture jumping;
    private Texture throwing;
    private Texture ducking;

    private  Animation<TextureRegion> idle_anim;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> shoot;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> fall;
    private Animation<TextureRegion> granade;
    private Animation<TextureRegion> crouch;
    public ArrayList<Bullet> bullets;

    public TextureRegion temp;
    public MyGdxGame game;
    public Sound diee;

    private int bullethitcount;
    private boolean settodestroy,destroyed;
    public boolean Right;//, In_air, Jump, Shoot, Throw, Crouch, Up, Walk, Idle, Fall;
    public int count = 0;
    float up=-1;
    public int stand =0;

    public Player(World world, PlayScreen screen, MyGdxGame game) {//,ArrayList<Bullet>bullets) {

        this.world = world;
        this.screen = screen;
        this.game = game;
        diee = Gdx.audio.newSound(Gdx.files.internal("Sound/dead.mp3"));

        //this.bullets = bullets;
        currentState = State.Walk;
        previousState = State.Walk;
        elspsedTime = 0f;
        bullets = new ArrayList<Bullet>();

        Right = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        idle = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/idle.png"));
        walking = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/walking.png"));
        shooting = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/shooting.png"));
        jumping = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/jumping.png"));
        throwing = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/throwing.png"));
        ducking = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/ducking.png"));

        for(int i=0; i<5; i++)
            frames.add(new TextureRegion(idle, 52*i, 0, 52, 78));
        idle_anim = new Animation(1f/10f, frames);
        frames.clear();

        for(int i=0; i<13; i++)
            frames.add(new TextureRegion(walking, 52*i, 0, 52, 78));
        walk = new Animation(1f/15f, frames);
        frames.clear();

        for(int i=0; i<10; i++)
            frames.add(new TextureRegion(shooting, 52*i, 0, 52, 78));
        shoot = new Animation(1f/15f, frames);
        frames.clear();

        for(int i=0; i<6; i++)
            frames.add(new TextureRegion(jumping, 52*i, 0, 52, 78));
        jump = new Animation(1f/10f, frames);
        frames.clear();

        for(int i=0; i<6; i++)
            frames.add(new TextureRegion(jumping, 52*i, 0, 52, 78));
        fall = new Animation(1f/10f, frames);
        frames.clear();

        for(int i=0; i<7; i++)
            frames.add(new TextureRegion(throwing, 52*i, 0, 52, 78));
        granade = new Animation(1f/10f, frames);
        frames.clear();

        for(int i=0; i<7; i++)
            frames.add(new TextureRegion(ducking, 52*i, 0, 52, 78));
        crouch = new Animation(1f/10f, frames);
        frames.clear();

        definePlayer();

        setBounds(0, 0, 52f, 78f);
        setRegion(new TextureRegion(idle, 0, 0, 52, 78));
    }

    public void update(float dt, boolean spacePressed) {
        if(settodestroy){
            game.setScreen(game.menuScreen);
        }
        if(up>=0){
            setY(getY()-5f);
            if(up >= 1.5)
                up = -1;
            else
                up+=dt;
        }
        else {
            if(Right)
                setPosition(b2body.getPosition().x - getWidth() / 4, b2body.getPosition().y - getHeight() / 4);
            else
                setPosition(b2body.getPosition().x - getWidth() /1.5f, b2body.getPosition().y - getHeight() / 4);
        }
        setRegion(getFrame(dt, spacePressed));
    }

    public TextureRegion getFrame(float dt, boolean spacePressed) {
        currentState = getState(spacePressed);

        TextureRegion region;

        switch (currentState) {
            //case Fall:
            case Jump: {
                region = jump.getKeyFrame(elspsedTime, false);
                stand = 0;
                break;
            }
            case Fall: {
                region = fall.getKeyFrame(elspsedTime, true);
                stand = 0;
                break;
            }
            case Throw: {
                region = granade.getKeyFrame(elspsedTime, false);
                stand = 0;
                break;
            }
            case Shoot: {
                region = shoot.getKeyFrame(elspsedTime, true);

//                 /*if(shoot.getKeyFrameIndex(elspsedTime) == 9)
//                     elspsedTime = 0;*/
//                 if (count == 26){


                if (count == 2){

                    float bulletx;
                    float bulletY;
                    if(Right)
                        bulletx = b2body.getPosition().x + 10;

                    else
                        bulletx = b2body.getPosition().x - 10;

                    bulletY = b2body.getPosition().y + 5;

                    if(Right)
                        bullets.add(new Bullet(world, screen, bulletx, bulletY, 250f));
                    else
                        bullets.add(new Bullet(world, screen, bulletx, bulletY, -250f));
                }
                else if(count > 15)
                    count = 0;
                count++;
                stand = 0;
                break;
            }
            case Walk: {
                region = walk.getKeyFrame(elspsedTime, true);
                stand = 0;
                break;
            }
            case Crouch: {
                region = crouch.getKeyFrame(elspsedTime, true);
                stand = 1;
                break;
            }
            default: {
                region = idle_anim.getKeyFrame(elspsedTime, true);
                stand = 0;
                break;
            }
        }

        if((b2body.getLinearVelocity().x < 0 || !Right) && !region.isFlipX()) {
            region.flip(true, false);
            stand=1;
            Right = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || Right) && region.isFlipX()) {
            region.flip(true, false);
            stand = 1;
            Right = true;
        }
        elspsedTime = currentState == previousState ? elspsedTime + dt : 0;

        previousState = currentState;
        return region;
    }

    public State getState(boolean spacePressed) {

        if(b2body.getLinearVelocity().y > 0 && spacePressed)
            return  State.Jump;
        else if(b2body.getLinearVelocity().y < 0 && spacePressed)
            return State.Fall;
        else if(b2body.getLinearVelocity().x != 0)
            return State.Walk;
        else if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
            MenuScreen.click.play();
            return State.Shoot;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.X))
            return State.Throw;
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            return State.Crouch;
        else
            return State.Idle;
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200f + 52/2, 300f + 78/2);

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 20);
        fdef.filter.categoryBits = MyGdxGame.PLAYER_BIT;
        fdef.filter.maskBits = MyGdxGame.ENEMYBULLET_BIT | MyGdxGame.GROUND_BIT | MyGdxGame.BOSS_BULLET_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public  void  life() {
        if (stand == 0) {
            diee.play();
            bullethitcount++;
            MyGdxGame.life--;
            up = 0;
            setPosition(b2body.getPosition().x - getWidth() / 4, 500);
            if (bullethitcount == 3) {
                settodestroy = true;
            }
        }
    }

    public void bossfight() {
        if(fight == 0 && stand == 0){
            diee.play();
            bullethitcount++;
            MyGdxGame.life--;
            fight++;
            up = 0;
            MyGdxGame.wait = 1000;
            setPosition(b2body.getPosition().x - getWidth() / 4, 900);
            if (bullethitcount == 3) {
                settodestroy = true;
            }
        }
        else{
            fight++;
            if(fight==25)
                fight=0;
        }
    }
}