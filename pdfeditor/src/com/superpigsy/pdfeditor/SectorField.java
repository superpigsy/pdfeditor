package com.superpigsy.pdfeditor;
import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
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
	
	public SectorField(float offsetX, float offsetY, float radius, float targetAngle, Color color) {

		this(offsetX,offsetY,radius,color);
		this.targetAngle = targetAngle;
		this.startAngle = 90;
	}
	
	public SectorField(float offsetX, float offsetY, float radius, float startAngle, float targetAngle, Color color) {

		this(offsetX,offsetY,radius,color);
		this.targetAngle = targetAngle;
		this.startAngle = startAngle;
	}

	@Override
	public void draw(PDDocument document,PDPageContentStream contentStream) throws IOException {

		if (targetAngle - startAngle >= 360) {
		
			final float k = 0.552284749831f;
			contentStream.setNonStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
			contentStream.moveTo(offsetX - radius, offsetY);
			contentStream.curveTo(offsetX - radius, offsetY + k * radius, offsetX - k * radius, offsetY + radius, offsetX, offsetY + radius);

			contentStream.curveTo(offsetX + k * radius, offsetY + radius, offsetX + radius, offsetY + k * radius, offsetX + radius, offsetY);
			contentStream.curveTo(offsetX + radius, offsetY - k * radius, offsetX + k * radius, offsetY - radius, offsetX, offsetY - radius);
			contentStream.curveTo(offsetX - k * radius, offsetY - radius, offsetX - radius, offsetY - k * radius, offsetX - radius, offsetY);
			contentStream.fill();
			
		} else {

			targetAngle *= -1;
		
			double r = this.radius;
			
			contentStream.moveTo(offsetX, offsetY);

			contentStream.setNonStrokingColor(color.getRed(), color.getGreen(), color.getBlue());
			targetAngle = (Math.abs(targetAngle) > 360) ? 360 : targetAngle;

			double n = Math.ceil(Math.abs(targetAngle) / 45);

			double angleA = targetAngle / n;

			angleA = angleA * Math.PI / 180;

			startAngle = startAngle * Math.PI / 180;

			contentStream.lineTo((float) (offsetX + r * Math.cos(startAngle)), (float) (offsetY + r * Math.sin(startAngle)));

			for (int i = 1; i <= n; i++) {
				startAngle += angleA;

				double angleMid = startAngle - angleA / 2;

				double bx = offsetX + r / Math.cos(angleA / 2) * Math.cos(angleMid);

				double by = offsetY + r / Math.cos(angleA / 2) * Math.sin(angleMid);

				double cx = offsetX + r * Math.cos(startAngle);

				double cy = offsetY + r * Math.sin(startAngle);

				contentStream.curveTo((float) bx, (float) by, (float) cx, (float) cy, (float) cx, (float) cy);
			}

			if (targetAngle != 360) {
				contentStream.lineTo(offsetX, offsetY);
			}

			contentStream.fill();
		}
	}
}
