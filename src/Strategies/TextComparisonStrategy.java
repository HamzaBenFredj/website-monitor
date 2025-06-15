package Strategies;

public class TextComparisonStrategy implements ComparisonStrategy {
    @Override
    public boolean hasChanged(String oldContent, String newContent) {
        String oldText = oldContent.replaceAll("<[^>]*>", "");
        String newText = newContent.replaceAll("<[^>]*>", "");
        return !oldText.equals(newText);
    }
}