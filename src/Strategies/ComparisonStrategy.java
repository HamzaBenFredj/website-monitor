package Strategies;

public interface ComparisonStrategy {
    boolean hasChanged(String oldContent, String newContent);
}