import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Decolumnizer {

  public static void main(String[] args) throws IOException {
    for (String input : args) {
      System.out.println(new Decolumnizer(Files.readAllLines(Paths.get(input))).parse());
    }
  }

  private final List<String> lines;
  private final List<Paragraph> paragraphs;
  private Paragraph currentFeatureParagraph;

  public Decolumnizer(List<String> lines) {
    this.lines = lines.subList(1, Integer.parseInt(lines.get(0)) + 1);
    this.paragraphs = new ArrayList<>();

    this.paragraphs.add(new Paragraph());
  }

  public String parse() {

    for (String line : lines) {
      char[] chars = line.toCharArray();
      boolean insideFeature = false;

      final StringBuilder textFromThisLine = new StringBuilder();

      //feature begin and end detection
      int cornerCount = 0;
      int sideCount = 0;
      for (char c : chars) {
        switch (c) {
          case '+':
            cornerCount++;
            break;
          case '|':
            sideCount++;
            break;
        }
      }
      if (cornerCount == 2 && sideCount != 1) {
        if (currentFeatureParagraph == null) {
          currentFeatureParagraph = getCurrentParagraph();
          currentFeatureParagraph.addFeature();
        } else if (currentFeatureParagraph != null && sideCount == 2) {
          if (getCurrentFeature().length() == 0) {
            //do nothing
          } else {
            currentFeatureParagraph.addFeature();
          }
        } else {
          currentFeatureParagraph = null;
        }
      }

      for (int n = 0; n < chars.length; n++) {
        char c = chars[n];
        switch (c) {
          case '+':
          case '|':
            if (insideFeature) {
              if (n == chars.length - 1) {//end of line
                break;
              }
              if (chars[n + 1] == ' ') {
                insideFeature = false;
              }
              break;
            } else {
              insideFeature = true;
              break;
            }
          default:
            if (insideFeature) {
              if (currentFeatureParagraph != null && c != '-') {
                addChar(getCurrentFeature(), c);
              }
            } else {
              addChar(textFromThisLine, c);
            }
        }
      }

      if (textFromThisLine.length() == 0) {
        paragraphs.add(new Paragraph());
      } else {
        while (true) {
          int lastCharPos = textFromThisLine.length() - 1;
          char lastChar = textFromThisLine.charAt(lastCharPos);
          if (lastChar == '-') {
            textFromThisLine.deleteCharAt(lastCharPos);
            break;
          } else if (lastChar == ' ') {
            textFromThisLine.deleteCharAt(lastCharPos);
          } else {
            addChar(textFromThisLine, ' ');
            break;
          }
        }
        getCurrentParagraph().getText().append(textFromThisLine);
      }
    }

    StringBuilder result = new StringBuilder();
    for (Paragraph paragraph : paragraphs) {
      if (result.length() > 0) {
        result.append("\n\n");
      }
      for (StringBuilder feature : paragraph.getFeatures()) {
        result.append('(');
        result.append(feature.toString().trim());
        result.append(") ");
      }
      result.append(paragraph.getText());
    }
    return result.toString();
  }

  private StringBuilder getCurrentFeature() {
    List<StringBuilder> features = currentFeatureParagraph.getFeatures();
    return features.get(features.size() - 1);
  }

  private Paragraph getCurrentParagraph() {
    return paragraphs.get(paragraphs.size() - 1);
  }

  private void addChar(final StringBuilder text, final char c) {
    if (c == ' ') {
      int size = text.length();
      if (size < 1 || text.charAt(size - 1) == c) {
        return;
      }
    }
    text.append(c);
  }

  private class Paragraph {

    private final StringBuilder text = new StringBuilder();
    private final List<StringBuilder> features = new ArrayList<>();

    public StringBuilder getText() {
      return text;
    }

    public List<StringBuilder> getFeatures() {
      return features;
    }

    public void addFeature() {
      this.features.add(new StringBuilder());
    }
  }
}
