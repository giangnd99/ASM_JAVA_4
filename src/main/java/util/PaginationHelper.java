package util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PaginationHelper<T> {
    private List<T> itemList;
    private int pageSize;
    private int totalRecords;
    private int pageTotal;

    public PaginationHelper(List<T> itemList, int pageSize) {
        this.itemList = itemList;
        this.pageSize = pageSize;
        this.totalRecords = itemList.size();
        this.pageTotal = (int) Math.ceil((double) totalRecords / pageSize);
    }
    
    public List<T> getPage(int pageNumb) {
        List<T> pageContent = new ArrayList<>();
        if (pageNumb <= 0 || pageNumb > pageTotal) {
            return pageContent;
        }

        int startIndex = (pageNumb - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalRecords);
        pageContent = itemList.subList(startIndex, endIndex);

        return pageContent;
    }
}
