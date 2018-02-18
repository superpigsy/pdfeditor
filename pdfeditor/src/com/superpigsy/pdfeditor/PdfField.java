package com.superpigsy.pdfeditor;
import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public abstract class PdfField
{
	public float offsetX;
	public float offsetY;
	

	public Color color = Color.BLACK;
	
	PDDocument document;
	PDPageContentStream contentStream;
	
	public abstract void draw(PDDocument document,PDPageContentStream contentStream) throws IOException;
}