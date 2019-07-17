package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Sprite {

    public enum State {Walk, Shoot, Throw, Jump, Fall, Idle, Crouch}
    public State currentState;
    public State previousState;

    public float elspsedTime;

    public World world;
    public Body b2body;
    private PlayScreen screen;

    private Texture idle;
    private Texture walking;
    private Texture shooting;
    private Texture jumping;
    private Texture throwing;
    private Texture ducking;
   /* private TextureAtlas walking;
    private TextureAtlas shooting;
    private TextureAtlas jumping;
    private TextureAtlas falling;
    private TextureAtlas throwing;*/
    //private TextureAtlas looking_up;
    //private TextureAtlas shooting_up;
    //private TextureAtlas crouching;
    //private TextureAtlas crouch_shooting;
    //private TextureAtlas crouch_throwing;

    private  Animation<TextureRegion> idle_anim;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> shoot;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> fall;
    private Animation<TextureRegion> granade;
    private Animation<TextureRegion> crouch;
    ArrayList<Bullet> bullets;

    public TextureRegion temp;

    private int bullethitcount;
    private boolean settodestroy,destroyed;
    //private Animation<TextureRegion> look_up;
    //private Animation<TextureRegion> shoot_up;
    //private Animation<TextureRegion> crouch_shoot;
    //private Animation<TextureRegion> crouch_throw;

    public boolean Right;//, In_air, Jump, Shoot, Throw, Crouch, Up, Walk, Idle, Fall;
    public  int count = 0;

    public Player(World world,PlayScreen screen,ArrayList<Bullet>bullets) {

        this.world = world;
        this.screen = screen;
        this.bullets = bullets;
        currentState = State.Walk;
        previousState = State.Walk;
        elspsedTime = 0f;

        Right = true;
        //Idle = true;
        //Walk = false;
        //In_air = false;
        //Jump = false;
        //Fall = false;
        //Shoot = false;
        //Throw = false;
        //Crouch = false;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        idle = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/idle.png"));
        walking = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/walking.png"));
        shooting = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/shooting.png"));
        jumping = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/jumping.png"));
        throwing = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/throwing.png"));
        ducking = new Texture(Gdx.files.internal("Sprites/Player/Spritesheets/temp/ducking.png"));

        /*walking = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Walking/walking.txt"));
        shooting = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Shooting/shooting.txt"));
        jumping = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Jump/jumping.txt"));
        falling = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Jump/jumping.txt"));
        throwing = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Granade/throwing.txt"));*/
        //looking_up = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Up/up_looking.txt"));
        //shooting_up = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Up_shooting/Up_shooting.txt"));
        //crouching = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Crouch/crouching.txt"));
        //crouch_shooting = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Crouch_shooting/crouch_shooting.txt"));
        //crouch_throwing = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Crouch_granade/crouching_granade.txt"));


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
        shoot = new Animation(1f/10f, frames);
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

        /*walk = new Animation(1f/15f, walking.getRegions());
        shoot = new Animation(1f/15f, shooting.getRegions());
        jump = new Animation(1f/9f, jumping.getRegions());
        fall = new Animation(1f/9f, jumping.getRegions());
        granade = new Animation(1f/15f, throwing.getRegions());*/
        //look_up = new Animation(1f/15f, looking_up.getRegions());
        //shoot_up = new Animation(1f/15f, shooting_up.getRegions());
        //crouch = new Animation(1f/15f, crouching.getRegions());
        //crouch_shoot = new Animation(1f/15f, crouch_shooting.getRegions());
        //crouch_throw = new Animation(1f/15f, crouch_throwing.getRegions());

        definePlayer();

        setBounds(0, 0, 52f, 78f);
        setRegion(new TextureRegion(idle, 0, 0, 52, 78));
    }

    public void update(float dt) {
        //setBounds(0, 0, 52f/2, 78f/2);
        //setPosition((b2body.getPosition().x) , (b2body.getPosition().y * MyGdxGame.ppm) - getHeight()/2);

        setPosition((b2body.getPosition().x   + getWidth() + 120), (b2body.getPosition().y * MyGdxGame.ppm) - getHeight()/2 + 20);
        setRegion(getFrame(dt));

        //temp = getFrame(dt);
        //System.out.println(getX() + " | " + (b2body.getPosition().x * MyGdxGame.ppm - 52f/2));
    }

    /*public  void inputHandle() {
        if(Gdx.input.isKeyPressed(Input.Keys.X))
            Shoot = true;
    }*/

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        /*if(Jump) {
            if(!Fall)
                region = jump.getKeyFrame(elspsedTime, false);
            else
                region = fall.getKeyFrame(elspsedTime, false);
        }
        else if(Shoot)
            region = shoot.getKeyFrame(elspsedTime, true);
        else if(Throw)
            region = granade.getKeyFrame(elspsedTime, false);
        else if(Crouch) {
            if(Shoot)
                region = crouch_shoot.getKeyFrame(elspsedTime, true);
            else if(Throw)
                region = crouch_throw.getKeyFrame(elspsedTime, false);
            else
                region = crouch.getKeyFrame(elspsedTime, true);
        }
        else if(Up) {
            if(Shoot)
                region = shoot_up.getKeyFrame(elspsedTime, true);
            else
                region = look_up.getKeyFrame(elspsedTime, true);
        }
        else if(Walk)
            region = walk.getKeyFrame(elspsedTime, true);
        else if(Idle)
            region = idle_anim.getKeyFrame(elspsedTime, true);
        */

        switch (currentState) {
            //case Fall:
            case Jump:
                region = jump.getKeyFrame(elspsedTime, false);
                break;
            case Fall:
                region = fall.getKeyFrame(elspsedTime, true);
                break;
            case Throw:
                region = granade.getKeyFrame(elspsedTime, false);
                break;
            case Shoot: {
                region = shoot.getKeyFrame(elspsedTime, true);
                if (count < 5){
                    float bulletx = b2body.getPosition().x;
                    float bulletY = b2body.getPosition().y;
                    System.out.println(bulletx + " " + getX() + " " + bulletY + " " + getY());
                    if (!Right)
                        bullets.add(new Bullet(world, screen, bulletx, bulletY, -1.3f));
                    else
                        bullets.add(new Bullet(world, screen, bulletx, bulletY, 1.3f));
                }
                else if(count > 20)
                    count = 0;

                count++;
                break;
            }
            case Walk:
                region = walk.getKeyFrame(elspsedTime, true);
                break;
            case Crouch:
                region = crouch.getKeyFrame(elspsedTime, true);
                break;
            default:
                region = idle_anim.getKeyFrame(elspsedTime, true);
                break;

        }

        if((b2body.getLinearVelocity().x < 0 || !Right) && !region.isFlipX()) {
            region.flip(true, false);
            Right = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || Right) && region.isFlipX()) {
            region.flip(true, false);
            Right = true;
        }

        elspsedTime = currentState == previousState ? elspsedTime + dt : 0;

        /*if(currentState == previousState)
            elspsedTime += dt;
        else
            elspsedTime = 0;*/

        previousState = currentState;

        return region;

    }

    public State getState() {

        if(b2body.getLinearVelocity().y > 0)
            return  State.Jump;
        else if(b2body.getLinearVelocity().y < 0)
            return State.Fall;
        else if(b2body.getLinearVelocity().x != 0)
            return State.Walk;
        else if(Gdx.input.isKeyPressed(Input.Keys.Z))
            return State.Shoot;
        else if(Gdx.input.isKeyPressed(Input.Keys.X))
            return State.Throw;
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            return State.Crouch;
        else
            return State.Idle;
    }

    //

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set((400f + 52f/2f) / MyGdxGame.ppm,  (100f + 78f/2f) /MyGdxGame.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = MyGdxGame.Player_bit;
        fdef.filter.maskBits = MyGdxGame.Enemy_Bit | MyGdxGame.Enemy_Bit | MyGdxGame.Ground_bit | MyGdxGame.Object_Bit;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40/2 / MyGdxGame.ppm, 40/2 / MyGdxGame.ppm);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }



    public  void  playerBulletHit(){
        bullethitcount++;
        if(bullethitcount > 20){
            settodestroy = true;
        }
    }
}

