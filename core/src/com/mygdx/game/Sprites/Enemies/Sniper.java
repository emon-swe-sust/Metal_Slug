package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;

public class Sniper extends Sprite {

    private Texture sniper;  //frame size -->> 48 x 47

    private Animation<TextureRegion> running;
    //private Animation<TextureRegion> aiming;
    private Animation<TextureRegion> shooting;
    private Animation<TextureRegion> dying;

    public World world;
    public Body sniper_body;
    private PlayScreen screen;

    float x, y;

    float engage_distance;
    float shooting_distance;
    float elspsedTime;

    boolean isDead;

    public Sniper(World world, PlayScreen screen, float x, float y) {

        this.world = world;
        this.screen = screen;

        this.x = x;
        this.y = y;

        engage_distance = 220f;
        shooting_distance = 100f;

        elspsedTime = 0f;

        isDead = false;

        sniper = new Texture("Sprites/Enemies/Sniper/sniper(2).png");

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=0; i<12; i++)
            frames.add(new TextureRegion(sniper, i*48, 47*2, 48, 47));
        running = new Animation<TextureRegion>(1f/15f, frames);
        frames.clear();

        /*for(int i=0; i<5; i++)
            frames.add(new TextureRegion(sniper, i*48, 47*2, 48 ,47));
        aiming = new Animation<TextureRegion>(1f/8f, frames);
        frames.clear();*/

        for(int i=0; i<13; i++)
            frames.add(new TextureRegion(sniper, i*48, 47, 48, 47));
        shooting = new Animation<TextureRegion>(1f/10f, frames);
        frames.clear();

        for(int i=0; i<12; i++)
            frames.add(new TextureRegion(sniper, i*48, 0, 48, 47));
        dying = new Animation<TextureRegion>(1f/15f, frames);
        frames.clear();

        defineSniper();

        setBounds(0, 0, 48f, 47f);
        setRegion(new TextureRegion(sniper, 0, 47*2, 48, 47));

    }

    public void update(float dt, float player_body_x) {

        if(!isDead) {
            if (sniper_body.getPosition().x - player_body_x < engage_distance) {
                if (sniper_body.getPosition().x - player_body_x < shooting_distance) {
                    sniper_body.setLinearVelocity(0, 0);
                    setRegion(shooting.getKeyFrame(elspsedTime, true));
                    if (shooting.getKeyFrameIndex(elspsedTime) == 5) {
                        //shoot 1 bullet
                        shoot();
                    }
                } else {
                    setRegion(running.getKeyFrame(elspsedTime, true));
                    sniper_body.applyAngularImpulse(-2f, true);
                    //setPosition(((sniper_body.getPosition().x * MyGdxGame.ppm) - 48f/2f),((sniper_body.getPosition().y * MyGdxGame.ppm) - 47f/2f));
                    setPosition((sniper_body.getPosition().x   + getWidth() + 120), (sniper_body.getPosition().y * MyGdxGame.ppm) - getHeight()/2 + 20);
                }
            }
            elspsedTime += dt;
        }
        else {
            setRegion(dying.getKeyFrame(elspsedTime, false));
            if(dying.getKeyFrameIndex(elspsedTime) == 11) {
                //remove body
            }
            elspsedTime += dt;
        }
    }

    public void shoot() {

    }

    public void defineSniper() {

        BodyDef bdef = new BodyDef();
        bdef.position.set((x + 48f/2f) / MyGdxGame.ppm, (y + 47f/2f) / MyGdxGame.ppm);
        bdef.type = BodyDef.BodyType.DynamicBody;
        sniper_body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = MyGdxGame.Enemy_Bit;
        fdef.filter.maskBits = MyGdxGame.Player_bit | MyGdxGame.Bullet_bit | MyGdxGame.Ground_bit | MyGdxGame.Object_Bit;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(40/2 / MyGdxGame.ppm, 40/2 / MyGdxGame.ppm);
        fdef.shape = shape;
        sniper_body.createFixture(fdef);
    }

}
