package outline;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import core.services.WindowService;

public class Activator implements BundleActivator {

	@NotNull
	private OutlineService service;

	public Activator() {
	}
	
	public void start(BundleContext bundleContext) throws Exception {
		service = new OutlineService();
		bundleContext.registerService(WindowService.class, service, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		service.shutdown();
	}

}
