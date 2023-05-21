import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> pages = PageResults.getPages(7, 10, 50);
        System.out.println(pages);
    }
}