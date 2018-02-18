import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public abstract class PdfField
{
	public float offsetX;
	public float offsetY;
	

	public Color color = Color.BLACK;;
	PDDocument document;
	PDPageContentStream contentStream;

	public void setContentStream(PDPageContentStream contentStream)
	{
		this.contentStream = contentStream;
	}
	
	public void setDocument(PDDocument document) {
		this.document = document;
	}
	
	public abstract void draw() throws IOException;
}