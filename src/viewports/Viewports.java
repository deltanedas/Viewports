package viewports;

import arc.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.game.*;
import mindustry.mod.*;

import static mindustry.game.EventType.*;

public class Viewports extends Mod {
	public static Seq<Viewport> all = Seq.with(new Viewport() {
		{
			camera.resize(320, 480);
		}

		@Override
		protected void update() {
			if (Vars.player != null && Vars.player.unit() != null) {
				cameraPos.set(Vars.player.unit());
			}

			viewportPos.set(0, Mathf.absin(10, 100));
//			size.set(Core.graphics.getWidth() / 2, Core.graphics.getHeight() / 2);
			size.set(320, 480);
		}
	});

	public Viewports() {
		Events.on(ClientLoadEvent.class, e -> load());
	}

	public static void load() {
		// make sure other mods haven't touched it, this isnt a mixin!
		if (Vars.renderer.getClass() == Renderer.class) {
			Vars.renderer = new ViewportRenderer();
			Log.warn("Enabled viewport renderer");
		} else {
			Log.err("Vars.renderer was replaced already!");
		}
	}
}
