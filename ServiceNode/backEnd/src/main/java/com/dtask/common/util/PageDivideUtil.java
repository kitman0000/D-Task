package com.dtask.common.util;

/**
 * Created by zhong on 2019-2-24.
 */
public class PageDivideUtil {

    public static int getCountOfPages(int amount,int countOfOnePage)
    {
        return calculatePage(amount,countOfOnePage);
    }

    private static int calculatePage(int intRows, int countOfOnePage)
    {
        int result;

        if(intRows == 0) {
            result = 0;
        }
        else if(intRows < countOfOnePage) {
            result = 1;
        }
        else if(intRows % countOfOnePage != 0) {
            result = intRows / countOfOnePage + 1;
        }
        else {
            result = intRows / countOfOnePage;
        }

        return result;
    }
}
