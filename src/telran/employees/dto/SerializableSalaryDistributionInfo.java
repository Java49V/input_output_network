package telran.employees.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SerializableSalaryDistributionInfo implements Serializable {
    private int minSalary;
    private int maxSalary;
    private int amountEmployees;

    public SerializableSalaryDistributionInfo(int minSalary, int maxSalary, int amountEmployees) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.amountEmployees = amountEmployees;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public int getAmountEmployees() {
        return amountEmployees;
    }
}
