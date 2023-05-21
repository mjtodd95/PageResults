import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PageListTest {
    private List<Integer> expectedPages;
    private List<Integer> actualPages;

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    public void ShouldDisplayInitialResultPages_WhenEvenNumberOfResultsToDisplay(int currentPage) {
        expectedPages = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        actualPages = PageResults.getPages(currentPage, 10, 50);
        assertEquals(expectedPages, actualPages);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
    public void ShouldDisplayInitialResultPages_WhenOddNumberOfResultsToDisplay(int currentPage) {
        expectedPages = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        actualPages = PageResults.getPages(currentPage, 13, 50);
        assertEquals(expectedPages, actualPages);
    }

    @Test
    public void ShouldDisplayInitialResultPages_WhenSmallNumberOfResultsToDisplay() {
        //Single item List
        expectedPages = Arrays.asList(1);
        actualPages = PageResults.getPages(1, 1, 1);
        assertEquals(expectedPages, actualPages);

        //Two item list
        expectedPages = Arrays.asList(1, 2);
        actualPages = PageResults.getPages(1, 2, 3);
        assertEquals(expectedPages, actualPages);

        //Three item list
        expectedPages = Arrays.asList(1, 2, 3);
        actualPages = PageResults.getPages(2, 3, 5);
        assertEquals(expectedPages, actualPages);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    public void ShouldDisplayInitialResultPages_WhenLargeNumberOfResultsToDisplay(int currentPage) {
        expectedPages = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        actualPages = PageResults.getPages(currentPage, 20, 25);
        assertEquals(expectedPages, actualPages);
    }

    @Test
    public void ShouldDisplayInitialResultPages_WhenNumberOfResultsToDisplayEqualsTotalNumberOfResults() {
        expectedPages = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        actualPages = PageResults.getPages(8, 10, 10);
        assertEquals(expectedPages, actualPages);
    }

    @Test
    public void ShouldShiftStartPage_WhenPastInitialCenter() {
        //Single shift even display
        expectedPages = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        actualPages = PageResults.getPages(7, 10, 50);
        assertEquals(expectedPages, actualPages);

        //Single shift odd display
        expectedPages = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        actualPages = PageResults.getPages(7, 11, 50);
        assertEquals(expectedPages, actualPages);

        //Multiple shifts even display
        expectedPages = Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        actualPages = PageResults.getPages(10, 10, 50);
        assertEquals(expectedPages, actualPages);

        //Multiple shifts odd display
        expectedPages = Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        actualPages = PageResults.getPages(10, 11, 50);
        assertEquals(expectedPages, actualPages);

        //Shift to just before total number of results
        expectedPages = Arrays.asList(40, 41, 42, 43, 44, 45, 46, 47, 48, 49);
        actualPages = PageResults.getPages(45, 10, 50);
        assertEquals(expectedPages, actualPages);

        //Two item list
        expectedPages = Arrays.asList(2, 3);
        actualPages = PageResults.getPages(3, 2, 5);
        assertEquals(expectedPages, actualPages);

        //Three item list
        expectedPages = Arrays.asList(2, 3, 4);
        actualPages = PageResults.getPages(3, 3, 5);
        assertEquals(expectedPages, actualPages);

        //Large List
        expectedPages = Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        actualPages = PageResults.getPages(13, 20, 25);
        assertEquals(expectedPages, actualPages);
    }

    @ParameterizedTest
    @ValueSource(ints = {46, 47, 48, 49, 50})
    public void ShouldShowLastSetOfResults_WhenCurrentPageIsWithinLastNumbersOfResults(int currentPage) {
        expectedPages = Arrays.asList(41, 42, 43, 44, 45, 46, 47, 48, 49, 50);
        actualPages = PageResults.getPages(currentPage, 10, 50);
        assertEquals(expectedPages, actualPages);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    public void ShouldThrowIllegalArgumentException_WhenParametersAreLessThanOne(int parameter) {
        Exception exception;

        exception = assertThrows(IllegalArgumentException.class, () ->
                PageResults.getPages(parameter, 5, 5));
        assertEquals("Current Page can't be less than 1", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () ->
                PageResults.getPages(1, parameter, 5));
        assertEquals("Number of Results to Display can't be less than 1", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () ->
                PageResults.getPages(1, 1, parameter));
        assertEquals("Total number of Results can't be less than 1", exception.getMessage());
    }

    @Test
    public void ShouldThrowIllegalArgumentException_WhenCurrentPageIsGreaterThanTotalNumberOfResults() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PageResults.getPages(21, 5, 20));
        assertEquals("Current Page can't be greater than Total Number of Results", exception.getMessage());
    }

    @Test
    public void ShouldThrowIllegalArgumentException_WhenNumberOfResultsToDisplayIsGreaterThanTotalNumberOfResults() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PageResults.getPages(2, 15, 12));
        assertEquals("Number of Results to Display can't be more than the Total Number of Results", exception.getMessage());
    }

}
