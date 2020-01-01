package de.slothsoft.charts.test;

import de.slothsoft.charts.Area;
import de.slothsoft.charts.Chart;
import de.slothsoft.charts.Font;
import de.slothsoft.charts.GraphicContext;
import de.slothsoft.charts.PaintInstructions;

/**
 * A chart that paints all methods of the {@link GraphicContext}.
 *
 * @author Stef Schulz
 * @since 0.2.0
 */

public class AllMethodsChart extends Chart {

	public AllMethodsChart() {
		getBorder().space(0);
	}

	@Override
	protected void paintGraph(GraphicContext gc, PaintInstructions instructions) {
		final Area area = instructions.getArea();
		paintAllMethods(gc, area);
	}

	static void paintAllMethods(GraphicContext gc, final Area area) {
		gc.setColor(0xFF000000);
		gc.drawRectangle(area.copy().endX(area.getEndX() - 1).endY(area.getEndY() - 1));

		gc.setColor(0xFF2222BB);
		gc.fillRectangle(5, 10, 40, 20);

		gc.setColor(0xFF2222FF);
		gc.fillRectangle(new Area(10, 10, 40, 30));

		gc.setColor(0xFF555511);
		gc.clip(new Area(60, 10, 80, 41));
		gc.drawPolyline(new double[]{70, 90, 50, 70}, new double[]{10, 40, 40, 10});
		gc.clip(null);

		gc.setColor(0xFF115511);
		gc.drawLine(60, 50, 80, 50);

		gc.setColor(0xFF770011);
		gc.translate(60, 60);
		gc.fillPolygon(new double[]{0, 20, 10}, new double[]{0, 0, 10});
		gc.translate(-60, -60);

		gc.setColor(0xFFABCDEF);
		gc.setFont(Font.NORMAL);
		gc.drawText(5, 40, "ABCDE");

		gc.setColor(0xFFFEDCBA);
		gc.setFont(Font.TITLE);
		gc.drawText(5, 55, "FGH");

		gc.setColor(0xFFFFFF00);
		gc.fillOval(10, 80, 60, 30);
		gc.setColor(0xFF000000);
		gc.fillArc(10, 80, 60, 30, -45, -90);

		final int steps = 10;
		final double height = 10;
		for (int i = 0; i < steps; i++) {
			final int colorWithAlpha = 0x000000 | ((255 * (i + 1) / steps) << 24);
			gc.setColor(colorWithAlpha);
			gc.fillRectangle(100, 10 + i * height, 20, height);
		}
	}
}