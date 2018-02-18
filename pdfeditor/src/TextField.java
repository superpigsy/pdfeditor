import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;


public class TextField extends PdfField
{
	public String image;
	public String font;
	public int font_size;
	public String text;
	public float offsetX;
	public float offsetY;
	
	public float width;
	public Alignments alignment;
	
	public enum Alignments
	{
		Left,
		Middle,
		Right
	}
	

	public TextField(String image,float x,float y)
	{
		this.image = image;
		this.offsetX = x;
		this.offsetY = y;
	}
	
	public TextField(float width,String font,int font_size,String text,float x,float y)
	{
		this(font,font_size,text,x,y);
		this.width = width;
	}
	
	public TextField(String font,int font_size,String text,float x,float y)
	{
		this.font = font;
		this.font_size = font_size;
		this.text = text;
		this.offsetX = x;
		this.offsetY = y;
		this.alignment = Alignments.Left;
		

	}
	
	public TextField(String font,int font_size,String text,float x,float y,Color color)
	{
		this(font,font_size,text,x,y);
		this.color = color;
	}
	
	public TextField(String font,int font_size,String text,float x,float y,Color color,Alignments alignment)
	{
		this(font,font_size,text,x,y,color);
		this.alignment = alignment;
	}
	
	public TextField(String font,int font_size,String text,float x,float y,Alignments alignment)
	{
		this(font,font_size,text,x,y);
		this.alignment = alignment;
	}
	
	
	@Override
	public void draw() throws IOException
	{
		PDFont pdfont = PDType0Font.load(document, new File(font));
		ArrayList<String> texts = new ArrayList<String>();
		if(width != 0)
		{
			int textlength = text.length();
			
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < textlength; i++)
			{
				sb.append(text.substring(i,i+1));
				float titleWidth = pdfont.getStringWidth(sb.toString()) / 1000 * font_size;
				if(titleWidth > width)
				{
					sb.deleteCharAt(sb.length()-1);
					
					texts.add(sb.toString());
					
					i--;
					sb.setLength(0);
				}
				else if(titleWidth == width)
				{
					texts.add(sb.toString());
					sb.setLength(0);
				}
				else if(titleWidth < width)
				{
					if(i + 1 == textlength)
					{
						texts.add(sb.toString());
						sb.setLength(0);
					}
				}
			}
		}
		else
		{
			texts.add(text);
		}
		contentStream.setFont(pdfont, font_size);
		contentStream.setNonStrokingColor(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
		
		float lineHeight = pdfont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * font_size;
		for(int i = 0; i < texts.size(); i++)
		{
			contentStream.beginText();

			
			String text = texts.get(i);
			if (alignment == TextField.Alignments.Middle) {
				float titleWidth = pdfont.getStringWidth(text) / 1000 * font_size;
				contentStream.newLineAtOffset(offsetX - titleWidth / 2, offsetY);
				
			} else {
				contentStream.newLineAtOffset(offsetX, offsetY - i * lineHeight);
			}
			contentStream.showText(text);
			contentStream.endText();
		}
	}
}
