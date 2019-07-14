package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Player extends Sprite {

    //public enum State {Walk, Shoot, Throw, Crouch, Up, Jump, Fall, Idle}
    //public State currentState;
    //public State previousState;

    public float elspsedTime;

    public World world;
    public Body player_body;

    Texture idle;
    private TextureAtlas walking;
    private TextureAtlas shooting;
    private TextureAtlas jumping;
    private TextureAtlas falling;
    private TextureAtlas throwing;
    private TextureAtlas looking_up;
    private TextureAtlas shooting_up;
    private TextureAtlas crouching;
    private TextureAtlas crouch_shooting;
    private TextureAtlas crouch_throwing;

    Animation<TextureRegion> walk;
    Animation<TextureRegion> shoot;
    Animation<TextureRegion> jump;
    Animation<TextureRegion> fall;
    Animation<TextureRegion> granade;
    Animation<TextureRegion> look_up;
    Animation<TextureRegion> shoot_up;
    Animation<TextureRegion> crouch;
    Animation<TextureRegion> crouch_shoot;
    Animation<TextureRegion> crouch_throw;

    boolean Right, In_air, Jump, Shoot, Throw, Crouch, Up, Walk, Idle, Fall;

    public Player(World world, PlayScreen screen) {

        this.world = world;
        //currentState = State.Walk;
        //previousState = State.Walk;
        elspsedTime = 0f;

        Right = true;
        Idle = true;
        Walk = false;
        In_air = false;
        Jump = false;
        Fall = false;
        Shoot = false;
        Throw = false;

        idle = new Texture(Gdx.files.internal("Sprites/Player/Sprites/Player/Walking/0000.png"));

        walking = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Walking/walking.txt"));
        shooting = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Shooting/shooting.txt"));
        jumping = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Jump/jumping.txt"));
        falling = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Jump/jumping.txt"));
        throwing = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Granade/throwing.txt"));
        looking_up = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Up/up_looking.txt"));
        shooting_up = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Up_shooting.txt"));
        crouching = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Crouch/crouching.txt"));
        crouch_shooting = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Crouch_shooting/crouch_shooting.txt"));
        crouch_throwing = new TextureAtlas(Gdx.files.internal("Sprites/Player/Spritesheets/Player/Crouch_granade/crouch_granade.txt"));

        walk = new Animation(1f/15f, walking.getRegions());
        shoot = new Animation(1f/15f, shooting.getRegions());
        jump = new Animation(1f/9f, jumping.getRegions());
        fall = new Animation(1f/9f, jumping.getRegions());
        granade = new Animation(1f/15f, throwing.getRegions());
        look_up = new Animation(1f/15f, looking_up.getRegions());
        shoot_up = new Animation(1f/15f, shooting_up.getRegions());
        crouch = new Animation(1f/15f, crouching.getRegions());
        crouch_shoot = new Animation(1f/15f, crouch_shooting.getRegions());
        crouch_throw = new Animation(1f/15f, crouch_throwing.getRegions());

        setBounds(0, 0, 52f, 78f);
        setRegion(new TextureRegion(idle));
    }

    public void update(float deltaTime) {
        setBounds(0, 0, 52f, 78f);
        setPosition(player_body.getPosition().x - getWidth(), player_body.getPosition().y - getHeight());
        setRegion(getFrame(deltaTime));
    }

    /*public  void inputHandle() {
        if(Gdx.input.isKeyPressed(Input.Keys.X))
            Shoot = true;
    }*/

    public TextureRegion getFrame(float deltaTime) {
        //currentState = getState();

        TextureRegion region;

        if(Jump) {
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
        else
            region = new TextureRegion(idle);


        /*switch (currentState) {
            //case Fall:
            case Jump:
                region = jump.getKeyFrame(elspsedTime, false);
                break;
            case Up:
                region = look_up.getKeyFrame(elspsedTime, true);
                break;
            case Crouch:
                region = crouch.getKeyFrame(elspsedTime, true);
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
            default:
                region = new TextureRegion(idle);
                break;

        }*/

        if((player_body.getLinearVelocity().x < 0 || !Right) && !region.isFlipX()) {
            region.flip(true, false);
            Right = false;
        }
        else if((player_body.getLinearVelocity().x > 0 || Right) && region.isFlipX()) {
            region.flip(true, false);
            Right = true;
        }

        //if(currentState == previousState)
            //elspsedTime += deltaTime;
        //else
            //elspsedTime = 0;

        //previousState = currentState;

        return region;

    }

    /*public State getState() {
        if(Shoot == true)
            return  State.Shoot;
        else if(Throw == true)
            return  State.Throw;
        else if(player_body.getLinearVelocity().y > 0)
            return  State.Jump;
        else if(player_body.getLinearVelocity().y < 0)
            return State.Fall;
        else if(player_body.getLinearVelocity().x != 0)
            return State.Walk;
        else
            return State.Idle;
    }*/

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(300f / MyGdxGame.ppm,320f /MyGdxGame.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        player_body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(52f/2f, 78f/2f);
        fdef.shape = shape;
        player_body.createFixture(fdef);
    }
}
