package mip.classes.printservice;

import mip.classes.page.Page;
import mip.enums.Error;

import java.awt.print.*;
import java.util.Arrays;

/**
 * Created by killsett on 06.04.17.
 */
public class PrintService {
    private PrinterJob printerJob = PrinterJob.getPrinterJob();

    public Error print(Page[] pages) {
        if (pages.length > 0) {
            if (printerJob.printDialog()) {
                try {
                    PageFormat pageFormat = printerJob.defaultPage();
                    pageFormat.setOrientation(PageFormat.PORTRAIT);
                    Book pBook = new Book();
                    Paper paper = pageFormat.getPaper();

                    Arrays.stream(pages).forEach(page -> {
                        paper.setSize(page.getWidth(), page.getHeight());
                        paper.setImageableArea(0, 0, page.getWidth(), page.getHeight());
                        pageFormat.setPaper(paper);
                        pBook.append(page, pageFormat);
                    });
                    printerJob.setPageable(pBook);
                    printerJob.print();
                    return Error.OK;
                } catch (PrinterException e) {
                    e.printStackTrace();
                    return Error.ERROR_PRINTER;
                }
            } else {
                return Error.CANCEL_PRINTING;
            }
        } else {
            return Error.NOTHING_TO_PRINT;
        }
    }

}
