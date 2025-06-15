package Strategies;

public class HtmlComparisonStrategy implements ComparisonStrategy {
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        return !oldContent.equals(newContent);
    }
}