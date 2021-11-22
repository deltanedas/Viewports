const viewports = {
	Viewport: null,
	Viewports: null,
	Renderer: null
};

const Class = java.lang.Class;

const mods = Vars.mods.list()
for (var i = 0; i < mods.size; i++) {
	var mod = mods.get(i);
	if (mod.name == "viewports") {
		// load every class in the viewports package
		const loader = mod.loader;
		for (var name in viewports) {
			const path = "viewports." + name;
			viewports[name] = Class.forName(path, true, loader)
		}

		print("Created viewports JS bindings");
		break;
	}
}

module.exports = viewports;
global.viewports = viewports;
