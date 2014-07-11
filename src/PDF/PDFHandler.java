/*
 * RiftSKS ist ein Programm zum führen eines Raidkaders mit der Absicht
 * das SKS-Verfahren bei der Lootverteilung anzuwenden.
 * Copyright (C) 2013  Dirk Evers
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package PDF;

import Data.*;
import Data.Player.RaidState;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PDFHandler ist eine Klasse zum verarbeiten der Raidinformationen in ein
 * PDF-Format.
 * @author Dirk
 */
public class PDFHandler {
    private static final Logger logger = LogManager.getRootLogger();
        SimpleDateFormat raidDateFormat = new SimpleDateFormat("E, dd.MM.yyyy"); 
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        BaseColor lightGray = new BaseColor(230,230,230);
        Font font;
        Book book;
    
    /**
     * Konstruktor des PDFHandlers.
     * @param book Kaderbuch mit allen Informationen zum Kader und zu den Raids.
     */
    public PDFHandler(Book book){
        this.book = book;
    }
    
    /**
     *
     */
    public void printPDF(){
        logger.info("Print PDF.");
        try {
        float[] w = {0.35f,1,1,1,1,0.5f};
            File sksDir = new File("SKS");
            if(!sksDir.exists()||!sksDir.isDirectory()){
                sksDir.delete();
                sksDir.mkdir();
            }
            File file = new File(sksDir.getAbsolutePath()+"/sks("+currentDateFormat.format(book.getActiveRaid().getDate())+").pdf");
            Document document=new Document(); 
            if(file.exists()){
                file.delete();
            }
            PdfWriter.getInstance(document,new FileOutputStream(file.getAbsolutePath())); 
            document.open(); 
            document.addAuthor("RISKS-AutoGen");
            
            
            
            PdfPTable table= new PdfPTable(3);
            table.setSpacingAfter(10);
            Phrase p = new Phrase("Raidieschen SKS");
            font = p.getFont();
            font.setSize(10);
            p.setFont(font);
            PdfPCell cell = new PdfPCell(p);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
            cell.setBorder(0);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase("Raid vom "+raidDateFormat.format(book.getActiveRaid().getDate()),font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setBorder(0);
            table.addCell(cell);
            
            p = new Phrase(currentDateFormat.format(new Date()));
            p.setFont(font);
            cell = new PdfPCell(new Phrase(currentDateFormat.format(new Date()),font));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT); 
            cell.setBorder(0);
            table.addCell(cell);
            
            document.add(table); 
            
            
            table= new PdfPTable(1);
            table.setSpacingAfter(15);
            cell = new PdfPCell(new Phrase("SKS-Liste",font));
            cell.setBackgroundColor(lightGray);
            cell.setBorderWidth(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            table.addCell(cell);
            document.add(table); 
            
            
            document.add(createTitleTable(w)); 
            document.add(createMemberTable(w)); 
            document.close();
            
        } catch (DocumentException | FileNotFoundException ex) {
            
        }
    }
    
    /**
     * Erzeugt die Spaltentiteltabelle in der PDF
     * 
     * @param w Spaltenbreiten der einzelnen Spalten
     * @return die Tabelle
     */
    private PdfPTable createTitleTable(float[] w){
        float borderWith = 2f;
        PdfPTable table= new PdfPTable(6);
        String[] fields= {"Pos","Name","Klasse","Primärrolle","Sekundärrolle","SKS-Raids"};
        table.setSpacingAfter(15);
        try {
            table.setWidths(w);
        } catch (DocumentException ex) {
            
        }
        for(String field:fields){
            PdfPCell cell = new PdfPCell(new Phrase(field,font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setBorderWidth(borderWith);
            table.addCell(cell);
        }
        return table;
    }
    
    /**
     * Erzeugt die Datentabelle der PDF
     * @param w Spaltenbreiten der einzelnen Spalten
     * @return die Tabelle
     */
    private PdfPTable createMemberTable(float[] w){
        float borderWith = 1.25f;
        LinkedList<Player> list = book.getActiveRaid().getNextSKS();
        
        PdfPTable table= new PdfPTable(6);
        try {
            table.setWidths(w);
        } catch (DocumentException ex) {
        }
        for(int i=0;i<list.size();i++){
            BaseColor color = BaseColor.WHITE;
            if(i % 2 !=0){
                color = lightGray;
            }
            Player member = list.get(i);
            
            PdfPCell cell = new PdfPCell(new Phrase((i+1)+"",font));
            cell.setBorderWidth(borderWith);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setBorder(Rectangle.LEFT|Rectangle.BOTTOM|Rectangle.TOP);
            cell.setBackgroundColor(color);
            table.addCell(cell);
            

            cell = new PdfPCell(new Phrase(member.getName(),font));
            cell.setBorderWidth(borderWith);
            cell.setBorder(Rectangle.BOTTOM|Rectangle.TOP);
            cell.setBackgroundColor(color);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(member.getCallingString(),font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setBorderWidth(borderWith);
            cell.setBorder(Rectangle.BOTTOM|Rectangle.TOP);
            cell.setBackgroundColor(color);
            table.addCell(cell);
            
            cell = new PdfPCell(new Phrase(member.getSpecMainString(),font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setBorderWidth(borderWith);
            cell.setBorder(Rectangle.BOTTOM|Rectangle.TOP);
            cell.setBackgroundColor(color);
            table.addCell(cell);
            
            
            LinkedList<Player.Spec> specs = member.getSpecsSecond();
            String specString="";
            for(int j=0; j<specs.size();j++){
                specString+=(Player.specToString(specs.get(j)));
                if(j<specs.size()-1){
                    specString+="|";
                }
            }
            cell = new PdfPCell(new Phrase(specString,font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setBorderWidth(borderWith);
            cell.setBorder(Rectangle.BOTTOM|Rectangle.TOP);
            cell.setBackgroundColor(color);
            table.addCell(cell);
            
            int rc = member.getRaidCount();
            if(member.getState().equals(RaidState.TEILG)){
                rc++;
            }
            cell = new PdfPCell(new Phrase(""+rc,font));
            cell.setBorderWidth(borderWith);
            cell.setBorder(Rectangle.BOTTOM|Rectangle.TOP|Rectangle.RIGHT);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
            cell.setBackgroundColor(color);
            table.addCell(cell);
            
            table.completeRow();
        }
        
        return table;
    }
    
}
