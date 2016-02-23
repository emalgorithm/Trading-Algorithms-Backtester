package QuandlAPIUser;

import java.util.List;

/**
 * Created by ema on 23/02/16.
 */
public class Dataset {
    private long limit;
    private Object transform;
    private long column_index;
    private List<String> column_names;
    private String start_date;
    private String end_date;
    private String frequency;
    private List<List<Object>> data;
    private Object collapse;
    private String order;

    public List<String> getColumn_names() {
        return column_names;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getFrequency() {
        return frequency;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public int getIndexInColumnNames(String text) {
        return column_names.indexOf(text);
    }
}
