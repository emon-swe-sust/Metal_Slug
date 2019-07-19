package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Ground;

public class TiledObjectUtil {

    public  TiledObjectUtil(World world, MapObject object){
        Shape shape;
        shape = createPolyline((PolygonMapObject) object);
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = MyGdxGame.GROUND_BIT;
        fdef.filter.maskBits = MyGdxGame.BULLET_BIT | MyGdxGame.PLAYER_BIT | MyGdxGame.ENEMYBULLET_BIT | MyGdxGame.ENEMY_BIT ;
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    private static ChainShape createPolyline(PolygonMapObject polyline){

        float [] vertices = polyline.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];
        for(int i=0; i< worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[i*2], vertices[i*2 + 1]);

            //System.out.println(worldVertices[i]);
        }

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);
        return chainShape;
    }
}
