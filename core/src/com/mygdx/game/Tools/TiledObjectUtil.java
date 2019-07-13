package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.MyGdxGame;

public class TiledObjectUtil {

    public  TiledObjectUtil(World world, MapObject object){
            Shape shape;
            shape = createPolyline((PolygonMapObject) object);
            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);
            body.createFixture(shape,1.0f);
            shape.dispose();
    }

    private static ChainShape createPolyline(PolygonMapObject polyline){

        float [] vertices = polyline.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];
        for(int i=0; i< worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[i*2]/ MyGdxGame.ppm, vertices[i*2 + 1]/ MyGdxGame.ppm);

            //System.out.println(worldVertices[i]);
        }

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(worldVertices);

        return chainShape;
    }
}
