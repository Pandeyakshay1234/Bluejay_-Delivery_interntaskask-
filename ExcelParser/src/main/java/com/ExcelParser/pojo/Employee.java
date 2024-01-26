package com.ExcelParser.pojo;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    String employeeName;

    String positionId;

    List<Shift> shiftList = new ArrayList<>();

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Employee(String employeeName, String positionId) {
        this.employeeName = employeeName;
        this.positionId = positionId;
    }

    public List<Shift> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeName='" + employeeName + '\'' +
                ", positionId='" + positionId + '\'' +
                ", shiftList=" + shiftList +
                '}';
    }
}
