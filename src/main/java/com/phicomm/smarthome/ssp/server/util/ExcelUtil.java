package com.phicomm.smarthome.ssp.server.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.phicomm.smarthome.ssp.server.consts.Const.ExcelMgrVariate;

/**
 * @author fujiang.mao
 *
 */
public class ExcelUtil {

    private static final Logger LOGGER = LogManager.getLogger(ExcelUtil.class);

    /**
     * 字体是否加粗
     */
    public static boolean FONT_BLOD = false;

    /***
     * 字体大小
     */
    public static short FONT_SIZE = 12;

    /**
     * 字体名
     */
    public static String FONT_NAME = "宋体";

    /**
     * 创建Sheet
     * 
     * @param work
     *            工作簿
     * @param name
     *            页名
     * @return
     */
    private static HSSFSheet createSheet(HSSFWorkbook work, String name) {
        HSSFSheet sheet = work.createSheet(name);
        // 锁定表头
        sheet.createFreezePane(1, 1);
        sheet.setActive(false);
        return sheet;
    }

    /**
     * 创建页采取默认样式
     * 
     * @param workbook
     *            工作簿
     * @param title
     *            表头
     * @param name
     *            页名
     * @param content
     *            内容
     */
    public static HSSFSheet createSheet(HSSFWorkbook workbook, List<String> title, String name,
            List<List<String>> content) {
        CellStyle cellStyle = createStyle(workbook, FONT_BLOD, FONT_NAME, FONT_SIZE);
        CellStyle titleStyle = createStyle(workbook, true, FONT_NAME, (short) 14);
        return createSheet(workbook, name, cellStyle, titleStyle, title, content);
    }

    /**
     * 创建页采取默认样式
     * 
     * @param workbook
     *            工作簿
     * @param title
     *            表头
     * @param name
     *            页名
     * @param titleStyle
     *            表头样式
     * @param cell
     *            单元格样式
     * @param content
     *            内容
     */
    public static HSSFSheet createSheet(HSSFWorkbook workbook, String name, CellStyle cell, CellStyle titleStyle,
            List<String> title, List<List<String>> content) {
        HSSFSheet sheet = createSheet(workbook, name);
        createCell(sheet, -1, title, titleStyle);
        for (int i = 0; i < content.size(); i++) {
            createCell(sheet, i, content.get(i), cell);
        }
        for (int i = 0; i < title.size(); i++) {
            sheet.autoSizeColumn(i, true);
        }
        return sheet;
    }

    /**
     * 创建单元格样式
     * 
     * @param work
     *            工作簿
     * @param blod
     *            字体是否加粗
     * @param fontName
     *            字体名
     * @param fontsize
     *            字体大小
     * @return
     */
    public static CellStyle createStyle(HSSFWorkbook work, boolean blod, String fontName, short fontsize) {
        HSSFCellStyle style = work.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = work.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontsize);
        if (blod) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        style.setFont(font);
        style.setLocked(true);
        return style;
    }

    /**
     * 创建Cell
     * 
     * @param sheet
     * @param index
     * @param content
     * @param style
     */
    private static void createCell(HSSFSheet sheet, int index, List<String> content, CellStyle style) {
        HSSFRow row = sheet.createRow(index + 1);
        for (int i = 0; i < content.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(content.get(i));
            cell.setCellStyle(style);
        }
    }

    /**
     * 保存到文件
     * 
     * @param workbook
     *            工作簿
     * @param path
     *            文件路径
     * @param convert
     *            存在是否替换
     */
    public static void saveFile(HSSFWorkbook workbook, String path, boolean convert) {
        try {
            File file = new File(path);
            if (file.exists() && convert) {
                return;
            }
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(file);
            workbook.write(out);
            workbook.close();
            out.flush();
            out.close();
            LOGGER.debug("保存文件完成");
        } catch (Exception e) {
            LOGGER.error("ExcelUtil saveFile error " + e.getMessage());
        }
    }

