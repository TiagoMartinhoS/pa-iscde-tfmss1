package outline;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import core.model.WindowModel;
import core.model.WindowModel.WindowListener;
import core.services.WindowService;

public class OutlineService implements WindowService {
	
	private WindowModel model;
	private Canvas canvas;

	@Override
	public String getName() {
		return "Outline";
	}
	
	private WindowListener listener = new WindowListener() {
		@Override
		public void positionChanged(int x, int y) {
			update(x, y);
		}
	};

	@Override
	public Component createContent(final WindowModel model) {
		this.model = model;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		canvas = new Canvas() {
			public void paint(Graphics g) {
					g.setColor(new Color(255, 0, 0));
					double x = (model.getX() * 300) / screen.getWidth();
					double y = (model.getY() * 200) / screen.getHeight();
					g.drawOval((int) x, (int) y, 4, 4);
			}
		};
		canvas.setSize(300, 200);
		canvas.setBackground(new Color(255, 255, 255));
		canvas.repaint(); // refresh drawing
		model.addListener(listener);
		return canvas;
	}

	private void update(int x, int y) {
		canvas.repaint(); // refresh drawing
		canvas.getParent().validate();
		
	}
	
	public void shutdown() {
		model.removeListener(listener);
	}

}
