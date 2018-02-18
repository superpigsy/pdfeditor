import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class SectorField extends PdfField {

	public float targetAngle;
	public float radius;

	public double startAngle = 90;
	
	public SectorField(float offsetX, float offsetY, float radius, Color color)  
	{
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.targetAngle = 360;
		this.startAngle = 0;
		this.color = color;
		this.radius = radius;
	}
	
	public SectorField(float offsetX, float offsetY, float radius, float startAngle, float targetAngle, Color color) {

		this(offsetX,offsetY,radius,color);
		this.targetAngle = targetAngle;
		this.startAngle = startAngle;
	
	}

	@Override
	public void draw() throws IOException {

		if (targetAngle - startAngle >= 360) {

			final float k = 0.552284749831f;
			contentStream.setNonStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
			contentStream.moveTo(offsetX - radius, offsetX);
			contentStream.curveTo(offsetX - radius, offsetX + k * radius, offsetX - k * radius, offsetX + radius,
					offsetX, offsetX + radius);

			contentStream.curveTo(offsetX + k * radius, offsetX + radius, offsetX + radius, offsetX + k * radius,
					offsetX + radius, offsetX);
			contentStream.curveTo(offsetX + radius, offsetX - k * radius, offsetX + k * radius, offsetX - radius,
					offsetX, offsetX - radius);
			contentStream.curveTo(offsetX - k * radius, offsetX - radius, offsetX - radius, offsetX - k * radius,
					offsetX - radius, offsetX);
			contentStream.fill();
		} else {

			targetAngle *= -1;
			contentStream.moveTo(super.offsetX, super.offsetX);

			contentStream.setNonStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
			targetAngle = (Math.abs(targetAngle) > 360) ? 360 : targetAngle;

			double n = Math.ceil(Math.abs(targetAngle) / 45);

			double angleA = targetAngle / n;

			angleA = angleA * Math.PI / 180;

			startAngle = startAngle * Math.PI / 180;

			contentStream.lineTo((float) (offsetX + radius * Math.cos(startAngle)),
					(float) (offsetY + radius * Math.sin(startAngle)));

			for (int i = 1; i <= n; i++) {
				startAngle += angleA;

				double angleMid = startAngle - angleA / 2;

				double bx = offsetX + radius / Math.cos(angleA / 2) * Math.cos(angleMid);

				double by = offsetY + radius / Math.cos(angleA / 2) * Math.sin(angleMid);

				double cx = offsetX + radius * Math.cos(startAngle);

				double cy = offsetY + radius * Math.sin(startAngle);

				contentStream.curveTo((float) bx, (float) by, (float) cx, (float) cy, (float) cx, (float) cy);
			}

			if (targetAngle != 360) {
				contentStream.lineTo(offsetX, offsetY);
			}

			contentStream.fill();
		}
	}

}
