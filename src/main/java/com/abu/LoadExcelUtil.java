package com.abu;

import com.abu.domain.TestCase;
import com.abu.domain.TestCaseConstants;
import com.google.common.base.Strings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadExcelUtil {

    private String filePath;
    private String sheetName;

    private LoadExcelUtil(){}

    public LoadExcelUtil(String filePath, String sheetName){
        this.filePath = filePath;
        this.sheetName = sheetName;
    }

    public static void main(String[] args) {
        List<TestCase> testCases = new LoadExcelUtil("D:\\excels\\sheet4.xlsx", "sheet1").getTestCases();

        for(int i=0; i< testCases.size();i++){
            TestCase testCase = testCases.get(i);
            if(testCase.getIndex()==21){
                System.out.println(testCase.getIndex());
                System.out.println(testCase.getTitle());
                //System.out.println(testCase.getSummary());
                for(String line: testCase.getDescriptions()){
                    System.out.println(line);
                }

                //System.out.println(testCase.getResult());
            }
        }
    }

    public List<TestCase> getTestCases(){

        List<TestCase> testCases = null;
        //实现从磁盘读取文件
        FileInputStream fs = null;
        //使用POI接受文件流读取的Excel整体文件
        XSSFWorkbook wb = null;

        try {
            fs = new FileInputStream(filePath);
            wb = new XSSFWorkbook(fs);

            //POI读取Excel的sheet页
            XSSFSheet sh = wb.getSheet(sheetName);
            testCases = getTestCases(sh);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return testCases;
    }

    /**
     * Load all the test cases from the sheet
     * @param sheet
     * @return
     */
    private List<TestCase> getTestCases(Sheet sheet) {
        List<TestCase> testCases = new ArrayList<>();
        int sheetMergeCount = sheet.getNumMergedRegions();
        int index = 0;
        for (int i=0; i< sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            //int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (firstColumn == 3) {
                TestCase ts = new TestCase(firstRow, lastRow);

                String title = sheet.getRow(ts.getFirstRow()).getCell(TestCaseConstants.INDEX_TITLE).getStringCellValue();
                fillComponent(sheet, ts);
                fillSummary(sheet, ts);
                fillSteps(sheet, ts);
                fillResult(sheet, ts);

                ts.setIndex(++index).setTitle(title);
                testCases.add(ts);
            }
        }
        return testCases;
    }

    /**
     * Get Summary String
     * @param sheet
     * @param testCase
     * @return
     */
    private void fillSummary(Sheet sheet, TestCase testCase){
        for(int i=testCase.getFirstRow();i<=testCase.getLastRow();i++){
            String line = sheet.getRow(i).getCell(TestCaseConstants.INDEX_SUMMARY).getStringCellValue();
            if(!Strings.isNullOrEmpty(line)){
                testCase.getSummary().add(line);
            }
        }
    }

    /**
     * Get Component/SubComponent String
     * @param sheet
     * @param testCase
     * @return
     */
    private void fillComponent(Sheet sheet, TestCase testCase){
        StringBuilder summary = new StringBuilder(60);
        for(int i=testCase.getFirstRow();i<=testCase.getLastRow();i++){
            String comp = sheet.getRow(i).getCell(TestCaseConstants.INDEX_COMP).getStringCellValue();
            if(!Strings.isNullOrEmpty(comp)){
                testCase.setComponent(comp);
            }
            String subcomp = sheet.getRow(i).getCell(TestCaseConstants.INDEX_SUBCOMP).getStringCellValue();
            if(!Strings.isNullOrEmpty(subcomp)){
                testCase.setSubComponent(subcomp);
            }else{
                testCase.setSubComponent("None");
            }
        }
    }

    /**
     * Get Steps String
     * @param sheet
     * @param testCase
     * @return
     */
    private void fillSteps(Sheet sheet, TestCase testCase){
        List<String> steps = testCase.getSteps();
        for(int i=testCase.getFirstRow();i<=testCase.getLastRow();i++){
            Cell cell = sheet.getRow(i).getCell(TestCaseConstants.INDEX_STEPS);
            String cell1 = sheet.getRow(i).getCell(TestCaseConstants.INDEX_STEPS).getStringCellValue();
            String cell2 = sheet.getRow(i).getCell(TestCaseConstants.INDEX_STEPS+1).getStringCellValue();

            StringBuilder sb = new StringBuilder(60);
            if(!Strings.isNullOrEmpty(cell1)){
                Hyperlink hl = cell.getHyperlink();
                if(hl != null){
                    sb.append(buildLink(cell1, hl.getAddress()));
                }else{
                    sb.append(cell1);
                }
            }
            if(!Strings.isNullOrEmpty(cell2)){
                sb.append(" / ");
                sb.append(cell2);
            }
            if(sb.length()>0){
                steps.add(sb.toString());
            }
        }
    }

    /**
     * Get Result String
     * @param sheet
     * @param testCase
     * @return
     */
    private void fillResult(Sheet sheet, TestCase testCase){
        List<String> result = testCase.getResult();
        for(int i=testCase.getFirstRow();i<=testCase.getLastRow();i++){
            Cell cell = sheet.getRow(i).getCell(TestCaseConstants.INDEX_RESULT);
            String line = cell.getStringCellValue();
            if(!Strings.isNullOrEmpty(line)){
                Hyperlink hl = cell.getHyperlink();
                if(hl != null){
                    result.add(buildLink(line, hl.getAddress()));
                }else{
                    result.add(line);
                }
            }
        };
    }

    private String buildLink(String title, String link){
        return String.format("[%s|%s]", title, link);
    }
}
