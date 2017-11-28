package com.mobike.library;


import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Random;

/**
 * Created by kimi on 2017/7/8 0008.
 * Email: 24750@163.com
 */

public class Mobike {

    public static final String TAG = Mobike.class.getSimpleName();

    private World world;

    public World getWorld() {
        return world;
    }

    //dt 更新引擎的间隔时间
    //velocityIterations 计算速度
    //positionIterations 迭代的次数
    private float dt = 1f / 50f;
    private int velocityIterations = 2;
    private int positionIterations = 5;
    private float friction = 0.3f, density = 0.5f, restitution = 0.3f, ratio = 50;
    private int width, height;
    private boolean enable = true;
    private final Random random = new Random();

    private ViewGroup mViewgroup;

    public Mobike(ViewGroup viewgroup) {
        this.mViewgroup = viewgroup;
        density = viewgroup.getContext().getResources().getDisplayMetrics().density;
    }

    public void onSizeChanged(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void onDraw(Canvas canvas) {
        if (!enable) {
            return;
        }
        world.step(dt, velocityIterations, positionIterations);
        int childCount = mViewgroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                view.setX(metersToPixels(body.getPosition().x) - view.getWidth() / 2);
                view.setY(metersToPixels(body.getPosition().y) - view.getHeight() / 2);
                view.setRotation(radiansToDegrees(body.getAngle() % 360));
            }
        }
        mViewgroup.invalidate();
    }

    public void onLayout(boolean changed) {
        createWorld(changed);
    }

    public void onStart() {
        setEnable(true);
    }

    public void onStop() {
        setEnable(false);
    }

    public void update() {
        world = null;
        onLayout(true);
    }


    private void createWorld(boolean changed) {
        if (world == null) {
            world = new World(new Vec2(0, 10));
            createTopAndBottomBounds();
            createLeftAndRightBounds();

            double h1 = 30 / 245.0001 * height;
            double h2 = h1 * 4 / 6;

            double mWidth1 = width / 16 * 4;
            double mWidth2 = width / 32 * 9;//宽一点
            double mWidth3 = width / 16 * 3;//小一点
            double mWidth4 = width / 16 * 2;//小一点
            double mWidth5 = width / 16;//小一点
            createOther(0, 0, width, (int) h1);//挡住瓶盖部分
            for (int i = 0; i < 4; i++) {
                createOther(0, (int) (h2 * (i + 1)), (int) (mWidth2 - (mWidth1) / 5 * i), (int) h1);//挡住瓶盖部分
            }
            for (int i = 0; i < 4; i++) {
                createOther(width, (int) (h2 * (i + 1)), (int) (mWidth2 - (mWidth1) / 5 * i), (int) h1);//挡住瓶盖部分
            }
        }
        int childCount = mViewgroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body == null || changed) {
                createBody(world, view);
            }
        }
    }

    /**
     * 清除
     */
    public void clearBody() {
        for (int i = 0; i < mViewgroup.getChildCount(); i++) {
            Log.i("zzzzzzzzz","还在循环"+i);
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                world.destroyBody(body);//世界中移除
                view.setTag(R.id.mobike_body_tag,null);
                mViewgroup.removeView(view);//界面中移除
                i = -1;
            }
        }
    }

    public void addBody() {
        int childCount = mViewgroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mViewgroup.getChildAt(i);
//            createBody(world, view);//向世界中添加
        }
    }


    private void createBody(World world, View view) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.setType(BodyType.DYNAMIC);

        bodyDef.position.set(pixelsToMeters(view.getX() + view.getWidth() / 2), pixelsToMeters( view.getHeight()/2 ));
        Shape shape = null;
        Boolean isCircle = (Boolean) view.getTag(R.id.mobike_view_circle_tag);
        if (isCircle != null && isCircle) {
            shape = createCircleShape(view);
        } else {
            shape = createPolygonShape(view);
        }
        FixtureDef fixture = new FixtureDef();
        fixture.setShape(shape);
        fixture.friction = friction;
        fixture.restitution = restitution;
        fixture.density = density;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixture);
        view.setTag(R.id.mobike_body_tag, body);
        body.setLinearVelocity(new Vec2(random.nextFloat(), random.nextFloat()));
    }

    private Shape createCircleShape(View view) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(pixelsToMeters(view.getWidth() / 2));
        return circleShape;
    }

    private Shape createPolygonShape(View view) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(pixelsToMeters(view.getWidth() / 2), pixelsToMeters(view.getHeight() / 2));
        return polygonShape;
    }

    private void createTopAndBottomBounds() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();
        float boxWidth = pixelsToMeters(width);
        float boxHeight = pixelsToMeters(ratio);
        box.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        bodyDef.position.set(0, -boxHeight);
        Body topBody = world.createBody(bodyDef);
        topBody.createFixture(fixtureDef);

        bodyDef.position.set(0, pixelsToMeters(height) + boxHeight);
        Body bottomBody = world.createBody(bodyDef);
        bottomBody.createFixture(fixtureDef);
