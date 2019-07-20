package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Sprites.Ground;


public class B2WorldCreator {
    public B2WorldCreator(World world , TiledMap map){

        BodyDef bdef =new BodyDef();
        PolygonShape shape =new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            new Ground(world,map,rect);
        }
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            new Ground(world,map,rect);
        }
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(PolygonMapObject.class)){
            new TiledObjectUtil(world,object);
        }
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class)){
            new TiledObjectUtil(world,object);
        }
    }
}