package com.superpigsy.pdfeditor;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageField extends PdfField {

	String image;
	
	public float width;
	public float height;
	
	public ImageField(String image,float offsetX,float offsetY)
	{
		this.image = image;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public ImageField(String image,float offsetX,float offsetY,float width,float height)
	{
		this(image,offsetX,offsetY);
		
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(PDDocument document,PDPageContentStream contentStream) throws IOException {

		PDImageXObject pdImage = PDImageXObject.createFromFile(image, document);

		if(this.width == 0 &&  this.height == 0)
		{
			contentStream.drawImage(pdImage, this.offsetX, this.offsetY);
		}
		else
		{
			contentStream.drawImage(pdImage, this.offsetX, this.offsetY, this.width, this.height);
		}
		
	}

}
