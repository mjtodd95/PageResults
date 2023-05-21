import java.util.ArrayList;
import java.util.List;

public class PageResults {

    public static List<Integer> getPages(int currentPage, int numberOfResultsToDisplay, int totalNumberOfResults) {
        validateParameters(currentPage, numberOfResultsToDisplay, totalNumberOfResults);

        List<Integer> pageList = new ArrayList<>(numberOfResultsToDisplay);

        int initialCenter = (numberOfResultsToDisplay / 2) + 1;
        int lastStartPage = (totalNumberOfResults + 1) - numberOfResultsToDisplay;

        int startPage = getStartPage(currentPage, initialCenter, lastStartPage);
        int endPage = numberOfResultsToDisplay + (startPage - 1);

        for (int i = startPage; i <= endPage; i++) {
            pageList.add((i));
        }
        return pageList;
    }

    private static int getStartPage(int currentPage, int initialCenter, int lastStartPage){
        int startPage = 1;

        //Shift the starting page once current page is past the initial center point to keep current page centered
        if (currentPage > initialCenter)
            startPage += (currentPage - initialCenter);

        //Once all remaining results can be shown cap the start page to be the last possible set of results
        if (startPage > lastStartPage)
            startPage = lastStartPage;

        return startPage;
    }

    private static void validateParameters(int currentPage, int numberOfResultsToDisplay, int totalNumberOfResults){
        if (currentPage < 1)
            throw new IllegalArgumentException("Current Page can't be less than 1");
        if (numberOfResultsToDisplay < 1)
            throw new IllegalArgumentException("Number of Results to Display can't be less than 1");
        if (totalNumberOfResults < 1)
            throw new IllegalArgumentException("Total number of Results can't be less than 1");
        if (currentPage > totalNumberOfResults)
            throw new IllegalArgumentException("Current Page can't be greater than Total Number of Results");
        if (numberOfResultsToDisplay > totalNumberOfResults)
            throw new IllegalArgumentException("Number of Results to Display can't be more than the Total Number of Results");
    }


}
