package viewports;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Reflect;
import arc.util.Tmp;

public abstract class Viewport {
	// camera for the viewport
	public Camera camera = new Camera();
	// set camera's position (world units)
	public Vec2 cameraPos = camera.position,
		// set viewport's position, size (pixels)
		viewportPos = new Vec2(), size = new Vec2();

	public Viewport() {
		// default to fullscreen, will not be updated when resized!
		size.set(Core.graphics.getWidth(), Core.graphics.getHeight());
		// TODO: resize event?
	}

	private Mat old;
	public void beginDraw() {
		size.set(camera.width, camera.height);

		update();

		setWindowSize((int) size.x, (int) size.y);
		camera.resize(size.x, size.y);
		Core.camera = camera;

		old = Draw.trans();
		Mat mat = Tmp.m1.set(old);
		mat.setToScaling(size.x / Core.graphics.getWidth(),
			size.y / Core.graphics.getHeight());
		Tmp.m2.setToTranslation(viewportPos.x, viewportPos.y);
//		mat.setToTranslation(viewportPos.x, viewportPos.y);
		mat.mulLeft(Tmp.m2);
		Draw.trans(mat);
		camera.resize(size.x / 64, size.y / 64);
	}

	public void endDraw() {
		Draw.trans(old);
	}

	// set pos/size/etc.
	abstract protected void update();

	public static void setWindowSize(int w, int h) {
		// TODO: remove gross hack
		Reflect.invoke((Object) Core.graphics, "updateSize",
			new Object[] {
				w, h
			}, int.class, int.class);
	}
}