//    /**
//     * 导出到Excel
//     * 
//     * @param response
//     * @param titles
//     * @param fileName
//     * @param content
//     */
//    public static void exportToExcel(HttpServletResponse response, String[] titles, String fileName,
//            List<List<String>> content) {
//        HSSFWorkbook bookWorkbook = new HSSFWorkbook();
//        HSSFSheet sheet = createSheet(bookWorkbook, Arrays.asList(titles), fileName, content);
//        // 设置列宽
//        sheet.setColumnWidth(0, 6000);
//        sheet.setColumnWidth(1, 6000);
//        sheet.setColumnWidth(2, 6000);
//        sheet.setColumnWidth(3, 6000);
//        sheet.setColumnWidth(4, 6000);
//        sheet.setColumnWidth(5, 6000);
//        sheet.setColumnWidth(6, 6000);
//        sheet.setColumnWidth(7, 6000);
//        sheet.setColumnWidth(8, 6000);
//        sheet.setColumnWidth(9, 6000);
//        sheet.setColumnWidth(10, 6000);
//        sheet.setColumnWidth(11, 6000);
//        sheet.setColumnWidth(12, 6000);
//        sheet.setColumnWidth(13, 6000);
//        sheet.setColumnWidth(14, 6000);
//
//        OutputStream outputStream = null;
//        try {
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName) + ".xls");
//            response.setContentType("application/vnd.ms-excel; charset=utf-8");
//            response.setCharacterEncoding("utf-8");
//            outputStream = response.getOutputStream();
//            bookWorkbook.write(outputStream);
//        } catch (IOException e) {
//            LOGGER.error("ExcelUtil exportToExcel error " + e.getMessage());
//        } finally {
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    LOGGER.error("ExcelUtil exportToExcel outputStream.close() error " + e.getMessage());
//                }
//                outputStream = null;
//            }
//            if (bookWorkbook != null) {
//                try {
//                    bookWorkbook.close();
//                } catch (IOException e) {
//                    LOGGER.error("ExcelUtil exportToExcel bookWorkbook.close() error " + e.getMessage());
//                }
//                bookWorkbook = null;
//            }
//        }
//    }
    
    /**
     * exportToExcel
     * 
     * @param response
     * @param titles
     * @param fileName
     * @param content
     */
    public static void exportToExcel(HttpServletResponse response, String[] titles, String fileName,
            List<List<String>> content) {
        HSSFWorkbook bookWorkbook = new HSSFWorkbook();
        int totalRow = content == null ? 0 : content.size();
        boolean isRemainder = totalRow % ExcelMgrVariate.SHEET_MAX_ROW == 0;
        int sheetColumnCount = totalRow / ExcelMgrVariate.SHEET_MAX_ROW;
        if (!isRemainder) {
            sheetColumnCount++;
        }
        if (sheetColumnCount > 0) {
            for (int sheetIndex = 1; sheetIndex <= sheetColumnCount; sheetIndex++) {
                int sheetStartRow = (sheetIndex - 1) * ExcelMgrVariate.SHEET_MAX_ROW;
                int sheetEndRow = sheetIndex * ExcelMgrVariate.SHEET_MAX_ROW;
                createSheets(bookWorkbook, Arrays.asList(titles), "Sheet" + sheetIndex, content.subList(
                        sheetStartRow > totalRow ? 0 : sheetStartRow, sheetEndRow > totalRow ? totalRow : sheetEndRow));
            }
        } else {
            createSheets(bookWorkbook, Arrays.asList(titles), "Sheet1", content);
        }
        OutputStream outputStream = null;
        try {
            // clear response
            response.reset();
            // set response header
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName) + ".xls");
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.setCharacterEncoding("utf-8");
            outputStream = response.getOutputStream();
            bookWorkbook.write(outputStream);
        } catch (IOException e) {
            LOGGER.error("ExcelUtil exportToExcel error " + e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    LOGGER.error("ExcelUtil exportToExcel outputStream.close() error " + e.getMessage());
                }
                outputStream = null;
            }
            if (bookWorkbook != null) {
                try {
                    bookWorkbook.close();
                } catch (IOException e) {
                    LOGGER.error("ExcelUtil exportToExcel bookWorkbook.close() error " + e.getMessage());
                }
                bookWorkbook = null;
            }
        }
    }
    
    private static void createSheets(HSSFWorkbook bookWorkbook, List<String> list, String sheetIndex,
            List<List<String>> content) {
        HSSFSheet sheet = createSheet(bookWorkbook, list, sheetIndex, content);
        // set column width
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 6000);
        sheet.setColumnWidth(6, 6000);
        sheet.setColumnWidth(7, 6000);
        sheet.setColumnWidth(8, 6000);
        sheet.setColumnWidth(9, 6000);
        sheet.setColumnWidth(10, 6000);
        sheet.setColumnWidth(11, 6000);
        sheet.setColumnWidth(12, 6000);
        sheet.setColumnWidth(13, 6000);
        sheet.setColumnWidth(14, 6000);
    }

}