//

    }

    /**
     * 起点坐标 长宽
     */
    private void createOther(int a, int b, int c, int d) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.setType(BodyType.STATIC);
        bodyDef.position.set(pixelsToMeters(a), pixelsToMeters(b));//位置坐标
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(pixelsToMeters(c), pixelsToMeters(d));
        FixtureDef fixture = new FixtureDef();

        fixture.setShape(polygonShape);
//        fixture.friction = friction;
//        fixture.restitution = restitution;
//        fixture.density = density;
        fixture.density = density;
        fixture.friction = friction;
        fixture.restitution = 0;
        Body body = world.createBody(bodyDef);
        body.createFixture(fixture);
//        body.setLinearVelocity(new Vec2(random.nextFloat(), random.nextFloat()));
    }

    private void createLeftAndRightBounds() {
        float boxWidth = pixelsToMeters(ratio);
        float boxHeight = pixelsToMeters(height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();
        box.setAsBox(boxWidth, boxHeight);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        bodyDef.position.set(-boxWidth, boxHeight);
        Body leftBody = world.createBody(bodyDef);
        leftBody.createFixture(fixtureDef);


        bodyDef.position.set(pixelsToMeters(width) + boxWidth, 0);
        Body rightBody = world.createBody(bodyDef);
        rightBody.createFixture(fixtureDef);


    }


    private float radiansToDegrees(float radians) {
        return radians / 3.14f * 180f;
    }

    private float degreesToRadians(float degrees) {
        return (degrees / 180f) * 3.14f;
    }

    public float metersToPixels(float meters) {
        return meters * ratio;
    }

    public float pixelsToMeters(float pixels) {
        return pixels / ratio;
    }

    public void random() {
        int childCount = mViewgroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Vec2 impulse = new Vec2(random.nextInt(1000) - 1000, random.nextInt(1000) - 1000);
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                body.applyLinearImpulse(impulse, body.getPosition(), true);
            }
        }
    }

    public void onSensorChanged(float x, float y) {
        world.setGravity(new Vec2(x, y));
        int childCount = mViewgroup.getChildCount();
//        world.
        for (int i = 0; i < childCount; i++) {
            Vec2 impulse = new Vec2(x / 4, y / 4);
            View view = mViewgroup.getChildAt(i);
            Body body = (Body) view.getTag(R.id.mobike_body_tag);
            if (body != null) {
                body.applyLinearImpulse(impulse, body.getPosition(), true);
            }
        }
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        if (friction >= 0) {
            this.friction = friction;
        }
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        if (density >= 0) {
            this.density = density;
        }
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        if (restitution >= 0) {
            this.restitution = restitution;
        }
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        if (ratio >= 0) {
            this.ratio = ratio;
        }
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        mViewgroup.invalidate();
    }
}
