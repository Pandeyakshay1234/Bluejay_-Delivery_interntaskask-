package com.ExcelParser;

/**
 * Hello world!
 *
 */
import com.ExcelParser.pojo.Employee;
import com.ExcelParser.pojo.Shift;
import com.aspose.cells.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReadExcelFile {

    public Map<String, Employee> employeeMap = new HashMap<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

    public static void main(String[] args) {
        new ReadExcelFile().parseFile();
    }


    public void parseFile(){
        // Load the Excel file
        Workbook workbook = null;
        try {
            String location = "C:\\interntask\\";
            workbook = new Workbook(location+ "Assignment_Timecard.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Worksheet worksheet = workbook.getWorksheets().get(0);

        // Access cells and read data
        Cells cells = worksheet.getCells();
        int numRows = cells.getMaxDataRow() + 1; // Get the number of rows
        int numCols = cells.getMaxDataColumn() + 1; // Get the number of columns


        Map<String, Integer> colIndex = new HashMap<>();
        for (int i = 0; i < numCols; i++) {
            Cell cell = cells.get(0, i);
            colIndex.put(cell.getStringValue().trim(), i);
        }

        for (int row = 1; row < numRows; row++) {
            extractDetails(cells, colIndex, row);
        }


        new Convertor().analysis(new ArrayList<>(employeeMap.values()));
        // Close the workbook
        try {
            workbook.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void extractDetails(Cells cells, Map<String, Integer> colIndex, int row) {
        Cell positionIdCell  = cells.get(row, colIndex.get("Position ID"));

        Employee employee = employeeMap.get(positionIdCell.getStringValue());


        if(employee == null){
            Cell employeeName  = cells.get(row, colIndex.get("Employee Name"));
            employee = new Employee(employeeName.getStringValue(), positionIdCell.getStringValue());
        }

        Shift shift = new Shift();

        String timeIn = cells.get(row, colIndex.get("Time")).getStringValue();
        String timeout = cells.get(row, colIndex.get("Time Out")).getStringValue();
        shift.setTimeIn(getCalenderDate(timeIn));
        shift.setTimeOut(getCalenderDate(timeout));

        String timeCard = cells.get(row, colIndex.get("Timecard Hours (as Time)")).getStringValue();

        Integer min = getMin(timeCard);

        shift.setMinutes(min);

        List<Shift> shiftList = employee.getShiftList();
        shiftList.add(shift);
        employee.setShiftList(shiftList);

        employeeMap.put(employee.getPositionId(), employee);
    }

    private  Integer getMin(String timeCard) {

        if(timeCard == null || timeCard.isEmpty()) return null;
        String [] timeSplit = timeCard.split(":");
        int min =  Integer.parseInt(timeSplit[0])*60;
        min += Integer.parseInt(timeSplit[1]);
        return min;
    }


    private Calendar getCalenderDate(String dateString) {
        Date date = null;
        if(dateString == null || dateString.isEmpty() ) return null;
        try {
            date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}

