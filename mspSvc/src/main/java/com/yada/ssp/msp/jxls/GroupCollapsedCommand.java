package com.yada.ssp.msp.jxls;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.jxls.transform.poi.PoiTransformer;

/**
 * 行折叠的命令
 */
public class GroupCollapsedCommand extends AbstractCommand {

    Area area;

    public String getName() {
        return "groupCollapsed";
    }

    public Size applyAt(CellRef cellRef, Context context) {
        Size resultSize = area.applyAt(cellRef, context);
        if (resultSize.equals(Size.ZERO_SIZE)) return resultSize;
        PoiTransformer transformer = (PoiTransformer) area.getTransformer();
        Workbook workbook = transformer.getWorkbook();
        Sheet sheet = workbook.getSheet(cellRef.getSheetName());
        int startRow = cellRef.getRow();
        int endRow = cellRef.getRow() + resultSize.getHeight() - 1;
        sheet.groupRow(startRow, endRow);
        sheet.setRowGroupCollapsed(startRow, true);
        return resultSize;
    }

    @Override
    public Command addArea(Area area) {
        super.addArea(area);
        this.area = area;
        return this;
    }
}
