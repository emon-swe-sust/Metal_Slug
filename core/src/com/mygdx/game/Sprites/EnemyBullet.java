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
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;

public class EnemyBullet extends Sprite {
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
    private boolean bpos;

    public EnemyBullet(World world,PlayScreen screen,float posx,float posy, float velocity){
        this.screen = screen;
        //this.right = right;
        this.velocity = velocity;
        this.posx = posx;
        this.posy = posy;
        this.world = world;
        pos = false;
        noob = true;
        bpos = false;

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
            setPosition(b2bulletbody.getPosition().x - getWidth() / 2, b2bulletbody.getPosition().y - getHeight() / 2f);
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
        shape.setRadius(3);

        fdef.filter.categoryBits = MyGdxGame.ENEMYBULLET_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.PLAYER_BIT;
        fdef.shape = shape;
        fdef.restitution = 0;
        fdef.friction = 0;

        b2bulletbody.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if (! destroyed || statetime < 1) {
            super.draw(batch);
        }
    }

    public  void  hitenemy(){
        //System.out.println("BAAL");
        destroyed = false;
        settodestroy = true;
    }
}