package com.superpigsy.pdfeditor;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class LineField extends PdfField{

	@Override
	public void draw(PDDocument document, PDPageContentStream contentStream) throws IOException {
		// TODO Auto-generated method stub
		 // 矩形を指定
		contentStream.addRect(200f, 400f, 100f, 150f);
       
       // 線の色を指定
		contentStream.setStrokingColor(Color.BLACK);
       // 線の太さを指定
		contentStream.setLineWidth(10f);
       
       // 線を描画
		contentStream.stroke();
	}


}
