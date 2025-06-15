package Strategies;

public class SizeComparisonStrategy implements ComparisonStrategy {
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        return oldContent.length() != newContent.length();
    }
}
