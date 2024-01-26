package com.ExcelParser;

import com.ExcelParser.pojo.Employee;
import com.ExcelParser.pojo.Shift;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
public class Convertor {

    public void analysis(List<Employee> employeeList) {
            getConsecutiveWork(employeeList);
            timeBetweenShifts(employeeList);
            singleLongShift(employeeList);
    }

    public void getConsecutiveWork(List<Employee> employeeList){
        List<Employee> workedConsecutiveDays = new ArrayList<>();
        employeeList.forEach(employee -> {

            if(hasWorkedConsecutiveDays(employee))
                workedConsecutiveDays.add(employee);
        });

        System.out.println("who has worked for 7 consecutive days:------->>> ");

        workedConsecutiveDays
                .forEach(emp ->
                        System.out.println("\t \t Employee Name: "+
                                emp.getEmployeeName()+", \tPosition:  "+ emp.getPositionId())
                );
    }
    public boolean hasWorkedConsecutiveDays(Employee employee) {
        List<Shift> shifts = employee.getShiftList();
        if (shifts.size() < 2) {
            return false; // Need at least 2 shifts for consecutive days
        }

        shifts.sort(Comparator.comparing(Shift::getTimeIn));

        int consecutiveDays = 1; // We have at least one shift
        Calendar previousTimeIn = shifts.get(0).getTimeIn();

        for (int i = 1; i < shifts.size(); i++) {
            Calendar currentTimeIn = shifts.get(i).getTimeIn();

            // Check if the current shift is consecutive to the previous one
            if (isConsecutive(previousTimeIn, currentTimeIn)) {
                consecutiveDays++;
                if (consecutiveDays == 7) {
                    return true; // Found 7 consecutive days
                }
            } else if (isSameDay(previousTimeIn, currentTimeIn)) {
                continue;
            } else {
                consecutiveDays = 1; // Reset consecutive days count
            }

            previousTimeIn = currentTimeIn;
        }

        return false; // Consecutive 7 days not found
    }

    private boolean isConsecutive(Calendar previous, Calendar current) {
        // Check if the current shift is one day ahead of the previous one
        return current.get(Calendar.YEAR) == previous.get(Calendar.YEAR) &&
                current.get(Calendar.MONTH) == previous.get(Calendar.MONTH) &&
                current.get(Calendar.DAY_OF_MONTH) == previous.get(Calendar.DAY_OF_MONTH) + 1;
    }
    private boolean isSameDay(Calendar previous, Calendar current) {
        // Check if the current shift is one day ahead of the previous one
        return current.get(Calendar.YEAR) == previous.get(Calendar.YEAR) &&
                current.get(Calendar.MONTH) == previous.get(Calendar.MONTH) &&
                current.get(Calendar.DAY_OF_MONTH) == previous.get(Calendar.DAY_OF_MONTH) ;
    }



    private void singleLongShift(List<Employee> employeeList){

        System.out.println("who has worked for 14 hrs shift:------->>> ");
        employeeList.forEach(emp -> {

            if (workedLongShift(emp))
                System.out.println("\t \t Employee Name: "+
                        emp.getEmployeeName()+", \tPosition:  "+ emp.getPositionId());

        });

    }

    private boolean workedLongShift(Employee employee){

        List<Shift> shifts = employee.getShiftList();
        for (Shift shift : shifts) {
            if (shift.getMinutes() != null && shift.getMinutes() >= 14 * 60) {
                return true;
            }
        }
        return false;
    }



    private void timeBetweenShifts(List<Employee> employeeList){

        System.out.println("who have less than 10 hours of time between shifts but greater than 1 hour:------->>> ");
        employeeList.forEach(emp -> {

                if (checkTimeBetweenShiftCriteria(emp)){
                    System.out.println("\t \t Employee Name: "+
                            emp.getEmployeeName()+", \tPosition:  "+ emp.getPositionId());

                }

            });

    }

    private boolean checkTimeBetweenShiftCriteria(Employee emp){


        List<Shift> shifts = emp.getShiftList();

        shifts.sort(Comparator.comparing(Shift::getTimeIn));

        Calendar previousTimeout = shifts.get(0).getTimeOut();

        for (int i = 1; i < shifts.size(); i++) {
            Calendar currentTimeIn = shifts.get(i).getTimeIn();

            // Check if the time difference between shifts is less than 10 hours and more than 1 hour
            if (isTimeDiffBetween1And10Hours(previousTimeout, currentTimeIn)) {
                return true; // Found a time difference between 1 and 10 hours
            }

            previousTimeout = shifts.get(i).getTimeOut();
        }

        return false; // No time difference between 1 and 10 hours found
    }

    private boolean isTimeDiffBetween1And10Hours(Calendar previousTimeout, Calendar current) {
        if(previousTimeout == null || current == null) return false;



        long diffInMillis = current.getTimeInMillis() - previousTimeout.getTimeInMillis();
        long diffInHours = diffInMillis / (60 * 60 * 1000); // Convert milliseconds to hours

        return diffInHours > 1 && diffInHours < 10;
    }


}
