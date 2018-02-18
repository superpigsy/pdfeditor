
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.fontbox.cff.CFFFont;
import org.apache.fontbox.cff.CFFParser;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;

public class PdfEditor {

	public static void main(String[] args) {

		ArrayList<PdfField> textFields = new ArrayList<PdfField>();

		try {

			PDDocument document = new PDDocument(); 
			
			PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0),
					PDPageContentStream.AppendMode.APPEND, true);

			textFields.add(new TextField("ABC",10,10));
			textFields.add(new SectorField(100,100,50,Color.RED));
			textFields.add(new SectorField(10,200,50,0,90,Color.RED));
			
			for(int i=0;i<textFields.size();i++)
			{
				PdfField field = textFields.get(i);
				field.setContentStream(contentStream);
				field.draw();
			}

			
			contentStream.close();
			// //Closing the content stream

			AccessPermission permission = document.getCurrentAccessPermission();

			permission.setCanAssembleDocument(false);
			// Set if the user can insert/rotate/delete pages.
			permission.setCanExtractContent(false);
			// Set if the user can extract content from the document.
			permission.setCanExtractForAccessibility(false);
			// Set if the user can extract content from the document for
			// accessibility purposes.
			permission.setCanFillInForm(false);
			// Set if the user can fill in interactive forms.
			permission.setCanModify(false);
			// Set if the user can modify the document.
			permission.setCanModifyAnnotations(false);
			// Set if the user can modify annotations.
			permission.setCanPrint(false);
			// Set if the user can print.
			permission.setCanPrintDegraded(false);
			// Set if the user can print the document in a degraded format.
			permission.setReadOnly();
			// Locks the access permission read only (ie, the setters will have
			// no effects).

			// Saving the document
			document.save(new File("pdfeditor.pdf"));

			// Closing the document
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	static CFFFont loadCFFFont(String file) throws IOException {
		CFFFont cff = null;
		FileInputStream input = new FileInputStream(file);
		byte[] bytes = IOUtils.toByteArray(input);
		CFFParser cffParser = new CFFParser();
		cff = cffParser.parse(bytes).get(0);
		return cff;
	}

}
