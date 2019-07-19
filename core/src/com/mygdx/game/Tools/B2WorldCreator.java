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
            /*bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);*/
            new Ground(world,map,rect);



        }

        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            /*bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);*/
            new Ground(world,map,rect);
        }
        for(MapObject object: map.getLayers().get(1).getObjects().getByType(PolygonMapObject.class)){
            //Polyline rect =((PolylineMapObject) object).getPolyline();
            /*bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);*/
            //new Ground(world,map,rect);
            new TiledObjectUtil(world,object);


        }
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(PolygonMapObject.class)){

            /*bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);*/
            //new Ground(world,map,rect);
            new TiledObjectUtil(world,object);


        }
        //create moon_door bodies/fixtures
        // for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
        // Rectangle rect =((RectangleMapObject) object).getRectangle();
           /* bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);*/
        //  new Door(world,map,rect);


        // }
        //create bricks bodies/fixtures
        /*for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            *//*bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);*//*


        }
        //create coin bodies/fixtures
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            *//*bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2,rect.getY()+rect.getHeight()/2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2,rect.getHeight()/2);
            fdef.shape=shape;
            body.createFixture(fdef);*//*


        }
*/
    }
}
/*sniper.add(new Sniper(world, this, 390, 275));
        sniper.add(new Sniper(world, this, 520, 275));
        sniper.add(new Sniper(world, this, 665, 275));
        sniper.add(new Sniper(world, this, 750, 320));
        sniper.add(new Sniper(world, this, 883, 320));
        sniper.add(new Sniper(world, this, 900, 320));
        sniper.add(new Sniper(world, this, 1030, 320));
        sniper.add(new Sniper(world, this, 1190, 320));
        sniper.add(new Sniper(world, this, 1300, 320));
        sniper.add(new Sniper(world, this, 1480, 320));
        sniper.add(new Sniper(world, this, 1660, 320));
        sniper.add(new Sniper(world, this, 1842, 280));
        sniper.add(new Sniper(world, this, 1900, 320));
        sniper.add(new Sniper(world, this, 2150, 320));
        sniper.add(new Sniper(world, this, 2285, 320));
        sniper.add(new Sniper(world, this, 2500, 320));
        sniper.add(new Sniper(world, this, 2700, 320));
        sniper.add(new Sniper(world, this, 2885, 320));
        sniper.add(new Sniper(world, this, 3000, 320));
        sniper.add(new Sniper(world, this, 3250, 320));*/