package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Bullet extends Sprite {
    World world;
    public Texture texture;
    public TextureRegion textureRegion;
    public //boolean right;
    float velocity;
    public boolean settodestroy;
    public boolean destroyed;
    public boolean noob;
    public float statetime;

    public float posx,posy;
    public boolean remove = false;
    public PlayScreen screen;
    public Body b2bulletbody;

    private float skel = 76;
    private float time = 1.99f;
    private boolean pos;

    public Bullet(World world,PlayScreen screen,float posx,float posy, float velocity){
        this.screen = screen;
        //this.right = right;
        this.velocity = velocity;
        this.posx = posx;
        this.posy = posy;
        this.world = world;
        pos = false;
        noob = true;

        texture = new Texture("Sprites/Player/Spritesheets/temp/bullet.png");
        textureRegion = new TextureRegion(texture);
        statetime = 0;

        definebullet();

        setBounds(0,0,10f,10f);
        setRegion(textureRegion);
        b2bulletbody.setLinearVelocity(velocity, 0);

        settodestroy = false;
        destroyed = false;
    }

    public void update(float dt) {
        statetime+=dt;
        if (settodestroy && !destroyed) {
            world.destroyBody(b2bulletbody);
            destroyed = true;
            setRegion(new Texture("Sprites/Player/Spritesheets/temp/EmptyBullet.png"));
            statetime = 0;
        } else if (!destroyed) {
            //setPosition(b2bulletbody.getPosition().x - getWidth() / 2, b2bulletbody.getPosition().y - getHeight() / 2f);
            // Portion for combination of b2dr and player

            //System.out.println(getX() + " " + (b2bulletbody.getPosition().x - getWidth()/2) + " " + getY() + (b2bulletbody.getPosition().y - getHeight()/2)
            // setRegion(textureRegion);

            //if(!right) {
                //setPosition(getX() - 1f, getY());
                //b2bulletbody.setLinearVelocity(velocity, 0);
            // setPosition((b2bulletbody.getPosition().x * (100) - getWidth()/2 )/(b2bulletbody.getPosition().x), (b2bulletbody.getPosition().y * MyGdxGame.ppm) - getHeight()/2);
                if(noob){
                   setX(getX() + 190);
                   noob = false;
                }
                setX(getX()+2.05f);
                setY((b2bulletbody.getPosition().y * MyGdxGame.ppm) - getHeight()/2);
                System.out.println(" -- > " +b2bulletbody.getPosition().x);
//                float baal = (b2bulletbody.getPosition().y * MyGdxGame.ppm) - getHeight()/2;
//                float chal = b2bulletbody.getPosition().x * 93;
//                if(pos){
//                    setPosition(chal-skel*100,baal);
//                }

           // System.out.println("---> " + b2bulletbody.getPosition().x);
            }
            //else
                //setPosition(getX() + 1f,getY());
           // System.out.println(b2bulletbody.getPosition().x + " " + getX());
            /*f (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                System.out.println("bal");
                if (!right)
                    b2bulletbody.setTransform(b2bulletbody, 0);
                else
                    b2bulletbody.applyLinearImpulse(new Vector2(100f, 0), b2bulletbody.getWorldCenter(), true);
            }*/
        //}

        if(b2bulletbody.getPosition().x > 3000|| b2bulletbody.getPosition().x < 0){
            System.out.println("--> " + b2bulletbody.getPosition().x);
            remove = true;
            settodestroy = true;
        }
    }

    public void definebullet(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(posx,posy);
        bdef.bullet = true;
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = 0;

        b2bulletbody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4/ MyGdxGame.ppm);

        fdef.shape = shape;
        fdef.restitution = 0;
        fdef.friction = 0;
        fdef.filter.categoryBits = MyGdxGame.Enemy_Bit;
        fdef.filter.maskBits = MyGdxGame.Enemy_Bit;
        b2bulletbody.createFixture(fdef);
    }

    public void draw(Batch batch){
        if (! destroyed || statetime < 1) {
            batch.begin();
            super.draw(batch);
            batch.end();
        }
    }

    public  void  hitenemy(){
        settodestroy = true;
    }
}