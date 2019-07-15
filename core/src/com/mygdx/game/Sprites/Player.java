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

public class Player extends Sprite {

    public enum State {Walk, Shoot, Throw, Jump, Fall, Idle, Crouch}
    public State currentState;
    public State previousState;

    public float elspsedTime;

    public World world;
    public Body b2body;

    private Texture idle;
    private Texture walking;
    private Texture shooting;
    private Texture jumping;
    private Texture throwing;
    private Texture ducking;

    private Animation<TextureRegion> idle_anim;
    private Animation<TextureRegion> walk;
    private Animation<TextureRegion> shoot;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> fall;
    private Animation<TextureRegion> granade;
    private Animation<TextureRegion> crouch;

    public boolean Right;//, In_air, Jump, Shoot, Throw, Crouch, Up, Walk, Idle, Fall;

    public Player(World world) {

        this.world = world;
        currentState = State.Walk;
        previousState = State.Walk;
        elspsedTime = 0f;
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

        definePlayer();

        setBounds(0, 0, 52f, 78f);
        setRegion(new TextureRegion(idle, 200, 200, 52, 78));
    }

    public void update(float dt) {
        this.setBounds(0, 0, 52f, 78f);
        {
            // Portion for combination of b2dr and player
            this.setPosition(b2body.getPosition().x / MyGdxGame.ppm + 180, (b2body.getPosition().y * 50) * 1.5f);
            if (b2body.getPosition().y < .66f)
                setY(getY() * .67f);
            else if (b2body.getPosition().y > .68)
                setY(getY() * 1.1f);
            if (b2body.getPosition().x > 31)
                setY(getY() * .25f);
            // System.out.println(b2body.getPosition().x + " " + b2body.getPosition().y + " " + getX() + " " + getY());
        }
        this.setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            //case Fall:
            case Jump:
                region = jump.getKeyFrame(elspsedTime, true);
                break;
            case Fall:
                region = fall.getKeyFrame(elspsedTime, true);
                break;
            case Throw:
                region = granade.getKeyFrame(elspsedTime, false);
                break;
            case Shoot:
                region = shoot.getKeyFrame(elspsedTime, true);
                break;
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

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(300f / MyGdxGame.ppm,200f /MyGdxGame.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(52f/300, 78f/450);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
