package com.abu;

import com.abu.domain.TestCase;

import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        List<TestCase> testCases = new LoadExcelUtil("D:\\excels\\sheet4.xlsx", "sheet1").getTestCases();

        SeleniumUtil.createDriver();
        SeleniumUtil.login();
        SeleniumUtil.firstCreate();

        for(int i=0; i< testCases.size();i++){
            TestCase testCase = testCases.get(i);
            if(testCase.getIndex()==20 || testCase.getIndex()==21){
                SeleniumUtil.createTestCase(testCase);
            }
        }
    }
}
