package doron.huji.ac.il.ex3;

import java.util.Date;

public class Item {

    private String task;
    private Date dueDate;

    public Item(String task, Date date) {
        this.task = task;
        this.dueDate = date;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    @Override
    public String toString() {
        return "#" + this.task;
    }
}
