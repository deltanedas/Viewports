package viewports;

import arc.*;
import arc.graphics.*;
import arc.util.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.ui.dialogs.*;

public class ViewportRenderer extends Renderer {
	public ViewportRenderer() {
		Renderer old = Vars.renderer;
		// replace ClientLauncher's reference to the old renderer
		ApplicationListener[] modules = Reflect.get(ApplicationCore.class, Vars.platform, "modules");
		for (int i = 0; i < modules.length; i++) {
			if (modules[i] == old) {
				modules[i] = this;
				break;
			}
		}

		init();

		// replace PlanetDialog's reference to old renderer.planets
//		Reflect.set(PlanetDialog.class, Vars.ui.planet, "planets", planets);

		// assert dominance
		old.dispose();
	}

	@Override
	public void update() {
		int realW = Core.graphics.getWidth(), realH = Core.graphics.getHeight();
		for (Viewport viewport : Viewports.all) {
			viewport.beginDraw();
			super.update();
			viewport.endDraw();
		}

		Viewport.setWindowSize(realW, realH);
	}
}
