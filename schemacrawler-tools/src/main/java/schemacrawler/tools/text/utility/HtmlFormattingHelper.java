/*
 *
 * SchemaCrawler
 * http://sourceforge.net/projects/schemacrawler
 * Copyright (c) 2000-2015, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */

package schemacrawler.tools.text.utility;


import static schemacrawler.tools.text.utility.Entities.escapeForXMLElement;
import static sf.util.Utility.isBlank;
import static sf.util.Utility.readResourceFully;
import schemacrawler.tools.options.TextOutputFormat;

/**
 * Methods to format entire rows of output as HTML.
 *
 * @author Sualeh Fatehi
 */
public final class HtmlFormattingHelper
  extends BaseTextFormattingHelper
{

  private static String htmlHeader()
  {
    final String styleSheet = readResourceFully("/sc.css")
                              + readResourceFully("/sc_output.css");

    return "<!DOCTYPE html>" + System.lineSeparator() + "<html lang=\"en\">"
           + System.lineSeparator() + "<head>" + System.lineSeparator()
           + "  <title>SchemaCrawler Output</title>" + System.lineSeparator()
           + "  <meta charset=\"utf-8\"/>" + System.lineSeparator()
           + "  <style>" + System.lineSeparator() + styleSheet
           + System.lineSeparator() + "  </style>" + System.lineSeparator()
           + "</head>" + System.lineSeparator() + "<body>"
           + System.lineSeparator();
  }

  /**
   * HTML footer.
   */
  private static final String HTML_FOOTER = "</body>" + System.lineSeparator()
                                            + "</html>";

  /**
   * HTML header.
   */
  private static final String HTML_HEADER = htmlHeader();

  public HtmlFormattingHelper(final TextOutputFormat outputFormat)
  {
    super(outputFormat);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String createDocumentEnd()
  {
    return HTML_FOOTER;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String createDocumentStart()
  {
    return HTML_HEADER;
  }

  @Override
  public String createHeader(final DocumentHeaderType type, final String header)
  {
    if (!isBlank(header))
    {
      final String prefix;
      final String headerTag;
      if (type == null)
      {
        prefix = "<p>&#160;</p>";
        headerTag = "h2";
      }
      else
      {
        switch (type)
        {
          case title:
            prefix = "<p>&#160;</p>";
            headerTag = "h1";
            break;
          case subTitle:
            prefix = "<p>&#160;</p>";
            headerTag = "h2";
            break;
          case section:
            prefix = "";
            headerTag = "h3";
            break;
          default:
            prefix = "<p>&#160;</p>";
            headerTag = "h2";
            break;
        }
      }
      return String.format("%s%n<%s>%s</%s>%n",
                           prefix,
                           headerTag,
                           header,
                           headerTag);
    }
    else
    {
      return "";
    }
  }

  @Override
  public String createLeftArrow()
  {
    return "\u2190";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String createObjectEnd()
  {
    return "</table>" + System.lineSeparator() + "<p>&#160;</p>"
           + System.lineSeparator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String createObjectStart(final String name)
  {
    final StringBuilder buffer = new StringBuilder();
    buffer.append("<table>").append(System.lineSeparator());
    if (!isBlank(name))
    {
      buffer.append("  <caption>").append(escapeForXMLElement(name))
        .append("</caption>").append(System.lineSeparator());
    }
    return buffer.toString();
  }

  @Override
  public String createRightArrow()
  {
    return "\u2192";
  }

  @Override
  public String createWeakLeftArrow()
  {
    return "\u21dc";
  }

  @Override
  public String createWeakRightArrow()
  {
    return "\u21dd";
  }

}
